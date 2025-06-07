package com.trafigura.model;

public class Position {
    private String securityCode;
    private int netQuantity;

    public Position() {
        // Default constructor for Jackson
    }

    public Position(String securityCode, int netQuantity) {
        this.securityCode = securityCode;
        this.netQuantity = netQuantity;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public int getNetQuantity() {
        return netQuantity;
    }

    public void setNetQuantity(int netQuantity) {
        this.netQuantity = netQuantity;
    }
}
