package ru.vukrox.periphery.proxy;

/***
 * Class designed to measure the time to execute the programme.
 * According to the measurements it takes 1644 milliseconds to add 1.000.000 elements to add to DB and to further convert it to XML.
 */

public class MeasureTime {

    static long startTime = 0;
    static long stopTime = 0;

    //method that is used before another method to capture the 1st start-time.
    public static long measureStartTime() {
        startTime = System.currentTimeMillis();
        return startTime;
    }

    //method to capture the moment when the work of a method which is being measured was finished
    public static long measureStopTime() {
        stopTime = System.currentTimeMillis();
        return stopTime;
    }

    //method to calculate and display the time of work of the programme
    public static void displayWorkTimeOfTheProgramme(long stopTime, long startTime) {
        System.out.println("The programme worked "+ ((stopTime-startTime)/1000) +" seconds.");
    }
}