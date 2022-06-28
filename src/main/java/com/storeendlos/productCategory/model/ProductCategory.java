package com.storeendlos.productCategory.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.storeendlos.Item.model.StoreItemModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pId;
    private String pName;
    private String pDescription;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;
    @OneToMany(mappedBy = "productCategory",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("productCategory")
    private List<StoreItemModel> itemdata;
    @PrePersist
    private void CreatedAt() {
        createdAt = new Date();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
