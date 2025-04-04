package com.groupe14ing2.gestioncongesabondants.models;

public enum EtatTraitement {
    ENATTENTE("En attente"),
    REFUSE("Refusé"),
    ACCEPTE("Accepté");
    private String displayName;

    EtatTraitement(String displayName) {
        this.displayName = displayName;
    }


    @Override public String toString() { return displayName; }
}
