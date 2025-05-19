package com.groupe14ing2.gestioncongesabondants.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.groupe14ing2.gestioncongesabondants.models.Admin;
import com.groupe14ing2.gestioncongesabondants.models.RoleAdmin;
import com.groupe14ing2.gestioncongesabondants.utils.PasswordUtils;


public class LoginController extends DatabaseLink {
    public LoginController() throws SQLException {

        super();
    }
    // this login function is temporary until we make a better one
    public Admin login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM Admin WHERE email = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, username);
       

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
        String hashedPassword = resultSet.getString("mot_passe");

         
         if (PasswordUtils.verifyPassword(password, hashedPassword)) {
            return new Admin(
                    resultSet.getString("id_admin"),
                    resultSet.getString("nom"),
                    resultSet.getString("prenom"),
                    RoleAdmin.valueOf(resultSet.getString("roles").trim().toUpperCase().replace("_", "")),
                    resultSet.getString("email"),
                    null
            );
         }
        
    }return null;}

}
