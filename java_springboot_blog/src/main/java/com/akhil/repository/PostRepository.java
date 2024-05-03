package com.akhil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akhil.model.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
    
}
