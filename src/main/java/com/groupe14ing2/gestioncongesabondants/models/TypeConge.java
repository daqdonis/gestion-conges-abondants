package com.groupe14ing2.gestioncongesabondants.models;

public enum TypeConge {

    MALADIECHRONIQUE("Maladie chronique invalidante"),
    MATERNITÉ("Maternité"),
    MALADIELONGUEDURÉE("Maladie longue durée"),
    SERVICENATIONAL("Service national"),
    OBLIGATIONSFAMILIALES("Obligations familiales"),
    ACCIDENTSGRAVES("Accidents graves");

    private final String titre;

    TypeConge(String titre) {
        this.titre = titre;
    }

    @Override
    public String toString() {
        return titre;
    }
}
