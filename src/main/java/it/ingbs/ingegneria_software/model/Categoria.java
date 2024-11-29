package it.ingbs.ingegneria_software.model;

import java.util.HashMap;

import it.ingbs.ingegneria_software.utilita_generale.InputDati;

public class Categoria {

  
    private static final String INSERISCI_UNA_DESCRIZIONE_PER_LA_SOTTOCATEGORIA = "Inserisci una descrizione per la sottocategoria:(facoltativo)";
    private static final String INSERISCI_IL_NOME_DELLA_SOTTOCATEGORIA = "Inserisci il nome della sottocategoria che vuoi aggiungere:";
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
             nomeSottocat = InputDati.leggiStringaNonVuota(INSERISCI_IL_NOME_DELLA_SOTTOCATEGORIA);
        }while(controlloSottocat(nomeSottocat));
        
        descSottocat = InputDati.leggiStringa(INSERISCI_UNA_DESCRIZIONE_PER_LA_SOTTOCATEGORIA);
        aggiungiFiglio(nomeSottocat,descSottocat);
    }

     /*
     * controllo se il nome della sottocategoria è gia presente (da mettere in categoria?)
     */
    public boolean controlloSottocat(String nome){
        for (String sottoCat : mappaSottocategorie.keySet()) 
        {
            return sottoCat.equalsIgnoreCase(nome);
        }
        return false;
    }


    public boolean isFoglia() {
        return mappaSottocategorie.isEmpty();
    }

    public HashMap<String, Categoria> getSottoCategorie() {
        return mappaSottocategorie;
    }

    public String getDescrizione() {
        return descrizione;
    }


    


    


    


}
