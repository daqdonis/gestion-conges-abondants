package com.groupe14ing2.gestioncongesabondants.controllers;

import java.io.IOException;

import javax.security.auth.login.FailedLoginException;

import com.groupe14ing2.gestioncongesabondants.models.Admin;


public class UserLoginController extends UserSocket {

    public UserLoginController() throws IOException {
        super();
    }

    public Admin login(String username, String password) throws FailedLoginException, IOException, ClassNotFoundException {

        // Send login command
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

        if (admin != null ) {
            System.out.println("Connexion réussie");
            return admin;
        }

        System.out.println("Login failed");
        throw new FailedLoginException();
    }

   
}