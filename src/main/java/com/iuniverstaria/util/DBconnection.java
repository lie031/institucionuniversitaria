package com.iuniverstaria.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {

    private static final String URL = "jdbc:mysql://localhost:3306/InstitucionUniversitaria";
    private static final String USER = "root";
    private static final String PASSWORD = "0303"; 

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL.", e);
        }
    }

    public static void main(String[] args) {
        try {
            Connection connection = DBconnection.getConnection();
            if (connection != null) {
                System.out.println("Conexión exitosa a la base de datos.");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }
}

