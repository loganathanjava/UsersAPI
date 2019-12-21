package com.dreams.users.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.dreams.users.entities.primarykeys.RoleBaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class UserRoles extends RoleBaseEntity {
	
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private UsersEntity usersEntity;
	
	private String roleName;
	
	
}
