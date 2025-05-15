package it.ingbs.ingegneria_software.model.fattori;

import java.util.HashMap;
import java.util.Map;

import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.model.gerarchie.CategoriaFoglia;
import it.ingbs.ingegneria_software.model.gerarchie.Gerarchia;
import it.ingbs.ingegneria_software.model.gerarchie.GestoreGerarchie;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.UtilityHandler;

public class GestoreFattori implements UtilityHandler {


    private final GestoreGerarchie gestoreGerarchie;
    private final GestoreDati gestoreDati;
    private final Map<String, FattoriConversione> mappaFattori;
    

    public GestoreFattori(GestoreGerarchie gestoreGerarchie, GestoreDati gestoreDati) {
        this.gestoreGerarchie = gestoreGerarchie;
        this.gestoreDati = gestoreDati;
        this.mappaFattori = gestoreDati.getFattori();
        
    }

    private FattoriConversione creaFattore(CategoriaFoglia catOfferta, CategoriaFoglia catRichiesta, double valoreConversione) {
        FattoriConversione fattore = new FattoriConversione(valoreConversione, catOfferta, catRichiesta);
        mappaFattori.put(catOfferta.getNome() + "->" + catRichiesta.getNome(), fattore);
        catOfferta.setFattoriConversione(fattore);
        return fattore;
    }

    private FattoriConversione creaFattoreOpposto(FattoriConversione fattore) {
        FattoriConversione opposto = new FattoriConversione(1 / fattore.getValoreConversione(), fattore.getCategoriaRichiesta(), fattore.getCategoriaOfferta());
        mappaFattori.put(fattore.getCategoriaRichiesta().getNome() + "->" + fattore.getCategoriaOfferta().getNome(), opposto);
        fattore.getCategoriaRichiesta().setFattoriConversione(opposto);
        return opposto;
    }

    private FattoriConversione creaFattoreDerivato(FattoriConversione fattore1, FattoriConversione fattore2) {
        if (!fattore1.getCategoriaRichiesta().equals(fattore2.getCategoriaOfferta())) {
            throw new IllegalArgumentException("Le categorie non sono compatibili per creare un fattore derivato.");
        }
        double valoreDerivato = fattore1.getValoreConversione() * fattore2.getValoreConversione();
        FattoriConversione derivato = new FattoriConversione(valoreDerivato, fattore1.getCategoriaOfferta(), fattore2.getCategoriaRichiesta());
        mappaFattori.put(fattore1.getCategoriaOfferta().getNome() + "->" + fattore2.getCategoriaRichiesta().getNome(), derivato);
        fattore1.getCategoriaOfferta().setFattoriConversione(derivato);

        // Creazione ricorsiva dei fattori derivati
        for (FattoriConversione fattoreEsistente : mappaFattori.values()) {
            if (fattoreEsistente.getCategoriaOfferta().equals(fattore2.getCategoriaRichiesta())) {
                creaFattoreDerivato(derivato, fattoreEsistente);
            } else if (fattoreEsistente.getCategoriaRichiesta().equals(fattore1.getCategoriaOfferta())) {
                creaFattoreDerivato(fattoreEsistente, derivato);
            }
        }

        return derivato;
    }

    @Override
    public void view() {
        System.out.println("Fattori di conversione:");
        for (Map.Entry<String, FattoriConversione> entry : mappaFattori.entrySet()) {
            String chiave = entry.getKey();
            FattoriConversione fattore = entry.getValue();
            System.out.println(chiave + ": " + fattore);
        }
    }

    @Override
    public void rimuovi() {
        String nomeCategoria = InputDati.leggiStringaNonVuota("Nome della categoria foglia da rimuovere: ");
        CategoriaFoglia categoriaDaRimuovere = gestoreGerarchie.getRadici().values().stream()
                .flatMap(gerarchia -> gerarchia.getCategorieFoglia().stream())
                .filter(categoria -> categoria.getNome().equalsIgnoreCase(nomeCategoria))
                .findFirst()
                .orElse(null);

        if (categoriaDaRimuovere != null) {
            CategoriaFoglia finalCategoriaDaRimuovere = categoriaDaRimuovere;
            mappaFattori.entrySet().removeIf(entry -> 
                entry.getValue().getCategoriaOfferta().equals(finalCategoriaDaRimuovere) ||
                entry.getValue().getCategoriaRichiesta().equals(finalCategoriaDaRimuovere)
            );
            System.out.println("Fattori associati alla categoria rimossi.");
        } else {
            System.out.println("Categoria foglia non trovata.");
        }
    }

    @Override
    public void aggiungi() {
        // Chiede all'utente i nomi delle categorie foglia e il valore di conversione
        String nomeCatOfferta = InputDati.leggiStringaNonVuota("Inserisci il nome della categoria offerta: ");
        String nomeCatRichiesta = InputDati.leggiStringaNonVuota("Inserisci il nome della categoria richiesta: ");
        double valoreConversione = InputDati.leggiDoubleLimitato("Inserisci il valore di conversione: ", 0.5, 2.0);

        // Trova le categorie foglia corrispondenti
        CategoriaFoglia catOfferta = getCatFogliaByName(nomeCatOfferta);        
        CategoriaFoglia catRichiesta = getCatFogliaByName(nomeCatRichiesta);

        // Crea il fattore e il fattore opposto
        FattoriConversione fattore = creaFattore(catOfferta, catRichiesta, valoreConversione);
        FattoriConversione fattoreOpposto = creaFattoreOpposto(fattore);

        // Crea i fattori derivati per eventuali categorie già esistenti
        for (FattoriConversione fattoreEsistente : mappaFattori.values()) {
            if (fattoreEsistente.getCategoriaRichiesta().equals(catOfferta)) {
                creaFattoreDerivato(fattoreEsistente, fattore);
            } else if (fattoreEsistente.getCategoriaOfferta().equals(catRichiesta)) {
                creaFattoreDerivato(fattore, fattoreEsistente);
            }
        }

        System.out.println("Fattore, fattore opposto e fattori derivati creati con successo.");
    }

    private CategoriaFoglia getCatFogliaByName(String nomeCategoria ) {
        CategoriaFoglia catFoglia = gestoreGerarchie.getRadici().values().stream()
                .flatMap(gerarchia -> gerarchia.getCategorieFoglia().stream())
                .filter(categoria -> categoria.getNome().equalsIgnoreCase(nomeCategoria))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Categoria non trovata."));
        return catFoglia;
    }

    @Override
    public void salva() {
        gestoreDati.setFattori((HashMap<String, FattoriConversione>) mappaFattori);
    }

    public void viewGerarchie() {
        System.out.println("Categorie Foglia:");
        for(Gerarchia gerarchia : gestoreGerarchie.getRadici().values()) {
            for(CategoriaFoglia categoria : gerarchia.getCategorieFoglia()) {
                System.out.println(categoria.toString(0));
            }
        }
        
    }


}