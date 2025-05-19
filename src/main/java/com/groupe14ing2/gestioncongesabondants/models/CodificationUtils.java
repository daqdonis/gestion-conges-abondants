package com.groupe14ing2.gestioncongesabondants.models;

public class CodificationUtils {

    public static String generateDemandeID(char typeDemande, String annee, String matricule) {
        return String.format("%c-%s-%s", typeDemande, annee, matricule);
    }

    public static String generateAdminID(char typeCompte, char typeDemande, String numeroCompte) {
        return String.format("%c-%c-%s", typeCompte, typeDemande, numeroCompte);
    }

    public static String generateCycleID(String nomCycle) {
        return nomCycle.substring(0, 3).toUpperCase();
    }

    public static String generateFiliereID(String nomFiliere, int numero) {
        return String.format("%s-%d", nomFiliere.substring(0, 3).toUpperCase(), numero);
    }


    public static String generateGroupID(int numeroGroupe) {
        return "G-" + numeroGroupe;
    }

    public static String generateSectionID(int numeroSection) {
        return "S-" + numeroSection;
    }

    public static String generateSemestreID(String cycleID, int numeroSemestre) {
        return String.format("%s-%d", cycleID.toUpperCase(), numeroSemestre);
    }

    public static String generateModuleID(String filiereID, String niveau, String semestreID, String nomModule) {
        return String.format("%s-%s-%s-%s", filiereID, niveau, semestreID, nomModule.substring(0, 3).toUpperCase());
    }
}
