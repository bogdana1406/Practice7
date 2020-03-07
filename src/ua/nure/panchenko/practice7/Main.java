package ua.nure.panchenko.practice7;

import ua.nure.panchenko.practice7.controller.DOMController;
import ua.nure.panchenko.practice7.controller.SAXController;
import ua.nure.panchenko.practice7.controller.STAXController;
import ua.nure.panchenko.practice7.entity.Gems;
import ua.nure.panchenko.practice7.util.Sorter;

public class Main {
    public static void usage() {
        System.out.println("java ua.nure.panchenko.practice7.Main xmlFileName");
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            usage();
            return;
        }

        String xmlFileName = args[0];
        System.out.println("Input ==> " + xmlFileName);

        ////////////////////////////////////////////////////////
        // DOM
        ////////////////////////////////////////////////////////

        // get
        DOMController domController = new DOMController(xmlFileName);
        domController.parse(true);
        Gems gems = domController.getGems();

        // sort (case 1)
        Sorter.sortGemsByGemName(gems);

        // save
        String outputXmlFile = "output.dom.xml";
        DOMController.saveToXML(gems, outputXmlFile);
        System.out.println("Output ==> " + outputXmlFile);

        ////////////////////////////////////////////////////////
        // SAX
        ////////////////////////////////////////////////////////

        // get
        SAXController saxController = new SAXController(xmlFileName);
        saxController.parse(true);
        gems = saxController.getGems();

        // sort  (case 2)
        Sorter.setSortGemsByGemValue(gems);

        // save
        outputXmlFile = "output.sax.xml";

        // other way:
        DOMController.saveToXML(gems, outputXmlFile);
        System.out.println("Output ==> " + outputXmlFile);

        ////////////////////////////////////////////////////////
        // StAX
        ////////////////////////////////////////////////////////

        // get
        STAXController staxController = new STAXController(xmlFileName);
        staxController.parse();
        gems = staxController.getGems();

        // sort  (case 3)
        Sorter.setSortGemsByGemFacets(gems);

        // save
        outputXmlFile = "output.stax.xml";
        DOMController.saveToXML(gems, outputXmlFile);
        System.out.println("Output ==> " + outputXmlFile);
    }
}
