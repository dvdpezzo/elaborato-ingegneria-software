package it.ingbs.ingegneria_software.model;

import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.model.gerarchie.Gerarchia;

public class FattoriConversione {
    
    private Gerarchia gerarchia1;
    private Gerarchia gerarchia2;
    private Categoria cat1;
    private Categoria cat2;
    private double valore;



    public FattoriConversione(double valore, Categoria foglia1, Categoria foglia2) {
        this.valore = valore;
        this.cat1 = foglia1;
        this.cat2 = foglia2;
    }

    
    public Categoria getCat1() {
        return cat1;
    }

    public Categoria getCat2() {
        return cat2;
    }

    public double getValore() {
        return valore;
    }




    


    

    
     


}
