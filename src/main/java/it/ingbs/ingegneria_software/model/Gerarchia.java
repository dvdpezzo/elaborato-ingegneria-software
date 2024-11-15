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
            nomePadre = InputDati.leggiStringaNonVuota("Insirisci il nome della caterogia padre:");
        }while(controlloNomeCategoria(nomePadre));
        Categoria padre = mappaCategorie.get(nomePadre);
        padre.aggiungiFiglio();
    }

    /*
     * controlla se il nome della categoria è univoco
     */
    private boolean controlloNomeCategoria(String nome){
        for (String nomiCategoria : mappaCategorie.keySet()) {
            return nomiCategoria.equals(nome);
            }
        return false;
     }

    public void eliminaSottocategoria(){}

}
