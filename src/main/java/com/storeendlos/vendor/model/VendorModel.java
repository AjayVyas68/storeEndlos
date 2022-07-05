package com.storeendlos.vendor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.storeendlos.AuditingAndResponse.Audit;
import com.storeendlos.Item.model.StoreItemModel;
import com.storeendlos.indent.Model.Indent;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "vendor_details")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class VendorModel extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    private  Long vid;
    private String vendorName;
    private String vendorAddress;
   // @Column(nullable = false)
    private String vendorPinCode;
 //   @Column(unique = true)

    private String vendorCode;
   // @Column(unique = true)
    private String gstNo;
    //@Column(unique = true)
    private String panCardNo;


    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "item_Vendor_details",
            joinColumns = @JoinColumn(name = "vendor_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    @JsonIgnoreProperties({"vendorDate","unit","productCategory","employe","dataVendorAndIndent"})
    private Set<StoreItemModel> itemData=new HashSet<>();
    @OneToMany(mappedBy = "vendorData", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("vendorData")
    @ToString.Exclude
    private Set<Indent> indentList;



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
   public Object addItem(StoreItemModel... storeItemModels)
    {
        this.itemData= Stream.of(storeItemModels).collect(Collectors.toSet());
        this.itemData.forEach(x->x.getVendorDate().add(this));
        //storeItemModel.getVendorDate().add(this);

        return null;
    }


    @Override
    public String toString() {
        return "VendorModel{" +
                "vid=" + vid +
                ", vendorName='" + vendorName + '\'' +
                ", vendorAddress='" + vendorAddress + '\'' +
                ", vendorPinCode='" + vendorPinCode + '\'' +
                ", vendorCode='" + vendorCode + '\'' +
                ", gstNo='" + gstNo + '\'' +
                ", panCardNo='" + panCardNo + '\'' +
                ", createdAt=" + createdAt +
                ", itemData=" + itemData +
                ", indentList=" + indentList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        VendorModel that = (VendorModel) o;
        return vid != null && Objects.equals(vid, that.vid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
