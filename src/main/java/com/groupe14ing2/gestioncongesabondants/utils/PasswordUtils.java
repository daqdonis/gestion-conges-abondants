package com.groupe14ing2.gestioncongesabondants.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Verifies a plain password against a hashed password
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
    
}
