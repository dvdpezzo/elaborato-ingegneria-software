package it.ingbs.ingegneria_software.model.comprensori;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.utilita_generale.*;

public class GestoreComuni implements Rimuovibile,Salvabile,Visualizzabile,Aggiungibile {

    private static final String ERRORE_COMUNE_NON_TROVATO = "Comune non trovato!";
    private static final String MSG_COMUNE_GIA_INSERITO = "Questo comune è già stato inserito!";
    private static final String MSG_INPUT_OUT_OF_RANGE = "Input out of range. Please enter a valid comune number:";
    private static final String MSG_INSERISCI_NUMERO_COMUNE = "Inserisci il numero del %d° comune:";

    private final HashMap<Integer, String> mappaComuni;
    private final GestoreDati gestoreDati;

    public GestoreComuni(GestoreDati gestoreDati) {
        this.gestoreDati = gestoreDati;
        this.mappaComuni = gestoreDati.getComuni();  
    
    }

    /**
     * Controlla se un comune è già presente nella lista.
     *
     * @param comune il comune da controllare
     * @return true se il comune è già presente, false altrimenti
     */
    public boolean controlloComuni(String comune) {
        return mappaComuni.containsValue(comune.toUpperCase());
    }

    /**
     * Sceglie un comune in base al numero.
     *
     * @param n il numero del comune
     * @return il nome del comune scelto
     */
    public String scegliComune(int n) {
        while (n < 1 || n > mappaComuni.size()) {
            n = InputDati.leggiIntero(MSG_INPUT_OUT_OF_RANGE);
        }
        return mappaComuni.getOrDefault(n, ERRORE_COMUNE_NON_TROVATO);
    }

    /**
     * Restituisce il numero più alto di comune.
     *
     * @return il numero più alto di comune
     */
    public int getLastNumero() {
        return mappaComuni.isEmpty() ? 0 : Collections.max(mappaComuni.keySet());
    }

    /**
     * Inserisce n comuni da una lista di comuni disponibili.
     * Se un comune viene scelto più volte, lo ripete fino a quando non viene scelto un altro.
     *
     * @param listaComuni la lista dei comuni disponibili
     * @param n il numero di comuni da inserire
     */
    public void inserimentoComuni(List<String> listaComuni, int n) {
        IntStream.range(0, n).forEach(i -> {
            while (true) {
                String comune = scegliComune(InputDati.leggiIntero(String.format(MSG_INSERISCI_NUMERO_COMUNE, i + 1)));
                if (!listaComuni.contains(comune)) {
                    listaComuni.add(comune);
                    break;
                }
                System.out.println(MSG_COMUNE_GIA_INSERITO);
            }
        });
    }

    /**
     * Verifica se un comune è presente in un comprensorio geografico.
     *
     * @param nomeComune il nome del comune da verificare
     * @return true se il comune è presente in un comprensorio, false altrimenti
     */
    private boolean isComuneInComprensorio(String nomeComune) {
        return gestoreDati.getComprensori().values().stream()
                .anyMatch(c -> c.getListaComuni().contains(nomeComune));
    }

    /**
     * Rimuove un comune dalla lista dei comuni e aggiorna i numeri dei restanti comuni.
     *
     * @param numeroComune il numero del comune da rimuovere
     * @return true se il comune è stato rimosso, false altrimenti
     */
    private boolean rimuoviComune(int numeroComune) {
        if (!mappaComuni.containsKey(numeroComune)) {
            System.out.println(ERRORE_COMUNE_NON_TROVATO);
            return false;
        }

        String nomeComune = mappaComuni.get(numeroComune);
        if (isComuneInComprensorio(nomeComune)) {
            System.out.println("Errore: Il comune è presente in un comprensorio geografico e non può essere eliminato.");
            return false;
        }

        mappaComuni.remove(numeroComune);
        
        // Riordina i numeri dei comuni rimanenti
        TreeMap<Integer, String> comuniOrdinati = new TreeMap<>(mappaComuni);
        mappaComuni.clear();
        int num = 1;
        for (String value : comuniOrdinati.values()) {
            mappaComuni.put(num++, value);
        }
        
        gestoreDati.setComuni(mappaComuni);
        return true;
    }

    @Override
    public void view() {
        for (Map.Entry<Integer, String> entry : mappaComuni.entrySet()) {
            System.out.println(String.format("%d) Comune: %s", entry.getKey(), entry.getValue()));
        }
    }

    @Override
    public void rimuovi() {
        view();
        int numeroComune = InputDati.leggiIntero("Inserisci il numero del comune da rimuovere:");
        if (rimuoviComune(numeroComune)) {
            System.out.println("Comune rimosso con successo.");
        }
    }

    @Override
    public void aggiungi() {
        String nomeComune = InputDati.leggiStringa("Inserisci il nome del comune da aggiungere:").toUpperCase();
        if (mappaComuni.containsValue(nomeComune)) {
            System.out.println(MSG_COMUNE_GIA_INSERITO);
            return;
        }
        mappaComuni.put(getLastNumero() + 1, nomeComune);
        System.out.println("Comune aggiunto con successo.");
    }

    @Override
    public void salva() {
        gestoreDati.setComuni(mappaComuni);
    }
}