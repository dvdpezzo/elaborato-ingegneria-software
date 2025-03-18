package it.ingbs.ingegneria_software.model;

import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;


public class RichiestaScambio {

    private Categoria catRichiesta; 
    private Categoria catOfferta; 
    private Fruitore fr; 
    private int oreRichieste; 
    private int oreOfferte;
    private Stato stato;

    /**
     * 
     * @param cat1 indica la richieste effettuata dal fruitore
     * @param ore  numero di ore della categoria richiesta
     * @param cat2 indica l'offerta del fruitore
     * @param fr   soggetto che esegue la richiesta (Fruitore)
     */

    public RichiestaScambio(Categoria catRichiesta, int ore, Categoria catOfferta, Fruitore fr, Double fattoreConv, Stato stato) {
        this.catRichiesta = catRichiesta;
        this.catOfferta = catOfferta;
        this.fr = fr;
        this.oreRichieste = ore;
        this.oreOfferte = calcolaOreOfferte(fattoreConv, ore);
        this.stato = stato;
    }



    public Stato getStato(){
        return stato; 
    }

    public void setStato(Stato stato){
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

    public int getOreOfferte() {
        return oreOfferte;
    }

    public void setOreOfferte(int oreOffertte) {
        this.oreOfferte = oreOffertte;
    }

    /**
     * 
     * @param catRichiesta categoria richiesta dal fruitore
     * @param catOfferta  categorie offerta dal fruitore
     * @param oreRichieste numero di ore che vengono richieste dal fruitore per la categoria richiesta
     * @return numero di ore che il fruitore deve offrire
     */
    public int calcolaOreOfferte(Double fattoreConv, int oreRichieste){
        return (int) (fattoreConv*oreRichieste);
    }

    public Fruitore getFr() {
        return fr;
    }


    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
         sb.append("Richiesta: [" + catRichiesta.getNome()+","+getOreRichieste()+"]");
         sb.append("\n");
         sb.append("Offerta: ["+catOfferta.getNome()+","+getOreOfferte()+"]");
         sb.append("\n");
         sb.append("Stato: "+getStato().toString());
         return sb.toString();
    }
    
    



    
}