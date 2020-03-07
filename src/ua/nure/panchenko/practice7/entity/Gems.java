package ua.nure.panchenko.practice7.entity;

import java.util.ArrayList;
import java.util.List;

public class Gems {
    private List<Gem> gemList;

    public List<Gem> getGemList() {
        if (gemList == null) {
            gemList = new ArrayList<>();
        }
        return gemList;
    }

    @Override
    public String toString() {
        if (gemList == null || gemList.isEmpty()) {
            return "Gems contains no gems";
        }
        StringBuilder result = new StringBuilder();
        for (Gem gem : gemList) {
            result.append(gem).append('\n');
        }
        return result.toString();
    }
}
