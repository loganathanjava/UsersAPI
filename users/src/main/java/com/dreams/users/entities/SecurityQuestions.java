package com.dreams.users.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dreams.users.entities.primarykeys.SecurityBaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mySecurity_questons")
@Getter
@Setter
public class SecurityQuestions extends SecurityBaseEntity implements Serializable{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -7209489161095465320L;

	@ManyToOne
	@JsonIgnore
	UsersEntity user;

	@Column(name = "question")
	private String question;


	@Column(name = "answer")
	private String answer;

}
