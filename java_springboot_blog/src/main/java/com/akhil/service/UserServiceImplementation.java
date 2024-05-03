package com.akhil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.akhil.config.JwtProvider;
import com.akhil.model.User;
import com.akhil.repository.UserRepository;


@Service
public class UserServiceImplementation implements UserService{
  
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserById(Long userId) throws Exception {
         Optional<User> opt = userRepository.findById(userId);
         if(opt.isPresent()){
            return opt.get();  
          }
          throw new Exception("user doesnot exists");
    }

    @Override
    public User findUserByJwt(String jwt) throws Exception {
      
      String email = jwtProvider.getEmailFromJwtToken(jwt);
      
      if(email==null){
        throw new Exception("Provide valid JWT token");
      }

      User user = userRepository.findByEmail(email);
      
      if(user==null){
        throw new Exception("User doesnot exists");
      }
      return user;
    }

    @Override
    public List<User>findAllUsers(){
        return userRepository.findAll();
    } 

    @Override
    public User updateUserById(User user,Long id) throws Exception{
        User oldUser = findUserById(id);
        String userName = user.getUserName();
        String email = user.getEmail();
        String password = user.getPassword();
        String profilePicture = user.getProfilePicture();

        if(userName!=null){
            oldUser.setUserName(userName);
        }
        if(email!=null){
          oldUser.setEmail(email);
        }
        if(password!=null){
          oldUser.setPassword(passwordEncoder.encode(password));
        }
        if(profilePicture!=null){
          oldUser.setProfilePicture(profilePicture);
        }
        
        return userRepository.save(oldUser);
    }

    @Override
    public void deleteUserById(Long id) throws Exception{
      findUserById(id);
        userRepository.deleteById(id);
    } 
    
} 
