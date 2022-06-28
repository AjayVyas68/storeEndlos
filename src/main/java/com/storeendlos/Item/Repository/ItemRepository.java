package com.storeendlos.Item.Repository;

import com.storeendlos.Item.model.StoreItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<StoreItemModel,Long> {
}
