package com.busyQa.CRMportal.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.busyQa.CRMportal.Config.JwtUtils;
import com.busyQa.CRMportal.entity.JwtRequest;
import com.busyQa.CRMportal.entity.JwtResponse;
import com.busyQa.CRMportal.entity.User;
import com.busyQa.CRMportal.service.impl.UserDetailsServiceImpl;

@RestController
@CrossOrigin("*")
public class AunthicateController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	
	//generate Token
	@PostMapping("/generate-token")
	
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{
		
		
		try {
			aunthenticate(jwtRequest.getUsername(),jwtRequest.getPassword());
			
		}catch(UsernameNotFoundException e) {
			e.printStackTrace();
			throw new Exception("User not found ");
		}
		
		//authenticate 
		
		UserDetails userDetails =this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String token = this.jwtUtils.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	
	private void aunthenticate(String username, String password) throws Exception {
		
		try {
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
		}catch(DisabledException e) {
			throw new Exception("USER DISABLED" + e.getMessage()); 
		}catch(BadCredentialsException e) {
			throw new Exception("Invalid Credentials" + e.getMessage());
		}
		
	}
	
	// return details of current user
	@GetMapping("/current-user")
	public User getCurrentUser(Principal principal) {
		 
		
		return ((User) this.userDetailsService.loadUserByUsername(principal.getName()));
		
	}
}
