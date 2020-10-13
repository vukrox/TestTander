package ru.vukrox.core.domain;

import ru.vukrox.periphery.data.manipulation.FileXmlOperations.FillerOfElements;

import java.util.ArrayList;
import java.util.List;

public class EntityImpl implements Entity {

    //Simple method to generate an array list of elements from 1 to "N" to further input them into SQL DB
    @Override
    public List<Integer> getInitialData() {

        int numOfElements = FillerOfElements.enterNumberOfElements();

        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < numOfElements + 1; i++) {
            list.add(i);
        }
        return list;
    }
}
