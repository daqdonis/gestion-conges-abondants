package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Admin;

import javax.security.auth.login.FailedLoginException;
import java.io.*;
import java.sql.SQLException;

public class UserLoginController/* extends UserSocket*/ {

    public UserLoginController() throws IOException {
        super();
    }

    public Admin login(String username, String password) throws FailedLoginException, IOException, ClassNotFoundException {
        try {
            LoginController loginController = new LoginController();
            return loginController.login(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        /*// Send login command
        bufferedWriter.write("login");
        bufferedWriter.newLine();

        // Send username
        bufferedWriter.write(username);
        bufferedWriter.newLine();

        // Send password
        bufferedWriter.write(password);
        bufferedWriter.newLine();

        bufferedWriter.flush();

        // Read server response
        Admin admin = (Admin) objectInputStream.readObject();

        if (admin != null) {
            System.out.println("Login successful");
            System.out.println(admin.getNom() + " " + admin.getPrenom());
            return admin;
        }

        System.out.println("Login failed");
        throw new FailedLoginException();*/
    }
}