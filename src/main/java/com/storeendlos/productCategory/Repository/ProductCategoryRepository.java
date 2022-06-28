package com.storeendlos.productCategory.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.storeendlos.productCategory.model.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {
}
