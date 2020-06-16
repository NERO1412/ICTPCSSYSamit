package com.samit1.sysamit;

import com.google.firebase.firestore.Exclude;

public class Inventori {
    private String brgId;
    private String brgType;
    private String brgCat;
    private String brgModel;
    private String brgSn;
    private String brgLocated;
    private String brgDesc;

    public  Inventori() {
        //public no-arg constructor neeeded
    }

    @Exclude
    public String getBrgId() {
        return brgId;
    }

    public void setBrgId(String brgId) {
        this.brgId = brgId;
    }

    public Inventori(String brgType, String brgCat, String brgModel, String brgSn, String brgLocated, String brgDesc){
        this.brgType = brgType;
        this.brgCat = brgCat;
        this.brgModel = brgModel;
        this.brgSn = brgSn;
        this.brgLocated = brgLocated;
        this.brgDesc = brgDesc;
    }

    public String getBrgType(){
        return  brgType;
    }

    public String getBrgCat(){
        return  brgCat;
    }

    public String getBrgModel(){
        return  brgModel;
    }
    public String getBrgSn(){
        return  brgSn;
    }
    public String getBrgLocated(){
        return  brgLocated;
    }
    public String getBrgDesc(){
        return  brgDesc;
    }
}
