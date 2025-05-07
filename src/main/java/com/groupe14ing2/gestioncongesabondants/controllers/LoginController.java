package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Admin;
import com.groupe14ing2.gestioncongesabondants.models.RoleAdmin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController extends DatabaseLink {
    public LoginController() throws SQLException {
        super();
    }
    // this login function is temporary until we make a better one
    public Admin login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM Admin WHERE email = ? AND mot_passe = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
            return new Admin(
                    resultSet.getInt("id_admin"),
                    resultSet.getString("nom"),
                    resultSet.getString("prenom"),
                    RoleAdmin.valueOf(resultSet.getString("roles").trim().toUpperCase().replace("_", "")),
                    resultSet.getString("email"),
                    null
            );
        return null;
    }
}
