package com.akhil.service;

import java.util.List;

import com.akhil.model.Post;
import com.akhil.model.User;


public interface PostService {
    
    public Post createPost(Post post, User user) throws Exception;
    public Post findPostById(Long id) throws Exception;
    
    public void deletePost(Long id) throws Exception;
    public Post updatePost(Post post,Long id) throws Exception;
    public List<Post>findAllPost();
}

