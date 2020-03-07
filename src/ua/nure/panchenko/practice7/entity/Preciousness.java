package ua.nure.panchenko.practice7.entity;

public enum Preciousness {
    PRECIOUS("Precious"),
    SEMIPRECIOUS("Semiprecious");

    private final String preciousVal;

    Preciousness(String preciousVal) {
        this.preciousVal = preciousVal;
    }

    public String getPreciousVal() {
        return preciousVal;
    }
}
