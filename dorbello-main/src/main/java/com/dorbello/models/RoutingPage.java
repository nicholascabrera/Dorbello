package com.dorbello.models;

public class RoutingPage {
    private String organization;
    private String group;
    private String child;
    private String parent;
    private String APT;         //assigned pick up time
    private String location;    //assigned pick up location

    public String getAPT() {
        return APT;
    }

    public String getChild() {
        return child;
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
