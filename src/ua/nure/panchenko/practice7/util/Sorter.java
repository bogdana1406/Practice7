package ua.nure.panchenko.practice7.util;

import ua.nure.panchenko.practice7.constants.Constants;
import ua.nure.panchenko.practice7.controller.DOMController;
import ua.nure.panchenko.practice7.entity.Gem;
import ua.nure.panchenko.practice7.entity.Gems;

import java.util.Collections;
import java.util.Comparator;

public class Sorter {
    // //////////////////////////////////////////////////////////
    // these are comparators
    // //////////////////////////////////////////////////////////

    /**
     * Sorts gems by names text
     */
    public static final Comparator<Gem> SORT_GEMS_BY_GEM_NAME = new Comparator<Gem>() {
        @Override
        public int compare(Gem o1, Gem o2) {
            return o1.getGemName().compareTo(o2.getGemName());
        }
    };

    /**
     * Sorts gems by values number.
     */
    public static final Comparator<Gem> SORT_GEMS_BY_GEM_VALUE = new Comparator<Gem>() {
        @Override
        public int compare(Gem o1, Gem o2) {
            return o1.getWeight().getWeightVal() - o2.getWeight().getWeightVal();
        }
    };

    public static final Comparator<Gem> SORT_GEMS_BY_GEM_FACETS = new Comparator<Gem>() {
        @Override
        public int compare(Gem o1, Gem o2) {
            return o1.getVisualParameter().getFacets() - o2.getVisualParameter().getFacets();
        }
    };
    // //////////////////////////////////////////////////////////
    // these methods take Test object and sort it
    // with according comparator
    // //////////////////////////////////////////////////////////
    public static final void sortGemsByGemName(Gems gems) {
        Collections.sort(gems.getGemList(), SORT_GEMS_BY_GEM_NAME);
    }

    public static final void setSortGemsByGemValue(Gems gems) {
        Collections.sort(gems.getGemList(), SORT_GEMS_BY_GEM_VALUE);
    }

    public static final void setSortGemsByGemFacets(Gems gems) {
        Collections.sort(gems.getGemList(), SORT_GEMS_BY_GEM_FACETS);
    }

    public static void main(String[] args) throws Exception {
        // Test.xml --> Test object
        DOMController domController = new DOMController(
                Constants.VALID_XML_FILE);
        domController.parse(false);
        Gems gems = domController.getGems();

        System.out.println("====================================");
        System.out.println(gems);
        System.out.println("====================================");

        System.out.println("====================================");
        Sorter.sortGemsByGemName(gems);
        System.out.println(gems);
        System.out.println("====================================");

        System.out.println("====================================");
        Sorter.setSortGemsByGemValue(gems);
        System.out.println(gems);
    }

}
