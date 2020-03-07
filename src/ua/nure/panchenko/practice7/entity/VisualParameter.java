package ua.nure.panchenko.practice7.entity;

public class VisualParameter {
    private String color;
    private TransparencyType transparency;
    private int facets;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public TransparencyType getTransparency() {
        return transparency;
    }

    public void setTransparency(TransparencyType transparency) {
        this.transparency = transparency;
    }

    public int getFacets() {
        return facets;
    }

    public void setFacets(int facets) {
        this.facets = facets;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Visual_parameters").append('\n');
        result.append(getColor()).append('\n');
        result.append(getTransparency()).append('\n');
        result.append(getFacets());
        return result.toString();
    }
}
