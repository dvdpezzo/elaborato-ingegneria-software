package it.ingbs.ingegneria_software.model;
import java.io.File;
import java.io.IOException;
import java.util.*;

import it.ingbs.ingegneria_software.utilita_generale.InputDati;

public class Categoria {

  
    private String nome;  // Nome della categoria
    private String descrizione; //descrizione generale della categoria
    private HashMap<String,Categoria> mappaSottocategorie;
    


    // Costruttore per una categoria NON foglia
    public Categoria(String nome,String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.mappaSottocategorie= new HashMap<>();
    }
    
    /*
     * aggiunge una sottocategoria alla categoria padre
     */
    public void aggiungiFiglio(String nome, String descrizione){
        mappaSottocategorie.put(nome,new Categoria(nome, descrizione));
    }
    
    // Getter per il nome
    public String getNome() {
        return nome;
    }


    /*
     * controllo se la sottocategoria (figlio) aggiunta è già presente e se non lo è la aggiungo alle sottocategorie
     */
    public void controlloFiglio() {
        String nomeSottocat;
        String descSottocat;
        do{
             nomeSottocat = InputDati.leggiStringaNonVuota("Inserisci il nome della sottocategoria che vuoi aggiungere:");
        }while(controlloSottocat(nomeSottocat));
        
        descSottocat = InputDati.leggiStringa("Inserisci una descrizione per la sottocategoria:(facoltativo)");
        aggiungiFiglio(nomeSottocat,descSottocat);
    }

     /*
     * controllo se il nome della sottocategoria è gia presente (da mettere in categoria?)
     */
    private boolean controlloSottocat(String nome){
        for (String sottoCat : mappaSottocategorie.keySet()) 
        {
            return sottoCat.equalsIgnoreCase(nome);
        }

        return false;
    }

    public void salvaCategorie(File nomeFile) throws IOException{
        GestoreFile gf = new GestoreFile();
        for (Categoria  categoria : mappaSottocategorie.values()) {
            gf.salvaSuFileCategoria(nomeFile,categoria);

            
        }
    }


    


}
