package it.ingbs.ingegneria_software.model;

import java.util.ArrayList;
import java.util.List;

import it.ingbs.ingegneria_software.utilita_generale.InputDati;

/**
 * Classe che gestisce le categorie nel sistema
 * @author Davide
 */
public class GestoreCategorie {
    
    public List<Categoria> creaGerarchia(String nomeCategoriaRadice) {
        List<Categoria> gerarchia = new ArrayList<>();
        
        // Creiamo la categoria radice
        Categoria radice = new Categoria(nomeCategoriaRadice);
        boolean condition=false;
        Categoria subCategoria;
        // Creiamo le sottocategorie e aggiungiamo Sottocategorie alla categoria radice
        do {
            // Inserire qui il codice per creare una nuova sottocategoria
            String nomeSubCategoria = InputDati.leggiStringa("Inserisci il nome della sottocategoria:");
            boolean esci = InputDati.yesOrNo("Vuoi aggiungere un'altra sottocategoria?");
            condition =!esci;  // Se esci è true, non creare più sottocategorie per questa categoria radice
            
            // Creiamo la nuova sottocategoria e la aggiungiamo alla categoria radice
            CampoCaratteristico campoCaratteristico = creaCampoCaratteristico();
            FattoreConversione fattoreConversione = creaFattoreConversione();
            if (fattoreConversione!= null) {
                subCategoria = new Categoria(nomeSubCategoria, campoCaratteristico, fattoreConversione);
            } else {
                subCategoria = new Categoria(nomeSubCategoria, campoCaratteristico, fattoreConversione);
                subCategoria = creaCategoria(nomeCategoriaRadice);
            radice.aggiungiSottoCategoria(subCategoria);
        } while (condition); 
        
        // Aggiungiamo la categoria radice alla gerarchia
        gerarchia.add(radice);
        
        return gerarchia;
    }

    private Categoria creaCategoria(String nomeCategoria){
        return new Categoria(nomeCategoria);
    }

    private FattoreConversione creaFattoreConversione(){
        return null;
    }

}
