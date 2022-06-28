package com.storeendlos.productCategory.service;

import com.storeendlos.exception.ExceptionService.ProductCategoryNotFound;
import com.storeendlos.productCategory.Repository.ProductCategoryRepository;
import com.storeendlos.productCategory.model.ProductCategory;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public ProductCategory saveProductCategory(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    public Optional<ProductCategory> findByProductCategoryId(Long id) {
        return Optional
                .of(productCategoryRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("product Category not Found")));
    }

    public List<ProductCategory> findAllProductCategories() {
        return productCategoryRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(ProductCategory::getPId))
                .collect(Collectors.toList());
    }

    public Object updateData(Long pId, ProductCategory productCategory) {
        Optional<ProductCategory> data = Optional.of(productCategoryRepository.findById(pId).orElseThrow(() -> new ProductCategoryNotFound("category Not Found")));


        if (data.isPresent()) {
            data.get().setPName(productCategory.getPName());
            data.get().setPDescription(productCategory.getPDescription());
        }
        return productCategoryRepository.save(data.get());
    }

    public Object updateProductCategory(Long pid, Map<String, Object> fields) {
        ProductCategory productCategory = productCategoryRepository.findById(pid).get();
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(ProductCategory.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, productCategory, value);
        });
        return productCategoryRepository.save(productCategory);
    }
}
