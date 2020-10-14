package ru.vukrox.periphery.dao;

import java.sql.Connection;
import java.util.List;
/**
 * Interface to realise reimplementation of other DB connections.
 * "Программу нужно реализовать с учетом возможности переиспользования/встраивания в других задачах и проектах."
 * */
public interface EntityDao {

    public List<Integer> getDataFromDb(Connection connection);
    public void addEntityList(List<Integer> list, Connection connection);
    public void deleteAllEntitiesFromDB(Connection connection);
}
