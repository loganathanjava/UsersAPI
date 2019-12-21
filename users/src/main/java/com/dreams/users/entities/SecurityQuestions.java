package com.dreams.users.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mySecurity_questons")
@Getter
@Setter
public class SecurityQuestions //extends SecurityBaseEntity implements Serializable
{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -7209489161095465320L;

	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private UsersEntity usersEntity;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "security_id")
	private long id;
	
	@Column(name = "question")
	private String question;


	@Column(name = "answer")
	private String answer;

}
