package com.groupe14ing2.gestioncongesabondants.controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseLink {
    protected static String URL; // Change to your MySQL URL
    protected static String USER; // Change to your MySQL username
    protected static String PASSWORD; // Change to your MySQL password
    protected final Connection connection;

    public DatabaseLink() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void setValues() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("config.cfg"));
        URL = br.readLine();
        USER = br.readLine();
        PASSWORD = br.readLine();
        br.close();
    }
}