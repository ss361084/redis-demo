package com.ss.redisdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ss.redisdemo.dto.UserDto;
import com.ss.redisdemo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/add")
	private ResponseEntity<UserDto> saveuser(@RequestBody UserDto user) {
		UserDto dto = userService.saveUser(user);
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/getAll")
	private ResponseEntity<List<UserDto>> getAllUsers() {
		List<UserDto> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/get")
	private ResponseEntity<UserDto> getUser(@RequestParam("userId") Long userId) {
		UserDto user = userService.getUserById(userId);
		return ResponseEntity.ok(user);
	}
	
	@DeleteMapping("/get")
	private ResponseEntity<Void> deleteUser(@RequestParam("userId") Long userId) {
		userService.deleteUserbyId(userId);
		return ResponseEntity.ok().build();
	}
}
