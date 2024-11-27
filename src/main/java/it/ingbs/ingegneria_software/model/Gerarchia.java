package it.ingbs.ingegneria_software.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import it.ingbs.ingegneria_software.utilita_generale.InputDati;

public class Gerarchia {
    

    private static final String INSERISCI_IL_NOME_DELLA_CATEGORIA_PADRE = "Inserisci il nome della categoria padre:";
    private final HashMap<String, Categoria> mappaCategorie;
    private final Categoria categoriaRadice;

    

    public Gerarchia(String nome) {
        Categoria categoriaRadice = new Categoria(nome.toUpperCase(), "");
        mappaCategorie = new HashMap<>();
        mappaCategorie.put(categoriaRadice.getNome(),categoriaRadice);
        this.categoriaRadice = categoriaRadice;
    }


    public void aggiungiSottocategoria(){
        String nomePadre;
        do{
            nomePadre = InputDati.leggiStringaNonVuota(INSERISCI_IL_NOME_DELLA_CATEGORIA_PADRE);
        }while(!controlloNomeCategoria(nomePadre));
        Categoria padre = mappaCategorie.get(nomePadre.toUpperCase());
        padre.controlloFiglio();
    }


    /*
     * controlla se il nome della categoria padre esiste 
     */
    private boolean controlloNomeCategoria(String nome){
        for (String nomiCategoria : mappaCategorie.keySet()) {
            return nomiCategoria.equals(nome.toUpperCase());
            }
        return false;
     }

    
   
    public void eliminaSottocategoria(){}



    public Map<String, Categoria> getCategoriePrincipali() {
        return mappaCategorie;
    }


}
