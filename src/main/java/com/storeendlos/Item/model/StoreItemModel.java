package com.storeendlos.Item.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.storeendlos.AuditingAndResponse.Audit;
import com.storeendlos.indent.Model.Indent;
import com.storeendlos.issue.model.IssueItem;
import com.storeendlos.productCategory.model.ProductCategory;
import com.storeendlos.unit.model.Unit;
import com.storeendlos.user.model.User;
import com.storeendlos.vendor.model.VendorModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "item")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StoreItemModel  extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private String itemName;
    private String itemDescription;
    private String sourceName;
    private LocalDate expiryDate;
    private int quantity;
    private Double tax;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "p_id"),name = "p_id",referencedColumnName ="pId")
    @JsonIgnoreProperties("itemdata")
    private ProductCategory productCategory;
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(foreignKey = @ForeignKey(name = "u_id"), name = "u_id", referencedColumnName = "uid")
    @JsonIgnoreProperties({"itemunit"})
    private Unit unit;



    @OneToMany(mappedBy = "storeItemModel",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"storeItemModel","emp","issueItemsData"})
    private Set<IssueItem> issueItem;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "emp_id"), name = "emp_id", referencedColumnName = "user_profile_id")
    @JsonIgnoreProperties({"itemModelSet","issueItemsData","itemRequest","response","hrModel","familyDetails","userExperienceData"
            ,"passwordEntity","userQualificationData","roles","indentData"})
    private User employe;
    @OneToMany(mappedBy = "storeItem",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"storeItem","employee"})
    private Set<Indent> itemRequest;
    @ManyToMany(mappedBy = "itemData")
    @JsonIgnoreProperties({"itemData"})
    private Set<VendorModel> vendorDate=new HashSet<>();

    public Set<Indent> getItemRequest() {
        return itemRequest;
    }

    public void setItemRequest(Set<Indent> itemRequest) {
        this.itemRequest = itemRequest;
    }

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

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Set<VendorModel> getVendorDate() {
        return vendorDate;
    }

    public void setVendorDate(Set<VendorModel> vendorDate) {
        this.vendorDate = vendorDate;
    }

    public Set<IssueItem> getIssueItem() {
        return issueItem;
    }

    public void setIssueItem(Set<IssueItem> issueItem) {
        this.issueItem = issueItem;
    }

    public User getEmploye() {
        return employe;
    }

    public void setEmploye(User employe) {
        this.employe = employe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreItemModel that = (StoreItemModel) o;
        return quantity == that.quantity && Objects.equals(itemId, that.itemId) && Objects.equals(itemName, that.itemName) && Objects.equals(itemDescription, that.itemDescription) && Objects.equals(sourceName, that.sourceName) && Objects.equals(expiryDate, that.expiryDate) && Objects.equals(tax, that.tax) && Objects.equals(createdAt, that.createdAt) && Objects.equals(productCategory, that.productCategory) && Objects.equals(unit, that.unit) && Objects.equals(vendorDate, that.vendorDate) && Objects.equals(issueItem, that.issueItem) && Objects.equals(employe, that.employe) && Objects.equals(itemRequest, that.itemRequest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemName, itemDescription, sourceName, expiryDate, quantity, tax, createdAt, productCategory, unit, vendorDate, issueItem, employe, itemRequest);
    }

    @Override
    public String toString() {
        return "StoreItemModel{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", expiryDate=" + expiryDate +
                ", quantity=" + quantity +
                ", tax=" + tax +
                ", createdAt=" + createdAt +
//                ", productCategory=" + productCategory +
//                ", unit=" + unit +
//                ", issueItem=" + issueItem +
//                ", employe=" + employe +
//                ", itemRequest=" + itemRequest +
//                ", vendorDate=" + vendorDate +
                '}';
    }
}
