package ua.nure.panchenko.practice7.controller;

import org.xml.sax.helpers.DefaultHandler;
import ua.nure.panchenko.practice7.constants.Constants;
import ua.nure.panchenko.practice7.constants.Names;
import ua.nure.panchenko.practice7.entity.Gem;
import ua.nure.panchenko.practice7.entity.Gems;
import ua.nure.panchenko.practice7.entity.TransparencyType;
import ua.nure.panchenko.practice7.entity.VisualParameter;
import ua.nure.panchenko.practice7.entity.Weight;


import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.events.EndElement;

import javax.xml.transform.stream.StreamSource;

public class STAXController extends DefaultHandler {

    private String xmlFileName;

    // main container
    private Gems gems;

    public Gems getGems() {
        return gems;
    }

    public STAXController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    /**
     * Parses XML document with StAX (based on event reader). There is no validation during the
     * parsing.
     */
    public void parse() throws XMLStreamException {

        Gem gem = null;
        VisualParameter visualParameter = null;
        TransparencyType transparencyType = null;
        Weight weight = null;

        // current element name holder
        String currentElement = null;

        XMLInputFactory factory = XMLInputFactory.newInstance();

        factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);

        XMLEventReader reader = factory.createXMLEventReader(
                new StreamSource(xmlFileName));

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            // skip any empty content
            if (event.isCharacters() && event.asCharacters().isWhiteSpace()) {
                continue;
            }

            // handler for start tags
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                currentElement = startElement.getName().getLocalPart();

                if (Names.GEMS.equals(currentElement)) {
                    gems = new Gems();
                    continue;
                }

                if (Names.GEM.equals(currentElement)) {
                    gem = new Gem();
                    continue;
                }

                if (Names.VISUAL_PARAMETERS.equals(currentElement)) {
                    visualParameter = new VisualParameter();
                    continue;
                }

                if (Names.VALUE.equals(currentElement)) {
                    weight = new Weight();
                    Attribute attribute = startElement.getAttributeByName(
                            new QName(Names.WEIGHT_MEASURE));
                    if (attribute != null) {
                        weight.setWeightMeasure(attribute.getValue());
                    }
                }

                if (Names.TRANSPARENCY.equals(currentElement)) {
                    transparencyType = new TransparencyType();
                    Attribute attribute = startElement.getAttributeByName(
                            new QName(Names.TRANS_MEASURE));
                    if (attribute != null) {
                        transparencyType.setTransMeasure(attribute.getValue());
                    }
                }

            }

            // handler for contents
            if (event.isCharacters()) {
                Characters characters = event.asCharacters();

                if (Names.GEM_NAME.equals(currentElement)) {
                    gem.setGemName(characters.getData());
                    continue;
                }

                if (Names.ORIGIN.equals(currentElement)) {
                    gem.setOrigin(characters.getData());
                    continue;
                }

                if (Names.COLOR.equals(currentElement)) {
                    visualParameter.setColor(characters.getData());
                    continue;
                }

                if (Names.FACETS.equals(currentElement)) {
                    visualParameter.setFacets(Integer.parseInt(characters.getData()));
                    continue;
                }

                if (Names.PRECIOUSNESS.equals(currentElement)) {
                    gem.setPreciousness(characters.getData());
                    continue;
                }

                if (Names.VALUE.equals(currentElement)) {
                    weight.setWeightVal(Integer.parseInt(characters.getData()));
                    continue;
                }

                if (Names.TRANSPARENCY.equals(currentElement)) {
                    transparencyType.setTransparency(Double.parseDouble(characters.getData()));
                    continue;
                }

            }

            // handler for end tags
            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                String localName = endElement.getName().getLocalPart();

                if (Names.GEM.equals(localName)) {
                    gems.getGemList().add(gem);
                    continue;
                }

                if (Names.VISUAL_PARAMETERS.equals(localName)) {
                    gem.setVisualParameter(visualParameter);
                }

                if (Names.VALUE.equals(localName)) {
                    gem.setWeight(weight);
                }

                if (Names.TRANSPARENCY.equals(localName)) {
                    visualParameter.setTransparency(transparencyType);
                }

            }
        }
        reader.close();
    }

    public static void main(String[] args) throws Exception {

        // try to parse (valid) XML file (success)
        STAXController staxContr = new STAXController(Constants.VALID_XML_FILE);
        staxContr.parse(); // <-- do parse (success)

        // obtain container
        Gems gems = staxContr.getGems();

        // we have Test object at this point:
        System.out.println("====================================");
        System.out.print("Here is the test: \n" + gems);
        System.out.println("====================================");
    }
}
