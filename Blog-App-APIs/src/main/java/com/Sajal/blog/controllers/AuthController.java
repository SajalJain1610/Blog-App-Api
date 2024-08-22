package com.Sajal.blog.controllers;

import com.Sajal.blog.exceptions.ApiException;
import com.Sajal.blog.payloads.JwtAuthRequest;
import com.Sajal.blog.payloads.JwtAuthResponse;
import com.Sajal.blog.payloads.UserDto;
import com.Sajal.blog.security.JwtTokenHelper;
import com.Sajal.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody JwtAuthRequest request
			) throws Exception{
		
		this.authenticate(request.getUsername(),request.getPassword());
		UserDetails userDetails = this.userDetailService.loadUserByUsername(request.getUsername());
		
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK); 
	}

	private void authenticate(String username, String password) throws Exception {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		// we might get exceptions here i.e. user disabled handling it in global exception	
		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);				
		}
		catch(BadCredentialsException ex) {
			System.out.println("invalid details of user in request");
			throw new ApiException("Invalid Username or password");
		}
	}
	
	// register new user
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto){
		UserDto newUser = this.userService.registerNewUser(userDto);
		
		return new ResponseEntity<UserDto>(newUser,HttpStatus.OK);
	}
}
