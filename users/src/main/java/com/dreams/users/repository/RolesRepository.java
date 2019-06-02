package com.dreams.users.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dreams.users.entities.Roles;

public interface RolesRepository extends CrudRepository<Roles, Long>{
	List<Roles> getRolesByUserId(String userId);
}
