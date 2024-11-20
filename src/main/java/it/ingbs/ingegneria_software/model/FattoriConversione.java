package it.ingbs.ingegneria_software.model;

public class FattoriConversione {
    
    private CategoriaFoglia categoria1;
    private CategoriaFoglia categoria2;
    private double valore;



    public FattoriConversione(double valore, CategoriaFoglia foglia1, CategoriaFoglia foglia2) {
        this.valore = valore;
        this.categoria1 = foglia1;
        this.categoria2 = foglia2;
    }



    public CategoriaFoglia getCategoria1() {
        return categoria1;
    }



    public CategoriaFoglia getCategoria2() {
        return categoria2;
    }



    public double getValore() {
        return valore;
    }

    

    /*
     * OSS: visto che il fattore di conversione lo inserisce il Configuratore metto la mappa nella classe configuratore
     */


}