package com.dreams.users.entities.primarykeys;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Embeddable
@NoArgsConstructor
public class SecurityQuestionIdentity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4145356477283143206L;
	
	@NotNull
	private long id;
}
