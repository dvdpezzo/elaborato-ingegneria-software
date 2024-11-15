package it.ingbs.ingegneria_software.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import it.ingbs.ingegneria_software.utilita_generale.InputDati;

/**
 * Classe che gestisce le categorie nel sistema
 * @author Davide
 */
public class GestoreCategorie {
    
    private static final Logger LOGGER = Logger.getLogger(GestoreCategorie.class.getName());
    private Map<String, List<Categoria>> categoriePerGerarchia;


    //OSS TENIAMO QUESTO IN QUANTO I METODI DI VISUALIZZAZIONE POSSONO ESSERE UTILI

    public void visualizzaAlberoCategorie() {
        LOGGER.info("Albero delle categorie: ");
        visualizzaCategorieRicorsiva(categoriePerGerarchia, "");
    }
    
    private void visualizzaCategorieRicorsiva(Map<String, List<Categoria>> categorie, String indentazione) {
        for (Map.Entry<String, List<Categoria>> entry : categorie.entrySet()) {
            String nomeRadice = entry.getKey();
            List<Categoria> gerarchia = entry.getValue();
    
            LOGGER.info(indentazione + nomeRadice);
            for (Categoria categoria : gerarchia) {
                visualizzaSottocategorieRicorsiva(categoria, indentazione + "  ");
            }
        }
    }
    
    private void visualizzaSottocategorieRicorsiva(Categoria categoria, String indentazione) {
        LOGGER.info(indentazione + categoria.getNome());
    
        for (Categoria sottocategoria : categoria.getSottocategorie()) {
            visualizzaSottocategorieRicorsiva(sottocategoria, indentazione + "  ");
        }
    }


}