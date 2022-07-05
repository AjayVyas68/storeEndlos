package com.storeendlos.Item.Repository;

import com.storeendlos.Item.model.StoreItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ItemRepository extends JpaRepository<StoreItemModel,Long> {

    List<?> findByItemIdAndVendorDateVid(Long itemId, Long vendorId);

    @Query("select i from StoreItemModel  i where  DATE(i.createdAt) between :start and :end")
    List<?> findByCreatedBetweenGenerateItem(@Param("start") Date start, @Param("end")  Date end);
    @Query("select i from StoreItemModel i where  i.createdAt=:date")
    List<?> findbySingleDate(Date date);
}
