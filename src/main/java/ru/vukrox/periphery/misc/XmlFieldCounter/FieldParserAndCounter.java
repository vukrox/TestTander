package ru.vukrox.periphery.misc.XmlFieldCounter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/***
 * A class which uses XML file with an attribute, parses its values and counts its arithmetic sum
 */

public class FieldParserAndCounter {

    double sum = 0.0;

    //method to parse file, get the values and count the sum
    public void countArithmeticSumOfXml() {
        try {
            File fXmlFile = new File("2.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            //now we have a node list of 1 element in xml file
            NodeList entryList = doc.getElementsByTagName("entry");
            //loop through the xml
            for (int temp = 0; temp < entryList.getLength(); temp++) {
                Node nNode = entryList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    sum = sum + Double.parseDouble(eElement.getAttribute("field"));
                }
            }
            System.out.println("The arithmetic sum of added elements is: " + sum + " .");

        } catch (ParserConfigurationException e) {
            System.out.println("Can't configure parser.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Can't open file.");
            e.printStackTrace();
        } catch (SAXException e) {
            System.out.println("SAXE exception.");
            e.printStackTrace();
        }
    }
}