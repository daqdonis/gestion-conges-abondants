package com.groupe14ing2.gestioncongesabondants.models;

import java.io.Serializable;

public enum EtatTraitement implements Serializable {
    ENATTENTE("En attente"),
    REFUSÉ("Refusé"),
    ACCEPTÉ("Accepté");
    //private String displayName;

    private final String displayName;

    EtatTraitement(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    
    public static EtatTraitement fromDisplayName(String displayName) {
        for (EtatTraitement etat : EtatTraitement.values()) {
            if (etat.displayName.equalsIgnoreCase(displayName)) {
                return etat;
            }
        }
        throw new IllegalArgumentException("Aucune valeur enum pour: " + displayName);
    }
}


    