package fr.milekat.virus;

import fr.milekat.virus.obj.Monde;
import fr.milekat.virus.utils.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    /**
     *      Si argument de lancement, lancement du programme en mode débug (Avec les commentaires de run)
     */
    public static boolean debug = false;

    public static void main(String[] args) {
        if (args.length >=1) debug = true;
        Scanner scanner = new Scanner(System.in);
        log("Définissez la taille du monde :");
        String input = scanner.nextLine();
        if (tools.isInt(input)) {
            int size = Integer.parseInt(input);
            if (size>=1) {
                newWorld(size);
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
     *      Create new World
     */
    private static void newWorld(int size) {
        int x;
        int y;
        String input;
        Scanner scanner = new Scanner(System.in);
        log("Ou est le patient 0 ? (Coordonnée X)");
        input = scanner.nextLine();
        if (tools.isInt(input) && Integer.parseInt(input) <= size) {
            x = Integer.parseInt(input);
        } else {
            log("Merci de choisir un nombre entier suppérieur ou égual à 0 et inférieur à " + size + ".");
            return;
        }
        log("Ou est le patient 0 ? (Coordonnée Y)");
        input = scanner.nextLine();
        if (tools.isInt(input) && Integer.parseInt(input) <= size) {
            y = Integer.parseInt(input);
        } else {
            log("Merci de choisir un nombre entier suppérieur ou égual à 0 et inférieur à " + size + ".");
            return;
        }
        Monde monde = new Monde(size, new int[] {x,y});
        runWorld(monde);
    }

    /**
     *      Lancement de la simulation dans notre monde !
     */
    private static void runWorld(Monde monde) {
        int step = 0;
        ArrayList<Integer> infectes = new ArrayList<>();
        do {
            monde.displayWorld();
            log("Étape " + step + " il y a " + monde.getInfectes() + " infecté(s).");
            infectes.add(monde.getInfectes());
            monde.nextStep();
            step++;
        } while (monde.getPatientsCount() > monde.getInfectes());
        monde.displayWorld();
        log("Étape " + step + " il y a " + monde.getInfectes() + " infecté(s).");
        infectes.add(monde.getInfectes());
        log("Résultat : " + infectes.toString());
    }
}
