package com.groupe14ing2.gestioncongesabondants;

import com.groupe14ing2.gestioncongesabondants.controllers.ServerLoginController;
import com.groupe14ing2.gestioncongesabondants.models.ServerURL;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class ServerApplication implements Runnable {
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(2514);

            while (true) {
                try {
                    socket = serverSocket.accept();
                    System.out.println("Accepted connection from " + socket.getInetAddress().getHostAddress());
                    bufferedReader = new BufferedReader(
                            new InputStreamReader(socket.getInputStream())
                    );

                    bufferedWriter = new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())
                    );

                    objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectInputStream = new ObjectInputStream(socket.getInputStream());

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                while (true) {
                    try {
                        System.out.println("Waiting for command");
                        String clientMessage = bufferedReader.readLine();
                        System.out.println(clientMessage);

                        switch (clientMessage) {
                            case "login":
                                ServerLoginController loginController = new ServerLoginController();

                                bufferedWriter.write(String.valueOf(loginController.login(
                                        bufferedReader.readLine(), // admin's username
                                        bufferedReader.readLine() // admin's password
                                )));
                                bufferedWriter.newLine();
                                bufferedWriter.flush();
                                break;
                            case "another case": // this is here for the future if we need it
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
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        ServerApplication serverApplication = new ServerApplication();
        serverApplication.run();
    }
}
