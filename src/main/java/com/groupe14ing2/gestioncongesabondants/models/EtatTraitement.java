package com.groupe14ing2.gestioncongesabondants.models;

import java.io.Serializable;

public enum EtatTraitement implements Serializable {
    ENATTENTE("En attente"),
    REFUSE("Refusé"),
    ACCEPTE("Accepté");
    private String displayName;

    EtatTraitement(String displayName) {
        this.displayName = displayName;
    }


    @Override public String toString() { return displayName; }
}
