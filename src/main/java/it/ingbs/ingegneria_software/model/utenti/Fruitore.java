package it.ingbs.ingegneria_software.model.utenti;

import it.ingbs.ingegneria_software.model.comprensori.GestoreComprensorio;

import java.util.ArrayList;

import it.ingbs.ingegneria_software.model.RichiestaScambio;
import it.ingbs.ingegneria_software.model.comprensori.ComprensorioGeografico;


public class Fruitore extends Utente{

    private final GestoreComprensorio gestoreComprensorio;
    private ComprensorioGeografico comprensorio;
    private String email;
    private ArrayList<RichiestaScambio> elencoRichieste = new ArrayList<RichiestaScambio>();



    /*
    * Creo un nuovo oggetto Fruitore
    */
    public Fruitore(String nomeUtente, String passUtente, ComprensorioGeografico comprensorio,String email){
        super(nomeUtente, passUtente);
        this.comprensorio=comprensorio;
        this.gestoreComprensorio = new GestoreComprensorio();
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public ComprensorioGeografico getComprensorio() {
        return comprensorio;
    }
    
    /**
     * visualizza a video le informazioni di un utente
     */
    public void infoFruitore(){
        StringBuffer sb = new StringBuffer();
        sb.append(this.getNomeUtente());
        sb.append((this.getPassword()));
        sb.append((this.getComprensorio().toString()));
        sb.append((this.getEmail()));
        sb.toString();
     }

     
        

    

}
