package net.seehope.common;

public enum InvoiceStatus {

COMMONINVOIVE("3"),NOINVOICE("2"),SPECIALINVOICE("4");
    public String type;
    InvoiceStatus(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
