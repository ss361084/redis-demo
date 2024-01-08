package com.ss.redisdemo.service;

import java.util.List;

import com.ss.redisdemo.dto.UserDto;

public interface UserService {

	UserDto saveUser(UserDto user);
	
	List<UserDto> getAllUsers();
	
	UserDto getUserById(Long userId);
	
	void deleteUserbyId(Long userId);
}
