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

    public static RoleAdmin fromDatabaseValue(String dbValue) {
        if (dbValue == null) return null;
        String normalized = dbValue.trim().toUpperCase().replaceAll("[-_]", "");
        for (RoleAdmin role : values()) {
            if (role.name().equals(normalized) || 
                role.toString().toUpperCase().replaceAll("[-_]", "").equals(normalized)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role value: " + dbValue);
    }
}
