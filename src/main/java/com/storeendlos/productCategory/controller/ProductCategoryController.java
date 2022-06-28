package com.storeendlos.productCategory.controller;

import com.storeendlos.productCategory.model.ProductCategory;
import com.storeendlos.productCategory.service.ProductCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/store/api/v1/productCategory")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @PostMapping
    public ResponseEntity<?> saveData(@RequestBody ProductCategory productCategory) {
        return new ResponseEntity<>(productCategoryService.saveProductCategory(productCategory), HttpStatus.CREATED);

    }

    @GetMapping(value = "/{pid}")
    public ResponseEntity<?> findByIdProductCategory(@PathVariable Long pid) {
        return ResponseEntity.ok(productCategoryService.findByProductCategoryId(pid));
    }

    @GetMapping
    public ResponseEntity<?> findAllProductCategory() {
        return ResponseEntity.ok(productCategoryService.findAllProductCategories());
    }

    @PutMapping(value = "/{pid}")
    public ResponseEntity<?> updateDataProductCategory(@PathVariable Long pid, @RequestBody ProductCategory productCategory) {
        return new ResponseEntity<>(productCategoryService.updateData(pid, productCategory), HttpStatus.OK);
    }

    @PatchMapping(value = "/{pid}")
    public ResponseEntity<?> updateData(@PathVariable Long pid, @RequestBody Map<String, Object> fields) {
        return new ResponseEntity<>(productCategoryService.updateProductCategory(pid, fields), HttpStatus.OK);
    }
}




