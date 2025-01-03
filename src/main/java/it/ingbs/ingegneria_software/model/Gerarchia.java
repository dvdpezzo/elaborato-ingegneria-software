package it.ingbs.ingegneria_software.model;

import java.util.HashMap;

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
        }while(!mappaCategorie.containsKey(nomePadre.toUpperCase()));
        Categoria padre = mappaCategorie.get(nomePadre.toUpperCase());
        padre.controlloFiglio();
    }


    
   
    public void eliminaSottocategoria(){}



    public HashMap<String, Categoria> getCategoriePrincipali() {
        return mappaCategorie;
    }


}
