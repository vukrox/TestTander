package ru.vukrox.periphery.misc.SupportiveClasses;

import java.io.File;
import java.net.URL;

/***
 * A class which is used to solve the issue when a file cannot be read from "resources" folder
 */
public class FromResourcesReader {

    public File getFileFromResources(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("File is not found!");
        } else {
            return new File(resource.getFile());
        }
    }
}
