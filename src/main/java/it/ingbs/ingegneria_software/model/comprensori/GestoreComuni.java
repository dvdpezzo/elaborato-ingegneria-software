package it.ingbs.ingegneria_software.model.comprensori;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.UtilityHandler;

public class GestoreComuni implements UtilityHandler {

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

        for (Map.Entry<Integer, String> entry : mappaComuni.entrySet()) {
            if (entry.getKey() == n) {
                return entry.getValue();
            }
        }

        return ERRORE_COMUNE_NON_TROVATO;
    }

    /**
     * Restituisce il numero più alto di comune.
     *
     * @return il numero più alto di comune
     */
    public int getLastNumero() {
        int max = 0;
        for (Map.Entry<Integer, String> entry : mappaComuni.entrySet()) {
            if (entry.getKey() > max) {
                max = entry.getKey();
            }
        }
        return max;
    }

    /**
     * Inserisce n comuni da una lista di comuni disponibili.
     * Se un comune viene scelto più volte, lo ripete fino a quando non viene scelto un altro.
     *
     * @param listaComuni la lista dei comuni disponibili
     * @param n il numero di comuni da inserire
     */
    public void inserimentoComuni(List<String> listaComuni, int n) {
        for (int i = 0; i < n; i++) {
            boolean comuneValido = false;
            while (!comuneValido) {
                int numeroComune = InputDati.leggiIntero(String.format(MSG_INSERISCI_NUMERO_COMUNE, i + 1));
                String comune = scegliComune(numeroComune);
                if (listaComuni.contains(comune)) {
                    System.out.println(MSG_COMUNE_GIA_INSERITO);
                } else {
                    listaComuni.add(comune);
                    comuneValido = true;
                }
            }
        }
    }

    /**
     * Verifica se un comune è presente in un comprensorio geografico.
     *
     * @param nomeComune il nome del comune da verificare
     * @return true se il comune è presente in un comprensorio, false altrimenti
     */
    private boolean isComuneInComprensorio(String nomeComune) {
        for (ComprensorioGeografico comprensorio : gestoreDati.getComprensori().values()) {
            if (comprensorio.getListaComuni().contains(nomeComune)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Rimuove un comune dalla lista dei comuni e aggiorna i numeri dei restanti comuni.
     *
     * @param numeroComune il numero del comune da rimuovere
     * @return true se il comune è stato rimosso, false altrimenti
     */
    private boolean rimuoviComune(int numeroComune) {
        if (mappaComuni.containsKey(numeroComune)) {
            String nomeComune = mappaComuni.get(numeroComune);

            // Verifica se il comune è presente in un comprensorio geografico
            if (isComuneInComprensorio(nomeComune)) {
                System.out.println("Errore: Il comune è presente in un comprensorio geografico e non può essere eliminato.");
                return false;
            }

            // Rimuove il comune specificato
            mappaComuni.remove(numeroComune);

            // Crea una nuova mappa temporanea per riassegnare i numeri
            HashMap<Integer, String> nuovaMappaComuni = new HashMap<>();
            int nuovoNumero = 1;

            // Riassegna i numeri in ordine crescente
            for (Map.Entry<Integer, String> entry : mappaComuni.entrySet()) {
                nuovaMappaComuni.put(nuovoNumero++, entry.getValue());
            }

            // Sostituisce la vecchia mappa con la nuova
            mappaComuni.clear();
            mappaComuni.putAll(nuovaMappaComuni);

            // Salva i dati dopo la rimozione
            gestoreDati.setComuni(mappaComuni);
            return true;
        } else {
            System.out.println(ERRORE_COMUNE_NON_TROVATO);
            return false;
        }
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
        String nomeComune = InputDati.leggiStringa("Inserisci il nome del comune da aggiungere:");
        if (!mappaComuni.containsValue(nomeComune.toUpperCase())) {
            int numeroComune = getLastNumero() + 1;
            mappaComuni.put(numeroComune, nomeComune.toUpperCase());
            System.out.println("Comune aggiunto con successo.");
        } else {
            System.out.println(MSG_COMUNE_GIA_INSERITO);
        }
    }

    @Override
    public void salva() {
        gestoreDati.setComuni(mappaComuni);
    }
}