package com.akhil.controller;


import com.akhil.model.User;
import com.akhil.response.UserResponse;
import com.akhil.service.UserService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserService userService;

 
  @GetMapping("/getusers")
  public List<User> findAllUser(@RequestHeader("Authorization") String jwt) throws Exception{

    List<User> user = userService.findAllUsers();
    return user;
  } 
  
  @GetMapping("/{userId}")
  public UserResponse findUserById(@RequestHeader("Authorization") String jwt, @PathVariable Long userId) throws Exception {
   try{
     User user = userService.findUserById(userId);  
     UserResponse res = new UserResponse();
     res.setUser(user);
     res.setMessage("User found");
     return res;
   }catch(Exception e){
    UserResponse res = new UserResponse();
    res.setMessage(e.getMessage());
    return res;
   }
  }

  @PutMapping("/update/{id}")
  public UserResponse updateUserById(@RequestHeader("Authorization") String jwt, @RequestBody User user,@PathVariable Long id) {
       try{
        User updatedUser = userService.updateUserById(user, id);
        UserResponse res = new UserResponse();
        res.setUser(updatedUser);
        res.setMessage("Updated successfully");
        return res; 
       }catch(Exception e){
        UserResponse res = new UserResponse();
        res.setMessage(e.getMessage());
        return res;
       }
  }

  @DeleteMapping("/delete/{id}")
  public UserResponse deleteUserById(@RequestHeader("Authorization") String jwt, @PathVariable Long id) {
      try{
        userService.deleteUserById(id);
        UserResponse res = new UserResponse();
        res.setMessage("Deleted successfully");
        return res;
      }catch(Exception e){
        UserResponse res = new UserResponse();
        res.setMessage(e.getMessage());
        return res;
      }
  }
  
  
  
 
}
