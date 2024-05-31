package com.dorbello.models;

/**
 * The page class is intended to hold all information on a certain parent.
 */
public class Page {
    private String organization;
    private String group;
    private String child;
    private String parent;
    private String APT;         //assigned pick up time
    private String location;    //assigned pick up location
    private int ETA;            //parents Estimated Time to Arrival

    public Page(){}

    public Page(String organization, String group, String child, String parent, String APT, String location, int ETA){
        this.organization = organization;
        this.group = group;
        this.child = child;
        this.parent = parent;
        this.APT = APT;
        this.location = location;
        this.ETA = ETA;
    }

    public String getAPT() {
        return APT;
    }

    public String getChild() {
        return child;
    }

    public int getETA() {
        return ETA;
    }

    public String getGroup() {
        return group;
    }

    public String getLocation() {
        return location;
    }
    
    public String getOrganization() {
        return organization;
    }

    public String getParent() {
        return parent;
    }

    public void setAPT(String aPT) {
        APT = aPT;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public void setETA(int eTA) {
        ETA = eTA;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
