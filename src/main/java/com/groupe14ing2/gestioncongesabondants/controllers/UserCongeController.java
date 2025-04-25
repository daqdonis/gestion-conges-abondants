package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Conge;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class UserCongeController extends UserSocket {

    public UserCongeController(Socket socket) throws IOException {
        super();
    }

    public ArrayList<Conge> getConges() throws IOException, ClassNotFoundException {
        bufferedWriter.write("get_conges");
        bufferedWriter.newLine();
        bufferedWriter.flush();

        return (ArrayList<Conge>) objectInputStream.readObject();
    }

    public void writeConge(Conge conge) throws IOException {
        bufferedWriter.write("add_conge");
        bufferedWriter.newLine();

        objectOutputStream.writeObject(conge);

        bufferedWriter.flush();
        objectOutputStream.flush();
    }
}
