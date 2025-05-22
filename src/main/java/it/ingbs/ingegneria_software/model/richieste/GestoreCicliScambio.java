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
    private final HashMap<Integer, List<RichiestaScambio>> richiesteChiuse;
    private int codiceCicloCorrente;
    
    public GestoreCicliScambio(HashMap<Fruitore, List<RichiestaScambio>> mappaRichieste) {
        this.mappaRichieste = mappaRichieste;
        this.richiesteChiuse = new HashMap<>();
        this.codiceCicloCorrente = 1;
    }
    
    public void valutaRichiesta(Fruitore proprietario, RichiestaScambio richiesta) {
        if (richiesta.getStato() == Stato.Chiuso) {
            Set<RichiestaScambio> cicloChiuso = ricostruisciCiclo(richiesta);
            if (cicloChiuso != null && !cicloChiuso.isEmpty()) {
                aggiungiRichiesteChiuse(cicloChiuso);
            }
            return;
        }
        
        HashMap<Fruitore, List<RichiestaScambio>> mappaComprensorio = filtraPerComprensorio(proprietario);
        Set<RichiestaScambio> cicloTrovato = cercaCiclo(richiesta, mappaComprensorio);
        
        if (cicloTrovato != null && !cicloTrovato.isEmpty()) {
            for (RichiestaScambio r : cicloTrovato) {
                r.setStato(Stato.Chiuso);
            }
            aggiungiRichiesteChiuse(cicloTrovato);
        }
    }

    private Set<RichiestaScambio> cercaCiclo(RichiestaScambio start, HashMap<Fruitore, List<RichiestaScambio>> mappaComprensorio) {
        // Prima cerca uno scambio diretto
        Set<RichiestaScambio> scambioDiretto = cercaScambioDiretto(start, mappaComprensorio);
        if (scambioDiretto != null) {
            return scambioDiretto;
        }

        // Se non trova uno scambio diretto, cerca un ciclo
        Set<RichiestaScambio> visited = new HashSet<>();
        Set<RichiestaScambio> cicloCorrente = new HashSet<>();
        
        if (trovaCicloRicorsivo(start, start, visited, cicloCorrente, mappaComprensorio)) {
            return cicloCorrente;
        }
        return null;
    }

    private Set<RichiestaScambio> cercaScambioDiretto(RichiestaScambio start, HashMap<Fruitore, List<RichiestaScambio>> mappaComprensorio) {
        for (List<RichiestaScambio> richieste : mappaComprensorio.values()) {
            for (RichiestaScambio r : richieste) {
                if (r.getStato() == Stato.Aperto && 
                    start.soddisfaRichiesta(r) && 
                    r.soddisfaRichiesta(start) &&
                    !isRichiestaInQualcheCiclo(r)) {
                    Set<RichiestaScambio> scambio = new HashSet<>();
                    scambio.add(start);
                    scambio.add(r);
                    return scambio;
                }
            }
        }
        return null;
    }

    private boolean isRichiestaInQualcheCiclo(RichiestaScambio richiesta) {
        for (List<RichiestaScambio> ciclo : richiesteChiuse.values()) {
            if (ciclo.contains(richiesta)) {
                return true;
            }
        }
        return false;
    }

    private boolean trovaCicloRicorsivo(RichiestaScambio current, RichiestaScambio start,
            Set<RichiestaScambio> visited, Set<RichiestaScambio> cicloCorrente,
            HashMap<Fruitore, List<RichiestaScambio>> mappaComprensorio) {
        
        visited.add(current);
        cicloCorrente.add(current);

        for (List<RichiestaScambio> richieste : mappaComprensorio.values()) {
            for (RichiestaScambio next : richieste) {
                if (next.getStato() == Stato.Aperto && 
                    current.soddisfaRichiesta(next) && 
                    !isRichiestaInQualcheCiclo(next)) {
                    if (!visited.contains(next)) {
                        if (trovaCicloRicorsivo(next, start, visited, cicloCorrente, mappaComprensorio)) {
                            return true;
                        }
                    } else if (next.equals(start) && cicloCorrente.size() >= 2) {
                        // Verifica che tutte le richieste nel ciclo siano compatibili con la successiva
                        return verificaCicloValido(cicloCorrente);
                    }
                }
            }
        }

        cicloCorrente.remove(current);
        return false;
    }

    private boolean verificaCicloValido(Set<RichiestaScambio> ciclo) {
        List<RichiestaScambio> cicloOrdinato = new ArrayList<>(ciclo);
        for (int i = 0; i < cicloOrdinato.size(); i++) {
            RichiestaScambio current = cicloOrdinato.get(i);
            RichiestaScambio next = cicloOrdinato.get((i + 1) % cicloOrdinato.size());
            if (!current.soddisfaRichiesta(next)) {
                return false;
            }
        }
        return true;
    }

    private void aggiungiRichiesteChiuse(Set<RichiestaScambio> ciclo) {
        if (ciclo == null || ciclo.isEmpty()) {
            return;
        }
        richiesteChiuse.put(codiceCicloCorrente++, new ArrayList<>(ciclo));
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

    private Set<RichiestaScambio> ricostruisciCiclo(RichiestaScambio start) {
        Set<RichiestaScambio> ciclo = new HashSet<>();
        ciclo.add(start);
        
        for (List<RichiestaScambio> richieste : mappaRichieste.values()) {
            for (RichiestaScambio r : richieste) {
                if (r.getStato() == Stato.Chiuso && start.soddisfaRichiesta(r)) {
                    ciclo.add(r);
                }
            }
        }
        
        return ciclo.size() >= 2 ? ciclo : null;
    }

    public HashMap<Integer, List<RichiestaScambio>> getRichiesteChiuse() {
        return richiesteChiuse;
    }
}
