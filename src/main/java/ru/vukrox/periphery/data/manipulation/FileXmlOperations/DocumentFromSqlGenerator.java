package ru.vukrox.periphery.data.manipulation.FileXmlOperations;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.sql.*;

/***
 * Class which takes SQL results and creates an XML document out of it.
 * it has single method which generates a document file.
 */

public class DocumentFromSqlGenerator {

    private static final String SQL_SELECT = "SELECT * FROM testtaskdb";

    //method to generate Document to further process it by an other method (to create an XML)
    public Document generateSqlDocument(Connection connection) {

        Statement statement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultMetadata = null;
        Document document = null;

        try {

            //Building XML Structure using DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.newDocument();
            Element rootElement = document.createElement("entries");
            document.appendChild(rootElement);

            //Initializing the statement to execute Select-statement
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT);
            //Get the Resultset metaInformation
            resultMetadata = resultSet.getMetaData();
            //Determine the number of columns
            int numCols = resultMetadata.getColumnCount();

            //the loop which performs the transformation of the resultset(meta) of the statement into the XML
            while (resultSet.next()) {
                Element row = document.createElement("entry");
                rootElement.appendChild(row);
                for (int i = 1; i <= numCols; i++) {
                    //For each column index, determine the column name
                    String colName = resultMetadata.getColumnName(i);
                    //Get the column value
                    Object colVal = resultSet.getObject(i);
                    Element node = document.createElement(colName);
                    node.appendChild(document.createTextNode(colVal.toString()));
                    row.appendChild(node);
                    //Null check and filling of null values into the possible empty spaces
                    if (resultSet.wasNull()) {
                        colVal = "and up";
                    }
                }
            }
            statement.close();
        } catch (SQLException sqlExceptionWhenSelectingAllUnits) {
            System.out.println("Couldn't select rows.");
            sqlExceptionWhenSelectingAllUnits.printStackTrace();
        } catch (ParserConfigurationException e) {
            System.out.println("Can't configure the parser.");
            e.printStackTrace();
        }
        return document;
    }
}


