package com.dreams.users.repository;

import org.springframework.data.repository.CrudRepository;

import com.dreams.users.entities.SecurityQuestions;

public interface SecurityQuestionRepository extends CrudRepository<SecurityQuestions , Long> {

}
