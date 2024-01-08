package com.ss.redisdemo.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ss.redisdemo.dto.UserDto;
import com.ss.redisdemo.entity.User;
import com.ss.redisdemo.mapper.UserMapper;
import com.ss.redisdemo.repository.UserRepository;
import com.ss.redisdemo.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;

	@CacheEvict(value = "saveUser", keyGenerator = "keyGenerator")
	@Override
	public UserDto saveUser(UserDto userDto) {
		log.info("Request For Save User");
		User user = userMapper.toEntity(userDto);
		user = userRepository.save(user);
		return userMapper.toDto(user);
	}

	@Cacheable(value = "getAllUser", keyGenerator = "keyGenerator", sync = true)
	@Override
	public List<UserDto> getAllUsers() {
		log.info("Request For Get All User");
		return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
	}

	@Cacheable(value = "getUserById", keyGenerator = "keyGenerator")
	@Override
	public UserDto getUserById(Long userId) {
		log.info("Request For Get User By Id: ",userId);
		return userMapper.toDto(userRepository.findById(userId).get());
	}

	@CacheEvict(value = "deleteUserById", keyGenerator = "keyGenerator")
	@Override
	public void deleteUserbyId(Long userId) {
		log.info("Request For Delete User By Id: ",userId);
		userRepository.deleteById(userId);
	}

}
