package com.dreams.users.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.dreams.users.entities.primarykeys.BaseIdEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "myusers")
@Getter
@Setter
@NoArgsConstructor
public class UsersEntity extends BaseIdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7146341953559756897L;

	@ApiModelProperty(value = "Refers user's email address. Email address will be used as username for login", required = true, notes = "Email address will be used as username for login")
	@Column(unique = true)
	private String email;

	@NotBlank
	@NotEmpty
	private String firstname;

	@Size(min = 1, message = "Lastname should be minimum 1 char")
	private String lastname;

	@NotEmpty(message = "Password Should not be empty")
	private String password;

	@Range(min = 1, message = "{user.type.range}")
	private int type;

	@Size(max = 10, min = 10, message = "Phone number should be valid")
	private String phone;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID")
	List<SecurityQuestions> securityQuestions;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID")
	List<UserRoles> roles;
	
}
