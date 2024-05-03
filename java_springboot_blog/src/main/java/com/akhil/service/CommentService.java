package com.akhil.service;

import java.util.List;

import com.akhil.model.Comment;
import com.akhil.model.Post;
import com.akhil.model.User;


public interface CommentService {
  public Comment createComment(Comment comment, User user, Post post)
    throws Exception;
  public List<Comment> getPostComment(Long id) throws Exception;
  public String deleteComment(Long id) throws Exception;
  public Comment updateComment(Comment comment,Long id) throws Exception;
  public List<Comment> getAllComment() throws Exception;
}
