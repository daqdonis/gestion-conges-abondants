package com.groupe14ing2.gestioncongesabondants.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GetAbandonts {
    public static ArrayList<Long> getIdsFromFile(File file) {
        ArrayList<Long> etudiantIds = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.lines().forEach(line -> {
                String[] s = line.split(",");

                int i;
                for (i = 1; i < s.length; i++) {
                    if (Float.parseFloat(s[i]) > 0)
                        break;
                }

                if (i == s.length) {
                    etudiantIds.add(Long.parseLong(s[0]));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return etudiantIds;
    }
}
