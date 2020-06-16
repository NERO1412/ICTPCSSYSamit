package com.samit1.sysamit;

public class ModelInv {

    String AduanDate, AduanTitle, AduanType, AduanStatus, AduanDesc, AduanPrior, AduanBy, AduanRemark;

    public ModelInv() {
    }

    public ModelInv(String aduanDate, String aduanTitle, String aduanType, String aduanStatus, String aduanDesc, String aduanPrior, String aduanBy, String aduanRemark) {
        AduanDate = aduanDate;
        AduanTitle = aduanTitle;
        AduanType = aduanType;
        AduanStatus = aduanStatus;
        AduanDesc = aduanDesc;
        AduanPrior = aduanPrior;
        AduanBy = aduanBy;
        AduanRemark = aduanRemark;
    }

    public String getAduanDate() {
        return AduanDate;
    }

    public void setAduanDate(String aduanDate) {
        AduanDate = aduanDate;
    }

    public String getAduanTitle() {
        return AduanTitle;
    }

    public void setAduanTitle(String aduanTitle) {
        AduanTitle = aduanTitle;
    }

    public String getAduanType() {
        return AduanType;
    }

    public void setAduanType(String aduanType) {
        AduanType = aduanType;
    }

    public String getAduanStatus() {
        return AduanStatus;
    }

    public void setAduanStatus(String aduanStatus) {
        AduanStatus = aduanStatus;
    }

    public String getAduanDesc() {
        return AduanDesc;
    }

    public void setAduanDesc(String aduanDesc) {
        AduanDesc = aduanDesc;
    }

    public String getAduanPrior() {
        return AduanPrior;
    }

    public void setAduanPrior(String aduanPrior) {
        AduanPrior = aduanPrior;
    }

    public String getAduanBy() {
        return AduanBy;
    }

    public void setAduanBy(String aduanBy) {
        AduanBy = aduanBy;
    }

    public String getAduanRemark() {
        return AduanRemark;
    }

    public void setAduanRemark(String aduanRemark) {
        AduanRemark = aduanRemark;
    }
}
