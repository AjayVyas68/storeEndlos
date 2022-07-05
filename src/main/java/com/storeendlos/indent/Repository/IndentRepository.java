package com.storeendlos.indent.Repository;

import com.storeendlos.indent.Model.Indent;
import com.storeendlos.indent.Model.IndentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IndentRepository extends JpaRepository<Indent,Long> {
    List<Indent> findByIndentStatus(IndentStatus indentStatus);
    List<Indent> findByCreated(Date date);
    @Query("select i from Indent  i where  DATE(i.created) between :start and :end")
    List<Indent> ffindByDateBEtween(@Param("start") Date start,@Param("end")  Date end);
  //  List<Indent> findByVendorDetailsIdAndAndIndentId(Long vendorId, Long indentId);

}
