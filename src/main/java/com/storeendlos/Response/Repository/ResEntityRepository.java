package com.storeendlos.Response.Repository;

import com.storeendlos.Response.Model.ResEntity;
import com.storeendlos.Response.Model.ResStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ResEntityRepository extends JpaRepository<ResEntity, Long> {
    List<ResEntity> findByCreated(Date date);
    List<ResEntity> findByResStatus(String satus);
    List<ResEntity> findByResStatusAndAndPdiId(ResStatus resStatus, Long pdiid);
    List<ResEntity> findByPoRaised(boolean raised);
    List<ResEntity> findByDoRaised(boolean doraised);
    List<ResEntity> findByIndentRaised(boolean indent);
}