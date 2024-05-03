package com.akhil.service;

import com.akhil.model.Comment;
import com.akhil.model.Post;
import com.akhil.model.User;
import com.akhil.repository.CommentRepository;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImplementation implements CommentService {

  @Autowired
  private CommentRepository commentRepository;

  @Override
  public Comment createComment(@Valid Comment comment, User user, Post post)
    throws Exception {
    @NotBlank
    String content = comment.getContent();

    try {
      Comment createdComment = new Comment();
      createdComment.setContent(content);
      createdComment.setUser(user);
      createdComment.setPost(post);
      createdComment.setCreatedAt(LocalDateTime.now());

      return commentRepository.save(createdComment);
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  @Override
  public List<Comment> getPostComment(Long id) throws Exception{
    try {
      return commentRepository.findByPostId(id);
  } catch (Exception e) {
      throw new Exception("Error getting comments for post ID: " + id, e);
  }
  } 

  @Override
  public List<Comment> getAllComment() throws Exception{
    return commentRepository.findAll();
  }

  @Override
  public String deleteComment(Long id) throws Exception{

    commentRepository.deleteById(id);
    return "Comment deleted successfully";

  }

  @Override
  public Comment updateComment(Comment comment, Long id) throws Exception{
    Optional<Comment> oldCommentOpt = commentRepository.findById(id);
    Comment oldComment = oldCommentOpt.get();
    String comments = comment.getContent();
    if(comment.getContent()!=null){
      oldComment.setContent(comments);
    }

    return commentRepository.save(oldComment);
    
  }
}
