package com.ecobackserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecobackserver.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}