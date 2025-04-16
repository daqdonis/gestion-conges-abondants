package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Conge;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServerCongeController{
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public ServerCongeController(Socket socket) {
        try {
            this.socket = socket;
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());



            while (true) {
                try {
                    DatabaseController db = new DatabaseController();
                    String clientMessage = bufferedReader.readLine();

                    switch (clientMessage) {
                        case "add_conge":
                            try {
                                // adds a conge to the db that is read from the socket
                                db.addConge((Conge) objectInputStream.readObject());
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "get_conges":
                            // sends an arraylist of conges to the user using the socket
                            objectOutputStream.writeObject(db.getAllConges());
                            objectOutputStream.flush();
                            break;
                        default:
                            bufferedWriter.write(clientMessage + " is an invalid message!");
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                    }

                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
