package fr.milekat.virus;

import fr.milekat.virus.obj.Monde;
import fr.milekat.virus.utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        if (tools.isInt(input)) {
            int size = Integer.parseInt(input);
            if (size>=1) {
                log("Combien de patient(s)0 ?");
                int patients;
                input = scanner.nextLine();
                if (tools.isInt(input) && Integer.parseInt(input) <= size) {
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
                    if (tools.isInt(input) && Integer.parseInt(input) <= size) {
                        x = Integer.parseInt(input);
                    } else {
                        log("Merci de choisir un nombre entier suppérieur ou égual à 0 et inférieur à " + size + ".");
                        return;
                    }
                    log("Ou est le patient0 n°" + i + " ? (Coordonnée Y)");
                    input = scanner.nextLine();
                    if (tools.isInt(input) && Integer.parseInt(input) <= size) {
                        y = Integer.parseInt(input);
                    } else {
                        log("Merci de choisir un nombre entier suppérieur ou égual à 0 et inférieur à " + size + ".");
                        return;
                    }
                    patients0.add(new int[] {x,y});
                }
                virus(patients0,size);
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
        System.out.println("[" + Date.setDateNow() + "] " + msg);
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
}
