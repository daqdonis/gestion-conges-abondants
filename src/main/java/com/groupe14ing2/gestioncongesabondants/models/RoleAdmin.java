package com.groupe14ing2.gestioncongesabondants.models;

import java.io.Serializable;

public enum RoleAdmin implements Serializable {
    ADMINCONGEABANDONT("admin_conge_abandont"),
    ADMINCOMPTES("admin_comptes");
    private String displayName;

    RoleAdmin(String displayName) {
        this.displayName = displayName;
    }

    @Override public String toString() { return displayName; }
}
