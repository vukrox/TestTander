package ru.vukrox.core.domain;

import ru.vukrox.periphery.misc.ConsoleInputter;

import java.util.ArrayList;
import java.util.List;
/**
 * "EntityImpl" - domain class which represents the core of the programme.
 * Can be used later to modify the toolset of this program if necessary.
 *
 * Here we start the whole program with the method "getInitialData".
 * */
public class EntityImpl implements Entity {

    //Simple method to generate an array list of elements from 1 to "N" to further input them into SQL DB
    @Override
    public List<Integer> getInitialData() {

        int numOfElements = ConsoleInputter.enterNumberOfElements();

        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < numOfElements + 1; i++) {
            list.add(i);
        }
        return list;
    }
}
