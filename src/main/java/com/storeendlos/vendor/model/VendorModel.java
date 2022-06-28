package com.storeendlos.vendor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.storeendlos.AuditingAndResponse.Audit;
import com.storeendlos.Item.model.StoreItemModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "vendor_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorModel extends Audit<String   > {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    private  Long vid;
    private String vendorName;
    private String vendorAddress;
    @Column(unique = true)
    private String vendorPinCode;
    @Column(unique = true)

    private String vendorCode;
    @Column(unique = true)
    private String gstNo;
    @Column(unique = true)
    private String panCardNo;


    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "item_Vendor_details",
            joinColumns = @JoinColumn(name = "vendor_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    @JsonIgnoreProperties({"vendorDate","unit","productCategory","employe","dataVendorAndIndent"})
    private Set<StoreItemModel> itemData=new HashSet<>();

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
