package com.ss.redisdemo.exception;

public class UserNotFoundException extends RuntimeException {

	UserNotFoundException() {
		super("User not found");
	}
}
