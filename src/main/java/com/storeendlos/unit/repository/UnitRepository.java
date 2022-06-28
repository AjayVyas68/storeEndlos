package com.storeendlos.unit.repository;

import com.storeendlos.unit.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface UnitRepository extends JpaRepository<Unit,Long> {
    List<Unit> findByUnitName(String name);
    List<Unit> findByCreated(Date date);
    Boolean existsByUnitName(String unit);
}
