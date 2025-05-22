package it.ingbs.ingegneria_software.model.richieste;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.ingbs.ingegneria_software.model.utenti.Fruitore;

public class GestoreCicliScambio {
    private final HashMap<Fruitore, List<RichiestaScambio>> mappaRichieste;
    private final HashMap<Fruitore, List<RichiestaScambio>> richiesteChiuse;
    
    public GestoreCicliScambio(HashMap<Fruitore, List<RichiestaScambio>> mappaRichieste) {
        this.mappaRichieste = mappaRichieste;
        this.richiesteChiuse = new HashMap<>();
    }
    
    public boolean valutaRichiesta(Fruitore proprietario, RichiestaScambio richiesta) {
        HashMap<Fruitore, List<RichiestaScambio>> mappaComprensorio = filtraPerComprensorio(proprietario);
        return cercaCiclo(richiesta, mappaComprensorio);
    }

    private boolean cercaCiclo(RichiestaScambio richiesta, HashMap<Fruitore, List<RichiestaScambio>> mappaComprensorio) {
        boolean richiestaPrincipaleSoddisfatta = false;
        for (Map.Entry<Fruitore, List<RichiestaScambio>> entry : mappaComprensorio.entrySet()) {
            for (RichiestaScambio r : entry.getValue()) {
                Set<RichiestaScambio> visited = new HashSet<>();
                if (isCyclic(r, visited, mappaComprensorio)) {
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
    }

    private HashMap<Fruitore, List<RichiestaScambio>> filtraPerComprensorio(Fruitore proprietario) {
        HashMap<Fruitore, List<RichiestaScambio>> mappaRichiesteComprensorio = new HashMap<>();
        for (Map.Entry<Fruitore, List<RichiestaScambio>> entry : mappaRichieste.entrySet()) {
            if (entry.getKey().getComprensorio() == (proprietario.getComprensorio())) {
                mappaRichiesteComprensorio.put(entry.getKey(), entry.getValue());
            }
        }
        return mappaRichiesteComprensorio;
    }

    public HashMap<Fruitore, List<RichiestaScambio>> getRichiesteChiuse() {
        return richiesteChiuse;
    }
}
