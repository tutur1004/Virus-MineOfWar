package fr.milekat.virus.obj;

public class Patients {
    private boolean infecte;

    public Patients(boolean infecte) {
        this.infecte = infecte;
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
}
