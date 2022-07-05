package com.storeendlos.indent.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.storeendlos.issue.model.IssueItem;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "usageItem")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UsageItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uId;
    private String deptName;
    private Date created;


    public Date getCreated() {
        return created;
    }
    @PrePersist
    public void setCreated() {
        this.created = new Date();
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "issued_id"),name = "issued_id",referencedColumnName = "issueId")
    @JsonIgnoreProperties("usageItems")
    private IssueItem issuedItem;



    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public IssueItem getIssuedItem() {
        return issuedItem;
    }

    public void setIssuedItem(IssueItem issuedItem) {
        this.issuedItem = issuedItem;
    }



    @Override
    public String toString() {
        return "UsageItem{" +
                "uId=" + uId +
                ", deptName='" + deptName + '\'' +
                ", created=" + created +
                ", issuedItem=" + issuedItem.getIssueId() ;

    }
}
