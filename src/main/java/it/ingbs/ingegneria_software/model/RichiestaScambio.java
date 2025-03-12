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

    


    public Categoria getCatRichiesta() {
        return catRichiesta;
    }

    public void setCatRichiesta(Categoria catRichiesta) {
        this.catRichiesta = catRichiesta;
    }

    public Categoria getCatOfferta() {
        return catOfferta;
    }

    public void setCatOfferta(Categoria catOfferta) {
        this.catOfferta = catOfferta;
    }

    public int getOreRichieste() {
        return oreRichieste;
    }

    public void setOreRichieste(int oreRichieste) {
        this.oreRichieste = oreRichieste;
    }

    public int getOreOffertte() {
        return oreOffertte;
    }

    public void setOreOffertte(int oreOffertte) {
        this.oreOffertte = oreOffertte;
    }

    /**
     * 
     * @param catRichiesta categoria richiesta dal fruitore
     * @param catOfferta  categorie offerta dal fruitore
     * @param oreRichieste numero di ore che vengono richieste dal fruitore per la categoria richiesta
     * @return numero di ore che il fruitore deve offrire
     */
    public int calcolaOreOfferte(Categoria catRichiesta, Categoria catOfferta, int oreRichieste){
        return (int) ((catOfferta.getFattore(catRichiesta))*oreRichieste);
    }

    public Fruitore getFr() {
        return fr;
    }


    
    public String toString(){
        StringBuffer sb = new StringBuffer();
         sb.append("Richiesta: [" + catRichiesta.getNome()+","+getOreRichieste()+"]");
         sb.append("Offerta: ["+catOfferta.getNome()+","+getOreOffertte()+"]");
         return sb.toString();
    }
    
    


    
    
}