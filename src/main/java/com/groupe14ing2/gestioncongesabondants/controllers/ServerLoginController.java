package com.groupe14ing2.gestioncongesabondants.controllers;

import java.sql.SQLException;

public class ServerLoginController extends DatabaseLink {
    public ServerLoginController() throws SQLException {
        super();
    }

    public boolean login(String username, String password) {
        return false;
        // TODO: Implement login for the admin with hashing functionality for the password
        // should return true if the username and password are correct in the database
        // and false if not
    }
}
