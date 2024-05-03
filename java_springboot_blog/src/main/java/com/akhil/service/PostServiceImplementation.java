package com.akhil.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akhil.model.Post;
import com.akhil.model.User;
import com.akhil.repository.PostRepository;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Service
public class PostServiceImplementation implements PostService {
    
    @Autowired
    private PostRepository postRepository;

    @Override
    public Post createPost(@Valid Post post, User user) throws Exception{
        @NotBlank
        String title = post.getTitle();
        @NotBlank
        String content = post.getContent();
        @NotBlank
        String image = post.getImage();
        @NotBlank
        String category = post.getCategory();
        String slug = post.getTitle().trim()
        .replaceAll("\\s+", "-")
        .toLowerCase()
        .replaceAll("[^a-zA-Z0-9-]", "");
        try{
            Post createdPost = new Post();
            createdPost.setTitle(title);
            createdPost.setContent(content);
            createdPost.setImage(image);
            createdPost.setUser(user);
            createdPost.setSlug(slug);
            createdPost.setCategory(category);
            createdPost.setCreatedAt(LocalDateTime.now());
            
            return postRepository.save(createdPost);
        }catch(Exception e){
             throw new Exception(e);
        }
           
        
    }

    @Override
    public Post findPostById(Long id) throws Exception{
        Optional<Post> opt = postRepository.findById(id);
        if(opt.isPresent()){
          return opt.get();  
        }
        throw new Exception("Post doesn't exists");
    }

    @Override
    public void deletePost(Long id) throws Exception{
        findPostById(id);

        postRepository.deleteById(id);
        
    }

    @Override
    public Post updatePost(Post post,Long id) throws Exception{
        Post oldPost = findPostById(id);
        

        if(post.getTitle() != null){
            String slug = post.getTitle().trim()
            .replaceAll("\\s+", "-")
            .toLowerCase()
            .replaceAll("[^a-zA-Z0-9-]", "");

            oldPost.setTitle(post.getTitle());
            oldPost.setSlug(slug);
        }
        if(post.getImage() != null){
            oldPost.setImage(post.getImage());
        }
        if(post.getContent() != null){
            oldPost.setContent(post.getContent());
        }
        if(post.getCategory() != null){
            oldPost.setCategory(post.getCategory());
        }
        

        return postRepository.save(oldPost);
    }
    @Override
    public List<Post>findAllPost(){
        return postRepository.findAll();
    }


}
