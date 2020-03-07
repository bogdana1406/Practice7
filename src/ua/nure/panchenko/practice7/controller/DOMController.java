package ua.nure.panchenko.practice7.controller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import ua.nure.panchenko.practice7.constants.Constants;
import ua.nure.panchenko.practice7.constants.Names;
import ua.nure.panchenko.practice7.entity.Gem;
import ua.nure.panchenko.practice7.entity.Gems;
import ua.nure.panchenko.practice7.entity.VisualParameter;
import ua.nure.panchenko.practice7.entity.Weight;
import ua.nure.panchenko.practice7.entity.TransparencyType;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class DOMController {

    private String xmlFileName;

    // main container
    private Gems gems;

    public DOMController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public Gems getGems() {
        return gems;
    }

    /**
     * Parses XML document.
     *
     * @param validate
     *            If true validate XML document against its XML schema.
     */

    public void parse(boolean validate)
            throws ParserConfigurationException, SAXException, IOException {

        // obtain DOM parser
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // set properties for Factory

        // XML document contains namespaces
        dbf.setNamespaceAware(true);

        // make parser validating
        if (validate) {
            // turn validation on
            dbf.setFeature(Constants.FEATURE_TURN_VALIDATION_ON, true);

            // turn on xsd validation
            dbf.setFeature(Constants.FEATURE_TURN_SCHEMA_VALIDATION_ON, true);
        }

        DocumentBuilder db = dbf.newDocumentBuilder();

        // set error handler
        db.setErrorHandler(new DefaultHandler() {
            @Override
            public void error(SAXParseException e) throws SAXException {
                // throw exception if XML document is NOT valid
                throw e;
            }
        });

        // parse XML document
        Document document = db.parse(xmlFileName);

        // get root element
        Element root = document.getDocumentElement();

        // create container
        gems = new Gems();

        // obtain questions nodes
        NodeList gemNodes = root
                .getElementsByTagName(Names.GEM);

        // process questions nodes
        for (int j = 0; j < gemNodes.getLength(); j++) {
            Gem gem = getGem(gemNodes.item(j));
            // add question to container
            gems.getGemList().add(gem);
        }
    }

    /**
     * Extracts gem object from the gem XML node.
     *
     * @param gemNode
     *            Gem node.
     * @return Gem object.
     */

    private static Gem getGem(Node gemNode) {
        Gem gem = new Gem();
        Element gemElement = (Element) gemNode;

        // process gem name
        Node gemNameNode = gemElement.getElementsByTagName(Names.GEM_NAME)
                .item(0);
        // set gem name
        gem.setGemName(gemNameNode.getTextContent());

        // process gem origin
        Node gemOriginNode = gemElement.getElementsByTagName(Names.ORIGIN)
                .item(0);

        // set gem origin
        gem.setOrigin(gemOriginNode.getTextContent());


        // process gem Preciousness
        Node gemPreciousnessNode = gemElement.getElementsByTagName(Names.PRECIOUSNESS)
                .item(0);

        // set gem Preciousness
        gem.setPreciousness(gemPreciousnessNode.getTextContent());

        Node gemWeightNode = gemElement.getElementsByTagName(Names.VALUE)
                .item(0);
        Weight weight = getWeight(gemWeightNode);
        // set gem Preciousness
        gem.setWeight(weight);
        // process gem visual_parameters
        Node visualParamNode = gemElement.getElementsByTagName(Names.VISUAL_PARAMETERS)
                .item(0);
        VisualParameter visualParameter = getVisualParam(visualParamNode);
        // set gem Preciousness
        gem.setVisualParameter(visualParameter);

        return gem;
    }

    private static Weight getWeight(Node weightNode) {
        Weight weight = new Weight();
        Element weightElement = (Element) weightNode;

        // process atr
        String weightMeasure = weightElement.getAttribute(Names.WEIGHT_MEASURE);
        weight.setWeightMeasure(weightMeasure);

        // process val
        int value = Integer.parseInt(weightElement.getTextContent());
        weight.setWeightVal(value);

        return weight;
    }

    private static VisualParameter getVisualParam(Node visualParamNode) {
        VisualParameter visualParameter = new VisualParameter();
        Element visualParamElement = (Element) visualParamNode;

        // process visual_parameter color
        Node colorNode = visualParamElement.getElementsByTagName(Names.COLOR)
                .item(0);

        // set visual_parameter color
        visualParameter.setColor(colorNode.getTextContent());

        // process visual_parameter facets
        Node facetsNode = visualParamElement.getElementsByTagName(Names.FACETS)
                .item(0);

        // set visual_parameter facets
        visualParameter.setFacets(Integer.parseInt(facetsNode.getTextContent()));

    // process visual_parameter transparency
        Node transparencyNode = visualParamElement.getElementsByTagName(Names.TRANSPARENCY)
                .item(0);
        TransparencyType transparencyType = getTransparencyType(transparencyNode);
        // set gem transparency
        visualParameter.setTransparency(transparencyType);

        return visualParameter;
    }

    private static TransparencyType getTransparencyType(Node transparencyNode) {
        TransparencyType transparency = new TransparencyType();
        Element transparencyElement = (Element) transparencyNode;

        // process attr
        String transMeasure = transparencyElement.getAttribute(Names.TRANS_MEASURE);
        transparency.setTransMeasure(transMeasure);

        // process content
        double value = Double.parseDouble(transparencyElement.getTextContent());
        transparency.setTransparency(value);

        return transparency;
    }

    /**
     * Creates and returns DOM of the Test container.
     *
     * @param gems
     *            Test object.
     * @throws ParserConfigurationException
     */


//    +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    public static Document getDocument(Gems gems) throws ParserConfigurationException {

        // obtain DOM parser
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // set properties for Factory

        // XML document contains namespaces
        dbf.setNamespaceAware(true);

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();

        // create root element
        Element gemsElement = document.createElement(Names.GEMS);

        // add root element
        document.appendChild(gemsElement);

        // add questions elements
        for (Gem gem : gems.getGemList()) {

            // add gem
            Element qemElement = document.createElement(Names.GEM);
            gemsElement.appendChild(qemElement);

            // add gemName
            Element gemNameElement =
                    document.createElement(Names.GEM_NAME);
            gemNameElement.setTextContent(gem.getGemName());
            qemElement.appendChild(gemNameElement);

            // add preciousness
            Element preciousnessElement =
                    document.createElement(Names.PRECIOUSNESS);
            preciousnessElement.setTextContent(gem.getPreciousness());
            qemElement.appendChild(preciousnessElement);
            // add origin
            Element gemOriginElement =
                    document.createElement(Names.ORIGIN);
            gemOriginElement.setTextContent(gem.getOrigin());
            qemElement.appendChild(gemOriginElement);

            // add visual parameters
            VisualParameter visualParameter = gem.getVisualParameter();
            Element visualParamElement =
                    document.createElement(Names.VISUAL_PARAMETERS);

            qemElement.appendChild(visualParamElement);

            // add color to visual parameters
            Element colorElement =
                    document.createElement(Names.COLOR);
            colorElement.setTextContent(visualParameter.getColor());
            visualParamElement.appendChild(colorElement);

            // add transparency to visual parameters
            Element transparencyElement = document.createElement(Names.TRANSPARENCY);
            TransparencyType transparencyType = visualParameter.getTransparency();
            transparencyElement.setTextContent(String.valueOf(transparencyType.getTransparency()));
            if (transparencyType.getTransMeasure() != null) {
                transparencyElement.setAttribute(Names.TRANS_MEASURE, "percent");
            }
            visualParamElement.appendChild(transparencyElement);

            // add facets to visual parameters
            Element facetsElement =
                    document.createElement(Names.FACETS);
            facetsElement.setTextContent(String.valueOf(visualParameter.getFacets()));
            visualParamElement.appendChild(facetsElement);

            // add value
            Element valueElement = document.createElement(Names.VALUE);
            Weight weight = gem.getWeight();
            valueElement.setTextContent(String.valueOf(weight.getWeightVal()));
            if (weight.getWeightMeasure() != null) {
                valueElement.setAttribute(Names.WEIGHT_MEASURE, "carat");
            }
            qemElement.appendChild(valueElement);

        }

        return document;
    }

    /**
     * Saves Test object to XML file.
     *
     * @param gems
     *            Test object to be saved.
     * @param xmlFileName
     *            Output XML file name.
     */
    public static void saveToXML(Gems gems, String xmlFileName)
            throws ParserConfigurationException, TransformerException {
        // Gems -> DOM -> XML
        saveToXML(getDocument(gems), xmlFileName);
    }

    /**
     * Save DOM to XML.
     *
     * @param document
     *            DOM to be saved.
     * @param xmlFileName
     *            Output XML file name.
     */

    public static void saveToXML(Document document, String xmlFileName)
            throws TransformerException {

        StreamResult result = new StreamResult(new File(xmlFileName));

        // set up transformation
        TransformerFactory tf = TransformerFactory.newInstance();
        javax.xml.transform.Transformer t = tf.newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");

        // run transformation
        t.transform(new DOMSource(document), result);
    }

    public static void main(String[] args) throws Exception {
        // try to parse NOT valid XML document with validation on (failed)
        DOMController domContr = new DOMController(Constants.INVALID_XML_FILE);
        try {
            // parse with validation (failed)
            domContr.parse(true);
        } catch (SAXException ex) {
            System.err.println("====================================");
            System.err.println("XML not valid");
            System.err.println("Gems object --> " + domContr.getGems());
            System.err.println("====================================");
        }

        // try to parse NOT valid XML document with validation off (success)
        domContr.parse(false);

        // we have Test object at this point:
        System.out.println("====================================");
        System.out.print("Here is the test: \n" + domContr.getGems());
        System.out.println("====================================");

        // save test in XML file
        Gems gems = domContr.getGems();
        DOMController.saveToXML(gems, Constants.INVALID_XML_FILE + ".dom-result.xml");
    }

}
