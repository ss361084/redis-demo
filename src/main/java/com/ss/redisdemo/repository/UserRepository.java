package com.ss.redisdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.redisdemo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
