package com.example.bitirmeprojesi;

import java.sql.Connection;
import java.util.List;

public abstract class DataBase implements DataAccess
{
    protected Connection connection;

    @FunctionalInterface
    public interface SingleDataCallback<T>
    {
        void onResponse(T data);
        default void onError(String message) {}
    }

    @FunctionalInterface
    public interface MultipleDataCallback<T>
    {
        void onResponse(List<T> dataList);
        default void onError(String message) {}
    }
}
