package fr.milekat.virus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static fr.milekat.virus.Main.debug;
import static fr.milekat.virus.Main.log;

public class Main {
    /**
     *      Si argument de lancement, lancement du programme en mode débug (Avec les commentaires de run)
     */
    public static boolean debug = false;

    /**
     *      Main pour définir la taille du monde et les patient(s)0
     */
    public static void main(String[] args) {
        if (args.length >=1) debug = true;
        Scanner scanner = new Scanner(System.in);
        log("Définissez la taille du monde :");
        String input = scanner.nextLine();
        if (isInt(input)) {
            int size = Integer.parseInt(input);
            if (size>=1) {
                log("Combien de patient(s)0 ?");
                int patients;
                input = scanner.nextLine();
                if (isInt(input) && Integer.parseInt(input) <= size) {
                    patients = Integer.parseInt(input);
                } else {
                    log("Merci de choisir un nombre entier suppérieur ou égual à 0.");
                    return;
                }
                List<int[]> patients0 = new ArrayList<>();
                for (int i=1;i<=patients;i++) {
                    int x;
                    int y;
                    log("Ou est le patient0 n°" + i + " ? (Coordonnée X)");
                    input = scanner.nextLine();
                    if (isInt(input) && Integer.parseInt(input) <= size) {
                        x = Integer.parseInt(input);
                    } else {
                        log("Merci de choisir un nombre entier suppérieur ou égual à 0 et inférieur à " + size + ".");
                        return;
                    }
                    log("Ou est le patient0 n°" + i + " ? (Coordonnée Y)");
                    input = scanner.nextLine();
                    if (isInt(input) && Integer.parseInt(input) <= size) {
                        y = Integer.parseInt(input);
                    } else {
                        log("Merci de choisir un nombre entier suppérieur ou égual à 0 et inférieur à " + size + ".");
                        return;
                    }
                    patients0.add(new int[] {x,y});
                }
                log("Et voilà : " + virus(patients0,size));
            } else {
                log("Merci de choisir un nombre entier suppérieur ou égual à 1.");
            }
        } else {
            log("Merci de choisir un nombre entier suppérieur ou égual à 1.");
        }
    }

    /**
     *      Logger avec date !
     */
    public static void log(String msg) {
        System.out.println("[" + setDateNow() + "] " + msg);
    }


    /**
     *      Lancement de la simulation !
     */
    public static List<Integer> virus(List<int[]> patients0, int n) {
        Monde monde = new Monde(n, patients0);
        int step = 0;
        ArrayList<Integer> infectes = new ArrayList<>();
        do {
            if (debug) monde.displayWorld();
            log("Étape " + step + " il y a " + monde.getInfectes() + " infecté(s).");
            infectes.add(monde.getInfectes());
            monde.nextStep();
            step++;
        } while (monde.getPatientsCount() > monde.getInfectes());
        if (debug) monde.displayWorld();
        log("Étape " + step + " il y a " + monde.getInfectes() + " infecté(s).");
        infectes.add(monde.getInfectes());
        log("Résultat : " + infectes.toString());
        return infectes;
    }

    /**
     *      Tools
     */
    private static final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static String setDateNow() {
        return df.format(new java.util.Date());
    }

    public static boolean isInt(String string) {
        try
        {
            Integer.parseInt(string);
            return true;
        }
        catch (NumberFormatException ignore)
        {
            return false;
        }
    }
}

class Monde {
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

class Patients {
    private boolean infecte;
    private final int[] position;

    public Patients(boolean infecte, int[] position) {
        this.infecte = infecte;
        this.position = position;
    }

    /**
     *      Infecté = true
     */
    public boolean isInfecte() {
        return this.infecte;
    }

    /**
     *      Définit le patient comme étant infecté
     */
    public void setInfecte(boolean infecte) {
        this.infecte = infecte;
    }

    public int[] getPosition() {
        return position;
    }
}
