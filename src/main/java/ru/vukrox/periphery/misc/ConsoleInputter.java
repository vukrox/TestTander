package ru.vukrox.periphery.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/***
 * "ConsoleInputter" is a class for taking input fro the user from the console.
 * method: "enterNumberOfElements".
 */
public class ConsoleInputter {

    //method which gets from console the "N" number of elements and stores it into an int-Value for further use
    public static int enterNumberOfElements() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int numOfRaws = 0;
        try {
            System.out.println("Please, enter the number of fields to insert into a database: ");
            numOfRaws = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            System.out.println("Input failed.");
            e.printStackTrace();
        }
        return numOfRaws;
    }
}