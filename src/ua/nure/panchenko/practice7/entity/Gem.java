package ua.nure.panchenko.practice7.entity;

public class Gem {
    private String gemName;
    private String origin;
    private String preciousness;
    private VisualParameter visualParameter;
    private Weight weight;

    public String getGemName() {
        return gemName;
    }

    public void setGemName(String gemName) {
        this.gemName = gemName;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getPreciousness() {
        return preciousness;
    }

    public void setPreciousness(String preciousness) {
//        if (isPreciousnessValid(preciousness)) {
            this.preciousness = preciousness;
//        } else this.preciousness = Preciousness.PRECIOUS.toString();
    }

    public VisualParameter getVisualParameter() {
        return visualParameter;
    }

    public void setVisualParameter(VisualParameter visualParameter) {
        this.visualParameter = visualParameter;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

//    boolean isPreciousnessValid(String preciousness) {
//        boolean preciousnessValid = true;
//        try {
//            Preciousness.valueOf(preciousness);
//        } catch (IllegalArgumentException e) {
//            preciousnessValid = false;
//        }
//        return preciousnessValid;
//    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(getGemName()).append('\n');
        result.append(getOrigin()).append('\n');
        result.append(getVisualParameter()).append('\n');
        result.append(getPreciousness()).append('\n');
        result.append(getWeight()).append('\n');

        return result.toString();
    }
}
