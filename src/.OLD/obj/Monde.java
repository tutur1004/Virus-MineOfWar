package fr.milekat.virus.obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static fr.milekat.virus.Main.debug;
import static fr.milekat.virus.Main.log;

public class Monde {
    private final HashMap<String, Patients> placement = new HashMap<>();
    private int infectes;
    private final int worldSize;

    /**
     *      Init du nouveau monde
     */
    public Monde(int size, List<int[]> patients0) {
        this.worldSize = size;
        generateWorld();
        for (int[] pos : patients0) {
            setPatientToInfecte(pos[0] , pos[1]);
        }
    }

    /**
     *      Génération des patients dans le monde !
     */
    private void generateWorld() {
        for (int x = 0;x < this.worldSize;x++) {
            for (int y = 0; y < this.worldSize; y++) {
                this.placement.put(x + ";" + y, new Patients(false, new int[] {x,y}));
                if (debug) log("Patient ajouté en " + x + y + " total de " + getPatientsCount() + " patient(s).");
            }
        }
    }

    /**
     *      Étape suivante dans le monde
     */
    public void nextStep() {
        ArrayList<String> newInfectes = new ArrayList<>();
        for (Patients patients : this.placement.values()) {
            int x = patients.getPosition()[0];
            int y = patients.getPosition()[1];
            if (debug) log("Check du patient en " + x + ";" + y +".");
            if (!patients.isInfecte()) {
                if (x - 1 >= 0 && this.placement.get((x - 1) + ";" + y).isInfecte()) {
                    newInfectes.add(x + ";" + y);
                } else if (x + 1 <= this.worldSize-1 && this.placement.get((x + 1) + ";" + y).isInfecte()) {
                    newInfectes.add(x + ";" + y);
                } else if (y - 1 >= 0 && this.placement.get(x + ";" + (y - 1)).isInfecte()) {
                    newInfectes.add(x + ";" + y);
                } else if (y + 1 <= this.worldSize-1 && this.placement.get(x + ";" + (y + 1)).isInfecte()) {
                    newInfectes.add(x + ";" + y);
                }
            } else {
                if (debug) log("Le patient est infecté.");
            }
        }
        for (String pos : newInfectes) {
            setPatientToInfecte(pos);
        }
    }

    /**
     *      Si le patient n'était pas déjà infecté, il le devient (Mode coordonnées)
     */
    private void setPatientToInfecte(int x, int y) {
        Patients patients = this.placement.get(x + ";" + y);
        if (!patients.isInfecte()) {
            this.placement.get(x + ";" + y).setInfecte(true);
            if (debug) log("Le patient en " + x + ";" + y + " devient infecté.");
            this.infectes++;
        }
    }

    /**
     *      Si le patient n'était pas déjà infecté, il le devient (Mode String)
     */
    private void setPatientToInfecte(String pos) {
        Patients patients = this.placement.get(pos);
        if (!patients.isInfecte()) {
            this.placement.get(pos).setInfecte(true);
            if (debug) log("Le patient en " + pos + " devient infecté.");
            this.infectes++;
        }
    }

    /**
     *      Connaitre le nombre de personnes dans ce monde
     */
    public int getPatientsCount() {
        return this.placement.size();
    }

    /**
     *      RConnaitre le nombre de personnes infectée(s) dans ce monde
     */
    public int getInfectes() {
        return infectes;
    }

    /**
     *      Méthode pour afficher la carte du monde (Mode Débug)
     */
    public void displayWorld() {
        for (int x = 0 ;x < this.worldSize; x++) {
            for (int y = 0; y < this.worldSize; y++) {
                if (this.placement.get(x + ";" + y).isInfecte()) {
                    System.out.print("X");
                } else {
                    System.out.print("O");
                }
            }
            System.out.println();
        }
    }
}
