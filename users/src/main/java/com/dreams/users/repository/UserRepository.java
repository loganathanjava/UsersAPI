package com.dreams.users.repository;

import org.springframework.data.repository.CrudRepository;

import com.dreams.users.entities.UsersEntity;

public interface UserRepository extends CrudRepository<UsersEntity, Long> {
	UsersEntity findByEmail(String email);
}
