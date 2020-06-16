package com.samit1.sysamit;

public class Model {
    String itemID, itemType, itemModel, itemSN, itemDepartment, itemUsedby;

    public Model() {
    }

    public Model(String itemID, String itemType, String itemModel, String itemSN, String itemDepartment, String itemUsedby) {
        this.itemID = itemID;
        this.itemType = itemType;
        this.itemModel = itemModel;
        this.itemSN = itemSN;
        this.itemDepartment = itemDepartment;
        this.itemUsedby = itemUsedby;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemId) {
        this.itemID = itemID;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemModel() {
        return itemModel;
    }

    public void setItemModel(String itemModel) {
        this.itemModel = itemModel;
    }

    public String getItemSN() {
        return itemSN;
    }

    public void setItemSN(String itemSN) {
        this.itemSN = itemSN;
    }

    public String getItemDepartment() {
        return itemDepartment;
    }

    public void setItemDepartment(String itemDepartment) {
        this.itemDepartment = itemDepartment;
    }

    public String getItemUsedby() {
        return itemUsedby;
    }

    public void setItemUsedby(String itemUsedby) {
        this.itemUsedby = itemUsedby;
    }
}
