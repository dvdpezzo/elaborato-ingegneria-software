package it.ingbs.ingegneria_software.gestione_file.Convertitori;

import java.util.HashMap;

import it.ingbs.ingegneria_software.model.fattori.FattoriConversione;
import it.ingbs.ingegneria_software.model.gerarchie.Categoria;

public class ConvertiFattori {

    public static HashMap<String, FattoriConversione> convertFattori(HashMap<String, Double> fattori, HashMap<String, Categoria> categorie) {
        HashMap<String, FattoriConversione> mappaFattori = new HashMap<>();
        for (String chiave : fattori.keySet()) {
            Double valore = fattori.get(chiave);
            String[] categorieLette = chiave.split("->");
            Categoria categoria1 = categorie.get(categorieLette[0].trim());
            Categoria categoria2 = categorie.get(categorieLette[1].trim());
            FattoriConversione fattoreConversione = new FattoriConversione(valore, categoria1, categoria2);
            mappaFattori.put(chiave, fattoreConversione);
        }
        return mappaFattori;
    }
}