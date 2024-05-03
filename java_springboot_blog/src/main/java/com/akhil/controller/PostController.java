package com.akhil.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.akhil.response.PostResponse;
import com.akhil.model.Post;
import com.akhil.model.User;
import com.akhil.service.PostService;
import com.akhil.service.UserService;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/post/create")
    public PostResponse createPost(@RequestBody Post post, @RequestHeader("Authorization") String jwt) throws Exception{
        try{
            User user=userService.findUserByJwt(jwt);
            
            Post createdPost = postService.createPost(post, user);
            PostResponse res = new PostResponse();
            res.setPost(createdPost);
            res.setMessage("Post created successfully");
            return res;
        }catch(Exception e){
            PostResponse res = new PostResponse();
            res.setMessage(e.getMessage());
            return res;
        }
        
    }
    
    @GetMapping("/getposts")
    public List<Post> getAllPosts() throws Exception{
        
        
        List<Post> posts = postService.findAllPost();
        return posts;
    }
    
    @GetMapping("/getpost/{id}")
    public Post findPostById(@PathVariable Long id) throws Exception{
        
        
        Post post = postService.findPostById(id);
        return post;
    }

    @DeleteMapping("/api/post/{id}")
    public String deletePost(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception{
        
        
        postService.deletePost(id);
        return "Post deleted successfully";
    }

    @PutMapping("/api/post/{id}")
    public PostResponse updatePost(@RequestHeader("Authorization") String jwt, @RequestBody Post post, @PathVariable Long id) throws Exception{
        try{
            Post updatedPost = postService.updatePost(post, id);
            PostResponse res = new PostResponse();
            res.setPost(updatedPost);
            res.setMessage("Post updated successfully");
            return res;
        }catch(Exception e){
            PostResponse res = new PostResponse();
            res.setMessage(e.getMessage());
            return res;
        }
        
    }
   
}
