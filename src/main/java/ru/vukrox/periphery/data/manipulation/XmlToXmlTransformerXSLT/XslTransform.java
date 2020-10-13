package ru.vukrox.periphery.data.manipulation.XmlToXmlTransformerXSLT;

import ru.vukrox.periphery.misc.SupportiveClasses.FromResourcesReader;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

/***
 * Class responsible for the transformation of 1.XML into 2.XML
 */
public class XslTransform {

    public void transformXMLviaXSLT () {
        //Creating an instance of Class to read XSL file from "resources" folder.
        FromResourcesReader resoursesReader = new FromResourcesReader();

        //performing transformation of 1.xml to 2.xml with attributes
        Transformer transformer = null;
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File(String.valueOf(resoursesReader.getFileFromResources("attributesMapper.xsl"))));
        try {
            transformer = factory.newTransformer(xslt);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        } catch (TransformerConfigurationException e) {
            System.out.println("Couldn't CONFIGURE transformation of the document.");
            e.printStackTrace();
        }
        Source xml = new StreamSource(new File("1.xml"));
        try {
            transformer.transform(xml, new StreamResult(new File("2.xml")));
        } catch (TransformerException e) {
            System.out.println("Couldn't transform the document.");
            e.printStackTrace();
        }
    }
}
