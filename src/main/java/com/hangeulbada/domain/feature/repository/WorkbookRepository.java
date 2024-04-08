package com.hangeulbada.domain.feature.repository;

import com.hangeulbada.domain.feature.entity.Workbook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkbookRepository extends JpaRepository<Workbook, Long> {
}
