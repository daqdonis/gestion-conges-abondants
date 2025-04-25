package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.ServerURL;

import javax.security.auth.login.FailedLoginException;
import java.io.*;
import java.net.Socket;

public class UserLoginController extends UserSocket {

    public UserLoginController() throws IOException {
        super();
    }

    public Socket login(String username, String password) throws FailedLoginException, IOException {

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

        if (Boolean.parseBoolean(bufferedReader.readLine())) {
            System.out.println("Login successful");
            return socket;
        }

        System.out.println("Login failed");
        throw new FailedLoginException();
    }
}