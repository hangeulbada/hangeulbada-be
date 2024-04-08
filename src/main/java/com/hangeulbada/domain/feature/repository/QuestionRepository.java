package com.hangeulbada.domain.feature.repository;

import com.hangeulbada.domain.feature.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
