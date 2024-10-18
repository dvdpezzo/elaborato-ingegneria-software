package it.ingbs.ingegneria_software.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.ingbs.ingegneria_software.utilita_generale.InputDati;

/**
 * Classe che gestisce le categorie nel sistema
 * @author Davide
 */
public class GestoreCategorie {
    
    private Map<String, List<Categoria>> categoriePerGerarchia;

    public GestoreCategorie() {
        categoriePerGerarchia = new HashMap<>();
    }

    public List<Categoria> creaGerarchiaRicorsiva(String nomeRadice) {
        List<Categoria> gerarchia = new ArrayList<>();
        Categoria categoriaRadice = creaCategoria(nomeRadice);
        gerarchia.add(categoriaRadice);
        
        boolean aggiungiSottoCategorie = InputDati.yesOrNo("Vuoi aggiungere sottocategorie a " + nomeRadice + "?");
        if (aggiungiSottoCategorie) {
            List<Categoria> sottocategorie = creaSottocategorie(categoriaRadice);
            gerarchia.addAll(sottocategorie);
        }
        categoriePerGerarchia.put(nomeRadice, gerarchia);
        return gerarchia;
    }
    
    private Categoria creaCategoria(String nome) {
        List<Valore> valoriCampo = chiediValoriCampo();
        CampoCaratteristico campo = new CampoCaratteristico("Tipo", valoriCampo);
        return new Categoria(nome, campo);
    }
    
    private List<Categoria> creaSottocategorie(Categoria categoriaPadre) {
        List<Categoria> sottocategorie = new ArrayList<>();
        boolean aggiungiSottoCategoria = true;
        
        while (aggiungiSottoCategoria) {
            String nomeSottocategoria = InputDati.leggiStringaNonVuota("Inserisci il nome della sottocategoria di " + categoriaPadre.getNome() + ":");
            Categoria sottocategoria = creaCategoria(nomeSottocategoria);
            sottocategorie.add(sottocategoria);
            
            aggiungiSottoCategoria = InputDati.yesOrNo("Vuoi aggiungere altre sottocategorie a " + categoriaPadre.getNome() + "?");
        }
        
        return sottocategorie;
    }
    
    private List<Valore> chiediValoriCampo() {
        List<Valore> valoriCampo = new ArrayList<>();
        boolean aggiungiValore = true;
        
        while (aggiungiValore) {
            String valore = InputDati.leggiStringaNonVuota("Inserisci un valore per il campo caratteristico:");
            valoriCampo.add(new Valore(valore));
            
            aggiungiValore = InputDati.yesOrNo("Vuoi aggiungere altri valori al campo caratteristico?");
        }
        
        return valoriCampo;
    }
    
    public void aggiungiFattoreConversione(Categoria categoria1, Categoria categoria2, Double fattoreConversione) {
        categoria1.aggiungiFattoreConversione(categoria2, fattoreConversione);
    }

    public void visualizzaAlberoCategorie() {
        System.out.println("Albero delle categorie:");
        visualizzaCategorieRicorsiva(categoriePerGerarchia, "");
    }
    
    private void visualizzaCategorieRicorsiva(Map<String, List<Categoria>> categorie, String indentazione) {
        for (Map.Entry<String, List<Categoria>> entry : categorie.entrySet()) {
            String nomeRadice = entry.getKey();
            List<Categoria> gerarchia = entry.getValue();
    
            System.out.println(indentazione + nomeRadice);
            for (Categoria categoria : gerarchia) {
                visualizzaSottocategorieRicorsiva(categoria, indentazione + "  ");
            }
        }
    }
    
    private void visualizzaSottocategorieRicorsiva(Categoria categoria, String indentazione) {
        System.out.println(indentazione + categoria.getNome());
    
        for (Categoria sottocategoria : categoria.getSottocategorie()) {
            visualizzaSottocategorieRicorsiva(sottocategoria, indentazione + "  ");
        }
    }


}