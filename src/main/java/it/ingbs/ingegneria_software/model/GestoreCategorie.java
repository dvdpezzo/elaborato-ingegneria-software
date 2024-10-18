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

    public List<Categoria> creaGerarchiaRicorsiva(String nomeGerarchia ) {
        List<Categoria> gerarchia = new ArrayList<>();
        String nomeRadice = InputDati.leggiStringaNonVuota("Inserire nome Categoria radice: ");
        Categoria categoriaRadice = creaCategoria(nomeRadice);
        gerarchia.add(categoriaRadice);
        
        boolean aggiungiSottoCategorie = InputDati.yesOrNo("Vuoi aggiungere subito categorie a " + nomeRadice + "?");
        if (aggiungiSottoCategorie) {
            List<Categoria> sottocategorie = creaSottocategorieAutomatiche(categoriaRadice);
            gerarchia.addAll(sottocategorie);
        }
        categoriePerGerarchia.put(nomeGerarchia, gerarchia);
        return gerarchia;
    }
    
    private Categoria creaCategoria(String nome) {
        List<Valore> valoriCampo = chiediValoriCampo(nome);
        CampoCaratteristico campo = new CampoCaratteristico("Tipo", valoriCampo);
        return new Categoria(nome, campo);
    }
    
    private List<Categoria> creaSottocategorieAutomatiche(Categoria categoriaPadre) {
        List<Categoria> sottocategorie = new ArrayList<>();
        List<Valore> valoriCampo = categoriaPadre.getCampoCaratteristico().getDominioValori();

        for (Valore valore : valoriCampo) {
            String nomeSottocategoria = categoriaPadre.getNome() + " - " + valore.getNome();
            Categoria sottocategoria = creaCategoria(nomeSottocategoria);
            sottocategorie.add(sottocategoria);
        }

        return sottocategorie;
    }

    
    private List<Valore> chiediValoriCampo(String nome) {
        List<Valore> valoriCampo = new ArrayList<>();
        boolean aggiungiValore = InputDati.yesOrNo("Vuoi aggiungere campi caratteristici alla categoria " + nome + " attualmente selezionata?");
        if (aggiungiValore){
            while (aggiungiValore) {
                String nomeSottocategoria = InputDati.leggiStringaNonVuota("Inserire nome del campo caratteristico: ");
                valoriCampo.add(new Valore(nomeSottocategoria));
                
                aggiungiValore = InputDati.yesOrNo("Vuoi aggiungerne altre? ");
            }
        }else{
            //allora è una categoria foglia e andrebbero aggiunti i fattori di conversione
        }
        
        return valoriCampo;
    }
    
    public void aggiungiFattoreConversione(Categoria categoria1, Categoria categoria2, Double fattoreConversione) {
        categoria1.aggiungiFattoreConversione(categoria2, fattoreConversione);
    }

    public void visualizzaAlberoCategorie() {
        System.out.println("Albero delle categorie: ");
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