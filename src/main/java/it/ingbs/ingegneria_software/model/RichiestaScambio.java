package it.ingbs.ingegneria_software.model;

import it.ingbs.ingegneria_software.model.gerarchie.Categoria;

public class RichiestaScambio {

    private Categoria cat1; 
    private Categoria cat2; 
    private Fruitore fr; 
    private int ore; 
    private boolean stato;

    /**
     * 
     * @param cat1 indica la richieste effettuata dal fruitore
     * @param ore  numero di ore della categoria richiesta
     * @param cat2 indica l'offerta del fruitore
     * @param fr   soggetto che esegue la richiesta (Fruitore)
     */

    public RichiestaScambio(Categoria cat1,int ore,Categoria cat2, Fruitore fr){
        this.cat1 = cat1; 
        this.cat2  = cat2; 
        this.fr=fr;
        this.ore=ore; 
        this.stato=true;
    }


    public Categoria getCat1() {
        return cat1;
    }


    public Categoria getCat2() {
        return cat2;
    }


    public Fruitore getFr() {
        return fr;
    }


    public int getOre() {
        return ore;
    }

    public boolean getStato(){
        return stato; 
    }


    public void setStato(boolean stato) {
        this.stato = stato;
    }

    
    


    
    
}
