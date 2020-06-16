package com.samit1.sysamit;

public class Aduan {

    private String dateComplaint;
    private String cusrName;
    private String statusComplaint;
    private String title;
    private String description;
    private String itemType;
    private int priority;
    private String itdescription;

    public Aduan() {
        //empty constructor needed
    }

    public Aduan(String dateComplaint, String cusrName, String statusComplaint, String title, String description, String itemType, int priority, String itdescription){
        this.dateComplaint = dateComplaint;
        this.cusrName = cusrName;
        this.statusComplaint = statusComplaint;
        this.title = title;
        this.description = description;
        this.itemType = itemType;
        this.priority = priority;
        this.itdescription = itdescription;
    }

    public String getDateComplaint() {
        return dateComplaint;
    }

    public String getCusrName() {
        return cusrName;
    }

    public String getStatusComplaint() {
        return statusComplaint;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getItemType() {
        return itemType;
    }

    public int getPriority() {
        return priority;
    }

    public String getItdescription() {
        return itdescription;
    }
}
