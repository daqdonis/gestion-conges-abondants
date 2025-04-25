package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Abondant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerLoginController extends DatabaseLink {
    public ServerLoginController() throws SQLException {
        super();
    }
    // this login function is temporary until we make a better one
    public boolean login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM Admin WHERE email = ? AND mot_passe = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
}
