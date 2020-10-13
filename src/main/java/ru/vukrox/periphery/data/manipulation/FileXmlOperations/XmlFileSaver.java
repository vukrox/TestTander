package ru.vukrox.periphery.data.manipulation.FileXmlOperations;

import org.w3c.dom.Document;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/***
 * SOLID Class to handle the converting of Document into XML.
 */
public class XmlFileSaver {

    //input a document and get an XML in the end --> store the XML into the file System
    public void getDocumentAsXml(Document doc) {

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            //transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            // we want to pretty format the XML output
            transformer.setOutputProperty
                    ("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            //
            Result output = new StreamResult(new File("1.xml"));
            Source input = new DOMSource(doc);
            transformer.transform(input, output);

        } catch (Exception e) {
            System.out.println("Couldn't transform it to xml.");
            e.printStackTrace();
        }
    }
}
