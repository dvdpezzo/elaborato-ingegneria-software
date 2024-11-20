package it.ingbs.ingegneria_software.model;


import java.util.HashMap;

public class Configuratore extends Utente  {

    private HashMap<String,FattoriConversione> mappaFattori;
    
    public Configuratore(String nomeUtente, String passwordUtente) {
        super(nomeUtente, passwordUtente);
        this.mappaFattori = new HashMap();
    }


    /*
     * assegno un fattore di conversione tra 2 categorie foglia 
     */
    public void assegnaFattoreConversione(CategoriaFoglia categoria1, CategoriaFoglia categoria2, double valore){
        //creo la chiave che è data dal nome delle 2 categorie foglia
        String chiave = categoria1.getNome()+" "+categoria2.getNome();
        
        //creo un fattore di conversione e lo aggiungo alla mappa dei fattori
        FattoriConversione fattore = new FattoriConversione(valore, categoria1, categoria2);
        mappaFattori.put(chiave,fattore);
        
        //creo in automatico il fattore opposto.
        fattoreOpposto(categoria1, categoria2, valore);

    }


    /*
     * Crea un fattore di conversione opposto rispeto a quello appena creato. 
     */
    public void fattoreOpposto(CategoriaFoglia categoria1, CategoriaFoglia categoria2, double valore)
    {
        String chiaveOpposta = categoria2.getNome()+" "+categoria1.getNome();

        FattoriConversione fattoreOpposto = new FattoriConversione(valore, categoria2, categoria1);
        mappaFattori.put(chiaveOpposta,fattoreOpposto);
    }




    /*
     * Controllo se esistono i valori di conversione da 1 a 2 e da 2 a 3, se esistono creo il valore da 1 a 3 e lo aggiungo alla mappa.
     */
    public void fattoreDerivato(CategoriaFoglia cat1, CategoriaFoglia cat2, CategoriaFoglia cat3){
        String controllo12 = cat1.getNome()+" "+cat2.getNome();
        String controllo23 = cat2.getNome()+" "+cat3.getNome();
        if(mappaFattori.containsKey(controllo12) && mappaFattori.containsKey(controllo23)){
            double val1 = mappaFattori.get(controllo12).getValore();
            double val2 = mappaFattori.get(controllo23).getValore();
            double valDerivato = val1*val2;

            //aggiungo il valore derivato alla mappa (da cat1 a cat3)

            assegnaFattoreConversione(cat1, cat3, valDerivato);
        }
        
    }



    /*
     * OSS: Salvataggio dei valori di conversione ancora da implementare 
     */

    



}
