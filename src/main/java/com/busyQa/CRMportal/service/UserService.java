package com.busyQa.CRMportal.service;

import java.util.Set;

import com.busyQa.CRMportal.entity.User;
import com.busyQa.CRMportal.entity.UserRole;

public interface UserService {

	//creating user
	public User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	//get user by username
	public User getUser(String username);
	
	//delete user by username
	public void deleteUser(Long userId );
}
