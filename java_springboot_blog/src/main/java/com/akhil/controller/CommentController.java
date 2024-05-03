package com.akhil.controller;

import java.util.List;

import com.akhil.model.Comment;
import com.akhil.model.Post;
import com.akhil.model.User;
import com.akhil.response.CommentResponse;
import com.akhil.service.CommentService;
import com.akhil.service.PostService;
import com.akhil.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PutMapping;



@RestController
public class CommentController {

  @Autowired
  private CommentService commentService;

  @Autowired
  private PostService postService;

  @Autowired
  private UserService userService;

  @PostMapping("/api/comment/create/{id}")
  public CommentResponse createComment(
    @RequestHeader("Authorization") String jwt,
    @RequestBody Comment comment,
    @PathVariable Long id
  ) throws Exception {
    try {
      Post post = postService.findPostById(id);
      User user = userService.findUserByJwt(jwt);

      Comment createdComment = commentService.createComment(
        comment,
        user,
        post
      );
      CommentResponse res = new CommentResponse();
      res.setComment(createdComment);
      res.setMessage("Comment created successfully");
      return res;
    } catch (Exception e) {
      CommentResponse res = new CommentResponse();
      res.setMessage(e.getMessage());
      return res;
    }
  }

  @GetMapping("/getpostcomment/{postId}")
  public List<Comment> getPostComments(@PathVariable Long postId) throws Exception{
    try {
      List<Comment> postComments = commentService.getPostComment(postId);
        return postComments;
    } catch (Exception e) {
        throw new Exception(e);
    }
  }

  @GetMapping("/api/comment/getcomment")
  public List<Comment> getAllComments(@RequestHeader("Authorization") String jwt) throws Exception {
    List<Comment> comments = commentService.getAllComment(); 
    return comments;
  }

  @DeleteMapping("/api/comment/delete/{id}")
  public CommentResponse deleteComments(@PathVariable Long id) throws Exception{
    
    try {
      commentService.deleteComment(id);
      CommentResponse res = new CommentResponse();
      res.setMessage("Comment deleted successfully");
      return res;
    } catch (Exception e) {
      CommentResponse res = new CommentResponse();
      res.setMessage(e.getMessage());
      return res;
    }
  }

  @PutMapping("/api/comment/update/{id}")
  public CommentResponse updateComment(@PathVariable Long id, @RequestBody Comment comment) throws Exception{
     Comment updatedComment = commentService.updateComment(comment, id);
      CommentResponse res = new CommentResponse();
      res.setComment(updatedComment);
      res.setMessage("Comment updated successfully");
      return res;
  }
  
  
}
