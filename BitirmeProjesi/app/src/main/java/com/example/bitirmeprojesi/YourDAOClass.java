package com.example.bitirmeprojesi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class YourDAOClass
{
    // Google Cloud SQL bağlantı bilgileri
    private static final String DB_URL = "jdbc:mysql://google/diyetisyen?" +
            "cloudSqlInstance=bitirme&" +
            "socketFactory=com.google.cloud.sql.mysql.SocketFactory&" +
            "user=root&" +
            "password=123456bbbB&" +
            "useSSL=false";

    private static final String TABLE_NAME = "User";
    private static final String COLUMN_ID = "UserID";
    private static final String COLUMN_NAME = "Username";

    private Connection conn;

    public YourDAOClass() throws SQLException
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        conn = DriverManager.getConnection(DB_URL);
    }

    public void addRecord(String name) throws SQLException
    {
        String sql = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME + ") VALUES (?)";

        try (PreparedStatement statement = conn.prepareStatement(sql))
        {
            statement.setString(1, name);
            statement.executeUpdate();
        }
    }

    public String getRecord(int id) throws SQLException
    {
        String sql = "SELECT " + COLUMN_NAME + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql))
        {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next())
                {
                    return resultSet.getString(COLUMN_NAME);
                }
            }
        }

        return null;
    }

    public void close() throws SQLException
    {
        if (conn != null) {
            conn.close();
        }
    }
}
