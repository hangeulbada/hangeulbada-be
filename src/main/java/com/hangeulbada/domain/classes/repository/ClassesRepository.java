package com.hangeulbada.domain.classes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassesRepository extends JpaRepository<Classes,String>{
    Optional<Classes> findByClassname(String classname);
}
