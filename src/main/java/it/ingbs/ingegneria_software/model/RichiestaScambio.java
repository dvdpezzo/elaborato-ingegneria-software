package it.ingbs.ingegneria_software.model;

import it.ingbs.ingegneria_software.model.gerarchie.Categoria;


public class RichiestaScambio {

    private Categoria catRichiesta; 
    private Categoria catOfferta; 
    private Fruitore fr; 
    private int oreRichieste; 
    private int oreOffertte;
    private boolean stato;

    /**
     * 
     * @param cat1 indica la richieste effettuata dal fruitore
     * @param ore  numero di ore della categoria richiesta
     * @param cat2 indica l'offerta del fruitore
     * @param fr   soggetto che esegue la richiesta (Fruitore)
     */

    public RichiestaScambio(Categoria catRichiesta,int ore,Categoria catOfferta, Fruitore fr){
        this.catRichiesta = catRichiesta; 
        this.catOfferta  = catOfferta; 
        this.fr=fr;
        this.oreRichieste=ore; 
        this.oreOffertte = calcolaOreOfferte(catRichiesta,catOfferta,oreRichieste);
        this.stato=true;
    }

    public boolean getStato(){
        return stato; 
    }


    public void setStato(boolean stato) {
        this.stato = stato;
    }


    public int calcolaOreOfferte(Categoria catRichiesta, Categoria catOfferta, int oreRichieste){
        
        return 0; 
    }

    public Fruitore getFr() {
        return fr;
    }
    
    


    
    
}