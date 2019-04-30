package com.coditas.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class ActivityDTO {

    private String upc;

    private String totalPhotosCount;

    private boolean frontFacing;

    private boolean leftFacing;

    private boolean rightFacing;

    private boolean bottomFacing;

    private boolean topFacing;

    private boolean rearFacing;

    private boolean priceImage;

    private Date createdDate;

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getTotalPhotosCount() {
        return totalPhotosCount;
    }

    public void setTotalPhotosCount(String totalPhotosCount) {
        this.totalPhotosCount = totalPhotosCount;
    }

    public boolean isFrontFacing() {
        return frontFacing;
    }

    public void setFrontFacing(boolean frontFacing) {
        this.frontFacing = frontFacing;
    }

    public boolean isLeftFacing() {
        return leftFacing;
    }

    public void setLeftFacing(boolean leftFacing) {
        this.leftFacing = leftFacing;
    }

    public boolean isRightFacing() {
        return rightFacing;
    }

    public void setRightFacing(boolean rightFacing) {
        this.rightFacing = rightFacing;
    }

    public boolean isBottomFacing() {
        return bottomFacing;
    }

    public void setBottomFacing(boolean bottomFacing) {
        this.bottomFacing = bottomFacing;
    }

    public boolean isTopFacing() {
        return topFacing;
    }

    public void setTopFacing(boolean topFacing) {
        this.topFacing = topFacing;
    }

    public boolean isRearFacing() {
        return rearFacing;
    }

    public void setRearFacing(boolean rearFacing) {
        this.rearFacing = rearFacing;
    }

    public boolean isPriceImage() {
        return priceImage;
    }

    public void setPriceImage(boolean priceImage) {
        this.priceImage = priceImage;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
