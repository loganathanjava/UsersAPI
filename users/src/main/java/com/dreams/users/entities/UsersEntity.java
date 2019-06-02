package com.dreams.users.entities;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "myusers")
@Getter
@Setter
public class UsersEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7146341953559756897L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private long id;

	@ApiModelProperty(value = "Refers user's email address. Email address will be used as username for login", required = true, notes = "Email address will be used as username for login")
	@Column(unique = true)
	private String email;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public SecurityQuestions getSecurityQuestions() {
		return securityQuestions;
	}

	public void setSecurityQuestions(SecurityQuestions securityQuestions) {
		this.securityQuestions = securityQuestions;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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

	@OneToOne(mappedBy = "user")
	@Cascade(value = CascadeType.ALL)
	SecurityQuestions securityQuestions;

}
