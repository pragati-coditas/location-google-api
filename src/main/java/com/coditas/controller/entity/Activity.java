package com.coditas.controller.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @author Harshal Patil
 */
@Entity
@Table(name = "activity")
public class Activity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String totalPhotosCount;

    @Column(columnDefinition="tinyint(1) default 0")
    private boolean frontFacing;

    @Column(columnDefinition="tinyint(1) default 0")
    private boolean leftFacing;

    @Column(columnDefinition="tinyint(1) default 0")
    private boolean rightFacing;

    @Column(columnDefinition="tinyint(1) default 0")
    private boolean bottomFacing;

    @Column(columnDefinition="tinyint(1) default 0")
    private boolean topFacing;

    @Column(columnDefinition="tinyint(1) default 0")
    private boolean rearFacing;

    @Column(columnDefinition="tinyint(1) default 0")
    private boolean priceImage;

    @JsonIgnore
    @OneToOne
    private Product product;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId", nullable=false)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTotalPhotosCount() {
        return totalPhotosCount;
    }

    public void setTotalPhotosCount(String totalPhotosCount) {
        this.totalPhotosCount = totalPhotosCount;
    }

    public Boolean getFrontFacing() {
        return frontFacing;
    }

    public void setFrontFacing(Boolean frontFacing) {
        this.frontFacing = frontFacing;
    }

    public Boolean getLeftFacing() {
        return leftFacing;
    }

    public void setLeftFacing(Boolean leftFacing) {
        this.leftFacing = leftFacing;
    }

    public Boolean getRightFacing() {
        return rightFacing;
    }

    public void setRightFacing(Boolean rightFacing) {
        this.rightFacing = rightFacing;
    }

    public Boolean getBottomFacing() {
        return bottomFacing;
    }

    public void setBottomFacing(Boolean bottomFacing) {
        this.bottomFacing = bottomFacing;
    }

    public Boolean getTopFacing() {
        return topFacing;
    }

    public void setTopFacing(Boolean topFacing) {
        this.topFacing = topFacing;
    }

    public Boolean getRearFacing() {
        return rearFacing;
    }

    public void setRearFacing(Boolean rearFacing) {
        this.rearFacing = rearFacing;
    }

    public boolean getPriceImage() {
        return priceImage;
    }

    public void setPriceImage(boolean priceImage) {
        this.priceImage = priceImage;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
