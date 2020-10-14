package ru.vukrox.periphery.data.manipulation;

import org.w3c.dom.Document;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.Collection;
import java.util.List;

/***
 * "XmlFileGenerator" - takes a List<> which is extracted from the DB(in EntityDao -> getDataFromDb)
 * and transforms it into 1.xml.
 */
public class XmlFileGenerator {

    //input a document and get an XML in the end --> store the XML into the file System
    public void saveDataToXmlFile(String fileName, List<Integer> data) {

        final XMLOutputFactory factory = XMLOutputFactory.newFactory();
        final String pathToXmlTarget = "0.xml";
        final String pathToXmlSource = "1.xml";


        try {
            XMLStreamWriter writer = factory.createXMLStreamWriter(
                    new FileOutputStream(pathToXmlTarget), "UTF-8");
            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeStartElement("entries");

            for (Integer aDbQueryResult : data) {
                writer.writeStartElement("entry");
                writer.writeStartElement("field");
                writer.writeCharacters(String.valueOf(aDbQueryResult));
                writer.writeEndElement();
                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.close();

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new StreamSource(
                            new BufferedInputStream(new FileInputStream(pathToXmlTarget))),
                    new StreamResult(new FileOutputStream(pathToXmlSource))
            );
        } catch (XMLStreamException | TransformerException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
