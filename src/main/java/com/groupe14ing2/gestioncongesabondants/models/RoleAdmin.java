package com.groupe14ing2.gestioncongesabondants.models;

import java.io.Serializable;

public enum RoleAdmin implements Serializable {
    ADMINCONGE("admin_conge"),
    ADMINABONDANT("admin_abondant"),
    ADMINCOMPTES("admin_comptes");
    private String displayName;

    RoleAdmin(String displayName) {
        this.displayName = displayName;
    }

    @Override public String toString() { return displayName; }
}
