package com.busyQa.CRMportal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busyQa.CRMportal.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByusername(String username);
}
