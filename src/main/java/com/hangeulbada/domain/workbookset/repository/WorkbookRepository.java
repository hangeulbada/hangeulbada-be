package com.hangeulbada.domain.workbookset.repository;

import com.hangeulbada.domain.workbookset.entity.Workbook;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WorkbookRepository extends MongoRepository<Workbook, String> {
    List<Workbook> findByTeacherId(String teacherId);
    List<Workbook> findByIdIn(List<String> ids);
}
