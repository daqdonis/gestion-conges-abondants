package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.ServerURL;

import java.io.*;
import java.net.Socket;

public class UserSocket {
    protected Socket socket;
    protected BufferedReader bufferedReader;
    protected BufferedWriter bufferedWriter;
    protected ObjectOutputStream objectOutputStream;
    protected ObjectInputStream objectInputStream;

    protected UserSocket() throws IOException {

        this.socket = new Socket(ServerURL.URL, ServerURL.PORT);
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream)
        );

        bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(outputStream)
        );

        objectOutputStream = new ObjectOutputStream(outputStream);

        objectInputStream = new ObjectInputStream(inputStream);

    }
}
