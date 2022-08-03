package com.busyQa.CRMportal.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busyQa.CRMportal.entity.User;
import com.busyQa.CRMportal.entity.UserRole;
import com.busyQa.CRMportal.repo.RoleRepository;
import com.busyQa.CRMportal.repo.UserRepository;
import com.busyQa.CRMportal.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	
	//creating user
	
	@Override
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {
		
		User local = this.userRepository.findByusername(user.getUsername());
		
		if(local != null) {
			System.out.println("User is already present!!");
			throw new Exception("User Already Present!!");
		}
		else {
			for(UserRole ur: userRoles) {
				roleRepository.save(ur.getRole());
				
			}
			
			user.getUserRoles().addAll(userRoles);
			local = this.userRepository.save(user);
		}
		
		return local;
	}

	//getting user by username

	@Override
	public User getUser(String username) {
		
		return this.userRepository.findByusername(username);
	}

	@Override
	public void deleteUser(Long UserId) {
		this.userRepository.deleteById(UserId);
		
	}

}
