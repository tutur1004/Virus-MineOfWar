package fr.milekat.virus.obj;

public class Patients {
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
