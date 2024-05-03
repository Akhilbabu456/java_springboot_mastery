package com.akhil.controller;

import com.akhil.config.JwtProvider;
import com.akhil.model.User;
import com.akhil.repository.UserRepository;
import com.akhil.request.LoginRequest;
import com.akhil.response.AuthResponse;
import com.akhil.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CustomUserDetailsService customUserDetails;

  @Autowired
  private JwtProvider jwtProvider;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostMapping("/signup")
  public AuthResponse createUser(@RequestBody User user) throws Exception {
    String email = user.getEmail();
    String password = user.getPassword();
    String userName = user.getUserName();
    try{
      if(email == null || email.isEmpty() || 
      password == null || password.isEmpty() || 
      userName == null || userName.isEmpty()){
          throw new Exception("All fields are required");
      }
  
      User isExistEmail = userRepository.findByEmail(email);
      if (isExistEmail != null) {
        throw new Exception("Email already exist");
      }
      User createdUser = new User();
      createdUser.setEmail(email);
      createdUser.setUserName(userName);
      createdUser.setPassword(passwordEncoder.encode(password));
      createdUser.setProfilePicture("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png");
  
      User savedUser = userRepository.save(createdUser);
  
      Authentication authentication = new UsernamePasswordAuthenticationToken(
        email,
        password
      );
  
      SecurityContextHolder.getContext().setAuthentication(authentication);
  
      String token = jwtProvider.generateToken(authentication);
  
      AuthResponse res = new AuthResponse();
  
      res.setJwt(token);
      res.setMessage("Signup successful");
  
      return res;
    }catch(Exception e){
      AuthResponse res = new AuthResponse();
      res.setMessage( e.getMessage());
      return res;
    }
  }
  
  @PostMapping("/google")
  public AuthResponse createGoogleUser(@RequestBody User user, 
                                     OAuth2AuthenticationToken token) throws Exception {
     OAuth2User oAuth2User = token.getPrincipal();
    String email = oAuth2User.getAttribute("email");
    String userName = oAuth2User.getAttribute("name");
    String password = oAuth2User.getAttribute("password");
    String profilePicture = oAuth2User.getAttribute("picture");
    try{
      if(email == null || email.isEmpty() || 
      password == null || password.isEmpty() || 
      userName == null || userName.isEmpty()){
          throw new Exception("All fields are required");
      }
  
      User isExistEmail = userRepository.findByEmail(email);
      if (isExistEmail != null) {
        throw new Exception("Email already exist");
      }
      User createdUser = new User();
      createdUser.setEmail(email);
      createdUser.setUserName(userName);
      createdUser.setPassword(passwordEncoder.encode(password));
      createdUser.setProfilePicture(profilePicture);
  
      User savedUser = userRepository.save(createdUser);
  
      Authentication authentication = new UsernamePasswordAuthenticationToken(
        email,
        password
      );
  
      SecurityContextHolder.getContext().setAuthentication(authentication);
  
      OAuth2AuthenticationToken gtoken = token;
      String gtokenString = gtoken.toString();

  
      AuthResponse res = new AuthResponse();
  
      res.setJwt(gtokenString);
      res.setMessage("Signup successful");
  
      return res;
    }catch(Exception e){
      AuthResponse res = new AuthResponse();
      res.setMessage( e.getMessage());
      return res;
    }
  }
  @PostMapping("/signin")
  public AuthResponse signinHandler(@RequestBody LoginRequest loginRequest) {
    String username = loginRequest.getEmail();
    String password = loginRequest.getPassword();
  try{
    Authentication authentication = authenticate(username, password);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = jwtProvider.generateToken(authentication);

    AuthResponse res = new AuthResponse();

    res.setJwt(token);
    res.setMessage("Signin successful");

    return res;
  }catch(Exception e){
    AuthResponse res = new AuthResponse();
    res.setMessage( e.getMessage());
    return res;
  }
  }

  private Authentication authenticate(String username, String password) throws Exception{
    UserDetails userDetails = customUserDetails.loadUserByUsername(username);

    if (userDetails == null) {
      throw new Exception("User not found");
    }

    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
      throw new Exception("Wrong password");
    }

    return new UsernamePasswordAuthenticationToken(
      userDetails,
      null,
      userDetails.getAuthorities()
    );
  }
}
