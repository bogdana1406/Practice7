package ua.nure.panchenko.practice7.entity;

public class Weight {
//    private static final String CARAT = "carat";
    private int weightVal;
    private String weightMeasure;

    public void setWeightVal(int weight) {
        this.weightVal = weight;
    }

    public int getWeightVal() {
        return weightVal;
    }

    public String getWeightMeasure() {
        return weightMeasure;
    }

    public void setWeightMeasure(String weightMeasure) {
        this.weightMeasure = weightMeasure;
    }

    @Override
    public String toString() {
        return getWeightVal() + ((getWeightMeasure() != null) ? " [Weight_measure=carat]" : "");
    }
}
