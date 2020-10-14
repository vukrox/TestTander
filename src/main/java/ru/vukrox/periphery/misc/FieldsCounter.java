package ru.vukrox.periphery.misc;

import java.util.List;

/***
 * "FieldsCounter" counts arithmetic sum of all elements.
 */
public class FieldsCounter {

    //method to parse file, get the values and count the sum
    public void countArithmeticSumOfXml(List<Integer> list) {
        System.out.println("Sum of elements equals: " + list.stream().mapToLong(x -> x).sum());
    }
}