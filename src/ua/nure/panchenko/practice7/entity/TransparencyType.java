package ua.nure.panchenko.practice7.entity;

public class TransparencyType {

    private double transparency;

    private String transMeasure;

    public double getTransparency() {
        return transparency;
    }

    public void setTransparency(double transparency) {
        this.transparency = transparency;
    }

    public void setTransMeasure(String transMeasure) {
        this.transMeasure = transMeasure;
    }

    public String getTransMeasure() {
        return transMeasure;
    }

    @Override
    public String toString() {
        return getTransparency() + ((getTransMeasure() != null) ? " [Trans_measure=percent]" : "");
    }
}
