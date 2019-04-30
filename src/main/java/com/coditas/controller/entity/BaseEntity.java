package com.coditas.controller.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Harshal Patil
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {
            "createdDate",
            "updatedDate",
            "hibernateLazyInitializer",
            "handler"
        })
public abstract class BaseEntity implements Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false,updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(insertable = false)
    private Date updatedDate;

    @PrePersist
    public void setCreatedDate(){
        this.setCreatedDate(new Date());
    }

    @PreUpdate
    public void setUpdatedDate(){
        this.setUpdatedDate(new Date());
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    private void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    private void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
