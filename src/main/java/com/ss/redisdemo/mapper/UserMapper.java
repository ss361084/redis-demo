package com.ss.redisdemo.mapper;

import org.mapstruct.Mapper;

import com.ss.redisdemo.dto.UserDto;
import com.ss.redisdemo.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDto toDto(User entity);
	
	User toEntity(UserDto dto);
}
