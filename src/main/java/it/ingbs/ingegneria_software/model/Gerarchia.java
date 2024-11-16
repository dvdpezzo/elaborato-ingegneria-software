package it.ingbs.ingegneria_software.model;

import java.util.HashMap;

import it.ingbs.ingegneria_software.utilita_generale.InputDati;

public class Gerarchia {
    

    private final HashMap<String, Categoria> mappaCategorie;
    private final Categoria categoriaRadice;

    

    public Gerarchia(String nome, String descrizione) {
        Categoria categoriaRadice = new Categoria(nome.toUpperCase(), descrizione);
        mappaCategorie = new HashMap<>();
        mappaCategorie.put(categoriaRadice.getNome(),categoriaRadice);
        this.categoriaRadice = categoriaRadice;
    }


    public void aggiungiSottocategoria(){
        String nomePadre;
        do{
            nomePadre = InputDati.leggiStringaNonVuota("Inserisci il nome della categoria padre:");
        }while(!controlloNomeCategoria(nomePadre));
        Categoria padre = mappaCategorie.get(nomePadre);
        controlloFiglio(padre);
    }


    /*
     * controllo se la sottocategoria (figlio) aggiunta è già presente e se non lo è la aggiungo alle sottocategorie
     */
    private void controlloFiglio(Categoria padre) {
        String nomeSottocat;
        String descSottocat;
        do{
             nomeSottocat = InputDati.leggiStringaNonVuota("Inserisci il nome della sottocategoria che vuoi aggiungere:");
        }while(!controlloSottocat(nomeSottocat));
        
        descSottocat = InputDati.leggiStringa("Inserisci una descrizione per la sottocategoria:(facoltativo)");
        
        padre.aggiungiFiglio(nomeSottocat,descSottocat);
    }

    /*
     * controlla se il nome della categoria padre esiste 
     */
    private boolean controlloNomeCategoria(String nome){
        for (String nomiCategoria : mappaCategorie.keySet()) {
            return nomiCategoria.equalsIgnoreCase(nome);
            }
        return false;
     }

    
    /*
     * controllo se il nome della sottocategoria è gia presente (da mettere in categoria?)
     */
    private boolean controlloSottocat(String nome){
        return true;
    }

    public void eliminaSottocategoria(){}

}
