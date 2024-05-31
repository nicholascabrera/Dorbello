package com.dorbello.models;

public class ETAPage {
    private String organization;
    private String group;
    private String child;
    private String parent;
    private int ETA; // parents Estimated Time to Arrival

    public ETAPage(){}

    public String getChild() {
        return child;
    }

    public int getETA() {
        return ETA;
    }

    public String getGroup() {
        return group;
    }

    public String getOrganization() {
        return organization;
    }

    public String getParent() {
        return parent;
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

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
