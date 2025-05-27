package com.groupe14ing2.gestioncongesabondants.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class EmailUtils {
    private static String FROM_EMAIL;
    private static String AUTH_EMAIL;
    private static String AUTH_PASSWORD;

    public static void setValues() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("config.cfg"));
        br.readLine();
        br.readLine();
        br.readLine();
        FROM_EMAIL = AUTH_EMAIL = br.readLine();
        AUTH_PASSWORD = br.readLine();
        br.close();
    }

    private static Properties getEmailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        return properties;
    }
    
    private static Session getEmailSession() {
        return Session.getInstance(getEmailProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(AUTH_EMAIL, AUTH_PASSWORD);
            }
        });
    }
    
    public static void sendEmail(String to, String subject, String msg) {
        if (to == null || to.trim().isEmpty()) {
            System.out.println("❌ Email non envoyé: adresse email manquante");
            return;
        }

        try {
            Session session = getEmailSession();
            MimeMessage message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject, "UTF-8");
            message.setText(msg, "UTF-8");
            
            Transport.send(message);
            System.out.println("✅ Email envoyé avec succès à: " + to);
            
        } catch (MessagingException mex) {
            System.out.println("❌ Échec de l'envoi de l'email à: " + to);
            mex.printStackTrace();
            showAlert("Erreur d'envoi", "L'email n'a pas pu être envoyé: " + mex.getMessage());
        }
    }
    
    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 