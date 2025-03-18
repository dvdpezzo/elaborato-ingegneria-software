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
        if(getStato().equals(Stato.Chiuso)){
            this.stato = Stato.Chiuso;
        }
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


    /**
     * Metodo che restituisce una stringa contenente le informazioni della richiesta di scambio
     * @return stringa contenente le informazioni della richiesta di scambio
     */
     
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



    /**
     * Metodo che verifica se una richiesta di scambio è compatibile con un'altra.
     * Due richieste sono compatibili se:
     * - La categoria richiesta di una corrisponde alla categoria offerta dell'altra.
     * - La categoria offerta di una corrisponde alla categoria richiesta dell'altra.
     * - Le ore richieste e offerte corrispondono.
     * - Lo stato della richiesta è "Aperto".
     *
     * @param richiesta richiesta di scambio da confrontare
     * @return true se le due richieste sono compatibili, false altrimenti
     */
    public boolean trovaRichiestaScambio(RichiestaScambio richiesta) {
        return richiesta.getCatRichiesta().equals(this.catOfferta) &&
            richiesta.getCatOfferta().equals(this.catRichiesta) &&
            richiesta.getOreRichieste() == this.oreOfferte &&
            richiesta.getOreOfferte() == this.oreRichieste &&
            richiesta.getStato().equals(Stato.Aperto);
    }

    
    



    
}