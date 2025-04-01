package it.ingbs.ingegneria_software.model.utenti;


import java.util.ArrayList;

import it.ingbs.ingegneria_software.model.RichiestaScambio;


public class Fruitore extends Utente{

    private final int codiceComprensorio ;
    private final String email;
    private final ArrayList<RichiestaScambio> elencoRichieste = new ArrayList<>();



    /*
    * Creo un nuovo oggetto Fruitore
    */
    public Fruitore(String nomeUtente, String passUtente, int codiceComprensorio ,String email){
        super(nomeUtente, passUtente);
        this.codiceComprensorio= codiceComprensorio;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public int getComprensorio() {
        return codiceComprensorio;
    }
    
    /**
     * visualizza a video le informazioni di un utente
     */
    public void infoFruitore(){
        StringBuffer sb = new StringBuffer();
        sb.append(this.getNomeUtente());
        sb.append((this.getPassword()));
        sb.append(String.valueOf(this.getComprensorio()));
        sb.append((this.getEmail()));
        sb.toString();
     }


    /**
    * Aggiunge una richiesta di scambio all'elenco delle richieste
    * @param rs richiesta aggiunta all'arrayList
    */
     public void aggiungiRichiesta(RichiestaScambio rs){
        elencoRichieste.add(rs);
     }

     /**
      * Restituisce l'elenco delle richieste di scambio
      * @return
      */
     public ArrayList<RichiestaScambio> getElencoRichieste(){
        return elencoRichieste;
     }
        

    

}
