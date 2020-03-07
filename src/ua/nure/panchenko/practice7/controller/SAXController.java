package ua.nure.panchenko.practice7.controller;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ua.nure.panchenko.practice7.constants.Constants;
import ua.nure.panchenko.practice7.constants.Names;
import ua.nure.panchenko.practice7.entity.Gem;
import ua.nure.panchenko.practice7.entity.Gems;
import ua.nure.panchenko.practice7.entity.TransparencyType;
import ua.nure.panchenko.practice7.entity.VisualParameter;
import ua.nure.panchenko.practice7.entity.Weight;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SAXController extends DefaultHandler {

    private String xmlFileName;

    // current element name holder
    private String currentElement;

    // main container
    private Gems gems;

    private Gem gem;

    private VisualParameter visualParameter;

    private TransparencyType transparencyType;

    private Weight weight;

    public SAXController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    /**
     * Parses XML document.
     *
     * @param validate
     *            If true validate XML document against its XML schema. With
     *            this parameter it is possible make parser validating.
     */

    public void parse(boolean validate)
            throws ParserConfigurationException, SAXException, IOException {

        // obtain sax parser factory
        SAXParserFactory factory = SAXParserFactory.newInstance();

        // XML document contains namespaces
        factory.setNamespaceAware(true);

        // set validation
        if (validate) {
            factory.setFeature(Constants.FEATURE_TURN_VALIDATION_ON, true);
            factory.setFeature(Constants.FEATURE_TURN_SCHEMA_VALIDATION_ON, true);
        }

        SAXParser parser = factory.newSAXParser();
        parser.parse(xmlFileName, this);
    }

    // ///////////////////////////////////////////////////////////
    // ERROR HANDLER IMPLEMENTATION
    // ///////////////////////////////////////////////////////////

    @Override
    public void error(org.xml.sax.SAXParseException e) throws SAXException {
        // if XML document not valid just throw exception
        throw e;
    }

    public Gems getGems() {
        return gems;
    }

    // ///////////////////////////////////////////////////////////
    // CONTENT HANDLER IMPLEMENTATION
    // ///////////////////////////////////////////////////////////

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        currentElement = localName;

        if (Names.GEMS.equals(currentElement)) {
            gems = new Gems();
            return;
        }

        if (Names.GEM.equals(currentElement)) {
            gem = new Gem();
            return;
        }

        if (Names.VISUAL_PARAMETERS.equals(currentElement)) {
            visualParameter = new VisualParameter();
            return;
        }

        if (Names.TRANSPARENCY.equals(currentElement)) {
            transparencyType = new TransparencyType();
            if (attributes.getLength() > 0) {
                transparencyType.setTransMeasure(attributes.getValue(uri, Names.TRANS_MEASURE));
            }
            return;
        }

        if (Names.VALUE.equals(currentElement)) {
            weight = new Weight();
            if (attributes.getLength() > 0) {
                weight.setWeightMeasure(attributes.getValue(uri, Names.WEIGHT_MEASURE));
            }
            return;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        String elementText = new String(ch, start, length).trim();

        // return if content is empty
        if (elementText.isEmpty()) {
            return;
        }

        if (Names.GEM_NAME.equals(currentElement)) {
            gem.setGemName(elementText);
            return;
        }

        if (Names.COLOR.equals(currentElement)) {
            visualParameter.setColor(elementText);
            return;
        }

        if (Names.FACETS.equals(currentElement)) {
            visualParameter.setFacets(Integer.parseInt(elementText));
            return;
        }

        if (Names.PRECIOUSNESS.equals(currentElement)) {
            gem.setPreciousness(elementText);
            return;
        }

        if (Names.ORIGIN.equals(currentElement)) {
            gem.setOrigin(elementText);
            return;
        }

        if (Names.TRANSPARENCY.equals(currentElement)) {
            transparencyType.setTransparency(Double.parseDouble(elementText));
            return;
        }

        if (Names.VALUE.equals(currentElement)) {
            weight.setWeightVal(Integer.parseInt(elementText));
            return;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        if (Names.GEM.equals(localName)) {
            // just add question to container
            gems.getGemList().add(gem);
            return;
        }

        if (Names.VISUAL_PARAMETERS.equals(localName)) {
            // just add answer to container
            gem.setVisualParameter(visualParameter);
            return;
        }

        if (Names.TRANSPARENCY.equals(localName)) {
            // just add answer to container
            visualParameter.setTransparency(transparencyType);
            return;
        }

        if (Names.VALUE.equals(localName)) {
            // just add answer to container
            gem.setWeight(weight);
            return;
        }
    }

    public static void main(String[] args) throws Exception {

        // try to parse valid XML file (success)
        SAXController saxContr = new SAXController(Constants.VALID_XML_FILE);

        // do parse with validation on (success)
        saxContr.parse(true);

        // obtain container
        Gems test = saxContr.getGems();

        // we have Test object at this point:
        System.out.println("====================================");
        System.out.print("Here is the test: \n" + test);
        System.out.println("====================================");

        // now try to parse NOT valid XML (failed)
        saxContr = new SAXController(Constants.INVALID_XML_FILE);
        try {
            // do parse with validation on (failed)
            saxContr.parse(true);
        } catch (Exception ex) {
            System.err.println("====================================");
            System.err.println("Validation is failed:\n" + ex.getMessage());
            System.err
                    .println("Try to print test object:" + saxContr.getGems());
            System.err.println("====================================");
        }

        // and now try to parse NOT valid XML with validation off (success)
        saxContr.parse(false);

        // we have Test object at this point:
        System.out.println("====================================");
        System.out.print("Here is the test: \n" + saxContr.getGems());
        System.out.println("====================================");
    }

}
