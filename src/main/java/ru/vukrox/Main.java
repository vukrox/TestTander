package ru.vukrox;

import ru.vukrox.core.domain.Entity;
import ru.vukrox.core.domain.EntityImpl;
import ru.vukrox.periphery.configuration.AppConfigFromFileImpl;
import ru.vukrox.periphery.dao.EntityDao;
import ru.vukrox.periphery.dao.PrepareStorageEnvironment;
import ru.vukrox.periphery.dao.postgres.EntityDaoPostgresImpl;
import ru.vukrox.periphery.dao.postgres.PostgresDbConnection;
import ru.vukrox.periphery.dao.postgres.PrepareStorageEnvironmentPostgresImpl;
import ru.vukrox.periphery.data.manipulation.XmlFileGenerator;
import ru.vukrox.periphery.data.manipulation.OneXmlTwoTransformer;
import ru.vukrox.periphery.misc.FieldsCounter;
import ru.vukrox.periphery.proxy.MeasureTime;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Stage 1:
        // Input integers into SQL DB in the field-column

        long start = MeasureTime.measureStartTime();
        try {
            //load properties
            AppConfigFromFileImpl.load("parameters.properties");
            //open connection
            PostgresDbConnection dbConnecton = new PostgresDbConnection();
            //set up the connection
            Connection connection = dbConnecton.establishConnection(AppConfigFromFileImpl.getInstance());
            //create table in the DB or check if exists
            PrepareStorageEnvironment prepareStorageEnvironment = new PrepareStorageEnvironmentPostgresImpl(connection);
            prepareStorageEnvironment.prepareStorageEnvironment();

            //prepare methods to work with the DB
            EntityDao entityDao = new EntityDaoPostgresImpl();
            //initialize domain class and issue the List<> for further processing
            Entity setOfInitData = new EntityImpl();
            //clean DB of any data before input
            entityDao.deleteAllEntitiesFromDB(connection);
            //fill the List<>
            List<Integer> listOfElements = setOfInitData.getInitialData();
            //fill the DB from the list
            entityDao.addEntityList(listOfElements, connection);

            /**
             * Stage 1 - over!!!
             ______________________________________________________________________________________*/

            //Stage2:
            // Extract data from the DataBase to insert it into XML-File "1.xml".

            //extract data into a List<> from DB.
            List<Integer> dataFromDb = entityDao.getDataFromDb(connection);
            //close connection
            PostgresDbConnection.closeConnection(connection);
            //save data from the List<> into the "1.xml"
            XmlFileGenerator xmlFileGenerator = new XmlFileGenerator();
            xmlFileGenerator.saveDataToXmlFile("1.xml", dataFromDb);

            /**
             * Stage 2 - over!!!
             ______________________________________________________________________________________*/

            //Stage 3:
            // Transform XML with attributes using XSLT
            OneXmlTwoTransformer oneXmlTwoTransformer = new OneXmlTwoTransformer();
            oneXmlTwoTransformer.transformXMLviaXSLT();

            /**
             * Stage 3 - over!!!
             ______________________________________________________________________________________*/

            //Stage 4:
            // count and parse
            FieldsCounter counter = new FieldsCounter();
            counter.countArithmeticSumOfXml(listOfElements);

            /**
             * Stage 4 - over!!!
             ______________________________________________________________________________________*/
            long stop = MeasureTime.measureStopTime();
            MeasureTime.displayWorkTimeOfTheProgramme(stop, start);
        } catch (IOException exception) {
            System.out.println("Failed during properties read");
        }
    }
}
