package it.ingbs.ingegneria_software.model.richieste;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import it.ingbs.ingegneria_software.model.utenti.Fruitore;

public class GestoreCicliScambio {
    private final HashMap<Fruitore, List<RichiestaScambio>> mappaRichieste;
    private final HashMap<Integer, List<RichiestaScambio>> richiesteChiuse;
    private final Random random = new Random();
    private int codiceCicloCorrente;
    
    public GestoreCicliScambio(HashMap<Fruitore, List<RichiestaScambio>> mappaRichieste) {
        this.mappaRichieste = mappaRichieste;
        this.richiesteChiuse = new HashMap<>();
        this.codiceCicloCorrente = 1;
    }
    
     public void valutaRichieste(Fruitore fruitore, RichiestaScambio richiesta) {
        if(richiesta.getStato() == Stato.Aperto){
            if(valutazioneRichiesta(fruitore, richiesta)){
                richiesta.setStato(Stato.Chiuso);
            }
        }          
        
    }

    /*
     * Metodo che valuta una richiesta di scambio
     */
    public boolean valutazioneRichiesta(Fruitore proprietarioRichiesta, RichiestaScambio richiesta) {
        // prendo la mappa di tutte le richieste che hanno fruitori con lo stesso comprensorio di proprietario richiesta
        HashMap<Fruitore, List<RichiestaScambio>> mappaRichiesteComprensorio = new HashMap<>();
        for (Map.Entry<Fruitore, List<RichiestaScambio>> entry : mappaRichieste.entrySet()) {
            if (entry.getKey().getComprensorio() == (proprietarioRichiesta.getComprensorio())) {
                mappaRichiesteComprensorio.put(entry.getKey(), entry.getValue());
            }
        }

        // Verifica se esiste un ciclo di richieste soddisfatte
        boolean richiestaPrincipaleSoddisfatta = false;
        for (Map.Entry<Fruitore, List<RichiestaScambio>> entry : mappaRichiesteComprensorio.entrySet()) {
            for (RichiestaScambio r : entry.getValue()) {
                Set<RichiestaScambio> visited = new HashSet<>();
                if (isCyclic(r, visited, mappaRichiesteComprensorio)) {
                    // Chiude tutte le richieste coinvolte nel ciclo
                    for (RichiestaScambio richiestaCiclo : visited) {
                        richiestaCiclo.setStato(Stato.Chiuso);
                    }
                    // Aggiunge il set di richieste chiuse alla mappa richiesteChiuse
                    aggiungiRichiesteChiuse(visited);
                    if (visited.contains(richiesta)) {
                        richiestaPrincipaleSoddisfatta = true;
                    }
                }
            }
        }

        return richiestaPrincipaleSoddisfatta;
    }

    private void aggiungiRichiesteChiuse(Set<RichiestaScambio> richiesteSet) {
        // Converte il set in una lista per confronti
        List<RichiestaScambio> nuovaListaRichieste = new ArrayList<>(richiesteSet);

        // Controlla se il set è già presente nella mappa
        for (List<RichiestaScambio> listaEsistente : richiesteChiuse.values()) {
            if (listaEsistente.containsAll(nuovaListaRichieste) && nuovaListaRichieste.containsAll(listaEsistente)) {
                // Il set è già presente, non aggiungere duplicati
                return;
            }
            
        }
        // Genera un codice univoco e aggiunge il set alla mappa
        int codiceUnivoco = random.nextInt(9999);
        richiesteChiuse.put(codiceUnivoco, nuovaListaRichieste);
    }

    /*
     * Metodo che filtra le richieste
     */
    private boolean isCyclic(RichiestaScambio richiesta, Set<RichiestaScambio> visited, HashMap<Fruitore, List<RichiestaScambio>> mappaRichiesteComprensorio) {
        if (visited.contains(richiesta)) {
            return true;
        }

        visited.add(richiesta);

        for (Map.Entry<Fruitore, List<RichiestaScambio>> entry : mappaRichiesteComprensorio.entrySet()) {
            for (RichiestaScambio r : entry.getValue()) {
                if (richiesta.soddisfaRichiesta(r)) {
                    if (isCyclic(r, visited, mappaRichiesteComprensorio)) {
                        return true;
                    }
                }
            }
        }

        visited.remove(richiesta);
        return false;
    }

    public HashMap<Integer, List<RichiestaScambio>> getRichiesteChiuse() {
        return richiesteChiuse;
    }
}
