package it.ingbs.ingegneria_software.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che gestisce le categorie nel sistema
 * @author Davide
 */
public class GestoreCategorie {
    public List<Categoria> creaGerarchia(String nomeCategoria) {
        List<Categoria> gerarchia = new ArrayList<>();
        
        // Creiamo la categoria radice
        Categoria radice = new Categoria(nomeCategoria);
        boolean condition=false;
        
        // Creiamo le sottocategorie e aggiungiamo Sottocategorie alla categoria radice
        do {
            // Inserire qui il codice per creare una nuova sottocategoria
            Categoria nomeSottocategoria = creaCategoria();
            radice.aggiungiSottoCategoria(nomeSottocategoria);
        } while (condition); 
        
        // Aggiungiamo la categoria radice alla gerarchia
        gerarchia.add(radice);
        
        return gerarchia;
    }

    private Categoria creaCategoria(){
        return null;
    }

    private FattoreConversione creaFattoreConversione(){
        return null;
    }

}
