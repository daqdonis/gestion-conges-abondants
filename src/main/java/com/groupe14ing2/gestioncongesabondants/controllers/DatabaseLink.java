package com.groupe14ing2.gestioncongesabondants.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseLink {
    protected static final String URL = "jdbc:mysql://localhost:3306/ conges_abondant "; // Change to your MySQL URL
    protected static final String USER = "nada"; // Change to your MySQL username
    protected static final String PASSWORD = "Nada2005;"; // Change to your MySQL password
    protected final Connection connection;

    public DatabaseLink() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
