package com.hangeulbada.domain.workbookset.repository;

import com.hangeulbada.domain.workbookset.entity.Workbook;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkbookRepository extends MongoRepository<Workbook, String> {
}
