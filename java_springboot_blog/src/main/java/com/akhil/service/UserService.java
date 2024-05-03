package com.akhil.service;

import java.util.List;


import com.akhil.model.User;

public interface UserService {
    
    public User findUserById(Long userId) throws Exception;
    public List<User>findAllUsers() throws Exception;
    public User findUserByJwt(String jwt) throws Exception;
    public User updateUserById(User user,Long id) throws Exception;
    public void deleteUserById(Long id) throws Exception;
}
