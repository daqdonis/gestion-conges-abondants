package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Conge;
import com.groupe14ing2.gestioncongesabondants.models.EtatTraitement;

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
                                  case "update_etat":
                            // Receives ID and new EtatTraitement to update Conge
                            int idDemande = Integer.parseInt(bufferedReader.readLine()); 
                            String etatStr = bufferedReader.readLine(); 

                            // Attempt to update the state in the database
                            EtatTraitement etat = EtatTraitement.valueOf(etatStr); 
                            db.updateCongeEtat(idDemande, etat); 
                            bufferedWriter.write("État de la demande mis à jour avec succès.");
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
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
