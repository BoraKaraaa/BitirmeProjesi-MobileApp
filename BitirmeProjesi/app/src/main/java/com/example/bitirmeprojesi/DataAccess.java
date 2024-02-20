package com.example.bitirmeprojesi;

import java.util.List;

public interface DataAccess
{
    public <T> T getById(DataTables dataTables, Class<T> type, int id, final DataBase.SingleDataCallback<T> callback);
    public <T> List<T> getTable(DataTables dataTables, Class<T> type, final DataBase.MultipleDataCallback<T> callback);
    public <T> void insert(DataTables dataTables, Class<T> type, T entity);
    public <T> void insertTable(DataTables dataTables, Class<T> type, List<T> entities);
    public <T> void update(DataTables dataTables, Class<T> type, int id, T entity);
    public <T> void delete(DataTables dataTables, Class<T> type, int id);
}
