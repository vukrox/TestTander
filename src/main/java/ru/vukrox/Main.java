package ru.vukrox;

import ru.vukrox.core.domain.Entity;
import ru.vukrox.core.domain.EntityImpl;
import ru.vukrox.periphery.configuration.AppConfigFromFileImpl;
import ru.vukrox.periphery.configuration.AppConfiguration;
import ru.vukrox.periphery.dao.DbInserter;
import ru.vukrox.periphery.dao.PrepareStorageEnvironment;
import ru.vukrox.periphery.dao.postgres.PostgreDBConnecton;
import ru.vukrox.periphery.dao.postgres.PrepareStorageEnvironmentPostgresImpl;
import ru.vukrox.periphery.data.manipulation.FileXmlOperations.DocumentFromSqlGenerator;
import ru.vukrox.periphery.data.manipulation.FileXmlOperations.XmlFileSaver;
import ru.vukrox.periphery.proxy.ProgramTimeMeasurement.MeasureTime;
import ru.vukrox.periphery.misc.XmlFieldCounter.FieldParserAndCounter;
import ru.vukrox.periphery.data.manipulation.XmlToXmlTransformerXSLT.XslTransform;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Stage 1: Input integers into SQL DB in the field-column
        long start = MeasureTime.measureStartTime();
        PostgreDBConnecton dbConnecton = new PostgreDBConnecton();

        try {
            AppConfigFromFileImpl.load("parameters.properties");
            Connection connection = dbConnecton.establishConnection(AppConfigFromFileImpl.getInstance());
            PrepareStorageEnvironment prepareStorageEnvironment = new PrepareStorageEnvironmentPostgresImpl(connection);
            prepareStorageEnvironment.prepareStorageEnvironment();

            DbInserter inserter = new DbInserter();

            Entity setOfInitData = new EntityImpl();
            inserter.removeAllUnitsFromDB(connection);
            List<Integer> listOfElements = setOfInitData.getInitialData();

            inserter.insertList2DB(listOfElements, connection);

            //Stage2: Extract data from the DataBase to insert it into an XML-File. XML-Structure is as below:
            //<entries>
            //     <entry>
            //          <field>

            DocumentFromSqlGenerator generator = new DocumentFromSqlGenerator();
            XmlFileSaver xmlFileSaver = new XmlFileSaver();
            xmlFileSaver.getDocumentAsXml(generator.generateSqlDocument(connection));
            PostgreDBConnecton.closeConnetion(connection);
        } catch (IOException exception) {
            System.out.println("Failed during properties read");
        }

        //Stage 3: Transform XML with attributes using XSLT
        XslTransform xslTransform = new XslTransform();
        xslTransform.transformXMLviaXSLT();

        //Stage 4 count and parse
        FieldParserAndCounter counter = new FieldParserAndCounter();
        counter.countArithmeticSumOfXml();
        long stop = MeasureTime.measureStopTime();
        MeasureTime.displayWorkTimeOfTheProgramme(stop, start);
    }
}
