package it.ingbs.ingegneria_software.model.comprensori;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.UtilityHandler;

// Classe che gestisce l'elenco dei comprensori inseriti dal configuratore
public class GestoreComprensorio implements UtilityHandler {

    private static final String INSERISCI_IL_CODICE_DEL_COMPRENSORIO_DA_RIMUOVERE = "Inserisci il codice del comprensorio da rimuovere:";
    private static final String COMPRENSORIO_AGGIUNTO = "Comprensorio aggiunto.";
    private static final String COMPRENSORIO_GIA_ESISTENTE = "Comprensorio già esistente.";
    private static final String CODICE_DEL_COMPRENSORIO_SBAGLIATO = "Codice del comprensorio sbagliato!";
    private static final String COMPRENSORIO_RIMOSSO_CON_SUCCESSO = "Comprensorio rimosso con successo.";
    private static final String CODICE_DEL_COMPRENSORIO_NON_TROVATO = "Codice del comprensorio non trovato.";
    private static final String INSERISCI_IL_CODICE_DEL_COMPRENSORIO_AL_QUALE_SI_VUOLE_AGGIUNGERE_IL_COMUNE = "Inserisci il codice del comprensorio al quale si vuole aggiungere il comune:";
    private static final String INSERISCI_IL_NOME_DEL_COMUNE = "Inserisci il nome del comune:";
    private static final String VUOI_AGGIUNGERE_UN_ALTRO_COMUNE = "Vuoi aggiungere un altro comune?";
    private static final int MIN_NUMERO_COMUNI_COMPRENSORIO = 3;

    private final HashMap<Integer, ComprensorioGeografico> mappaComprensori;
    private final GestoreDati gestoreDati;

    /**
     * Costruttore che legge i comprensori dal file e li carica nella mappa.
     */
    public GestoreComprensorio(GestoreDati gestoreDati) {
        this.gestoreDati = gestoreDati;
        this.mappaComprensori = gestoreDati.getComprensori();
    }

    /**
     * Restituisce la mappa dei comprensori.
     *
     * @return mappa dei comprensori
     */
    public HashMap<Integer, ComprensorioGeografico> getMappaComprensori() {
        return mappaComprensori;
    }


    public void impostaComuni(){
        for (ComprensorioGeografico comprensorio : mappaComprensori.values()) {
            comprensorio.setGestoreComuni(new GestoreComuni(gestoreDati));
        }
    }

    /**
     * Controlla se il comprensorio sia già presente oppure no.
     *
     * @param comprensorioNuovo il nuovo comprensorio da controllare
     * @return il comprensorio duplicato se esiste, altrimenti null
     */
    private ComprensorioGeografico controlloComprensorioDuplicato(ComprensorioGeografico comprensorioNuovo) {
        for (ComprensorioGeografico comprensorio : mappaComprensori.values()) {
            if (comprensorio.equals(comprensorioNuovo)) {
                return comprensorio;
            }
        }
        return null;
    }

    /**
     * Cerca un comprensorio per codice.
     *
     * @param codice il codice del comprensorio da cercare
     * @return il comprensorio trovato, altrimenti null
     */
    public ComprensorioGeografico getComprensorio(int codice) {
        return mappaComprensori.get(codice);
    }

    /**
     * Controlla se il comprensorio esiste in base al codice.
     *
     * @param codiceComprensorio il codice del comprensorio da controllare
     * @return true se il comprensorio esiste, altrimenti false
     */
    private boolean controllaEsistenzaComprensorio(int codiceComprensorio) {
        return getComprensorio(codiceComprensorio) != null;
    }


    /**
     * Aggiunge un comune a un comprensorio già esistente.
     *
    */
    public void aggiungiComuneAlComprensorio() {
        view();
        boolean risposta;
        int codiceComprensorio = InputDati.leggiIntero(INSERISCI_IL_CODICE_DEL_COMPRENSORIO_AL_QUALE_SI_VUOLE_AGGIUNGERE_IL_COMUNE);
        do {
            if (controllaEsistenzaComprensorio(codiceComprensorio)) {
                ComprensorioGeografico comprensorio = getComprensorio(codiceComprensorio);
                if (comprensorio != null) {
                    try {
                        comprensorio.aggiungiComuneNuovo(InputDati.leggiStringa(INSERISCI_IL_NOME_DEL_COMUNE));
                    } catch (IOException e) {
                        System.out.println("Errore durante l'aggiunta del comune: " + e.getMessage());
                    }
                    salva();
                }
                risposta = InputDati.yesOrNo(VUOI_AGGIUNGERE_UN_ALTRO_COMUNE);
            } else {
                System.out.println(CODICE_DEL_COMPRENSORIO_SBAGLIATO);
                risposta = false;
            }
        } while (risposta);
    }

    /**
     * Crea un comprensorio geografico.
     *
     * @return il comprensorio geografico creato
     */
    public ComprensorioGeografico creaComprensorioGeografico() {
        List<String> listaComuni = new LinkedList<>();
        GestoreComuni gestoreComuni = new GestoreComuni(gestoreDati);

        gestoreComuni.view();

        gestoreComuni.inserimentoComuni(listaComuni, MIN_NUMERO_COMUNI_COMPRENSORIO);

        Collections.sort(listaComuni);
        return new ComprensorioGeografico(listaComuni);
    }

    @Override
    public void view() {
        for (ComprensorioGeografico comprensorio : mappaComprensori.values()) {
            System.out.println(comprensorio.toString());
        }
    }

    @Override
    public void rimuovi() {
        view();
        int codiceComprensorio = InputDati.leggiIntero(INSERISCI_IL_CODICE_DEL_COMPRENSORIO_DA_RIMUOVERE);

        if (controllaEsistenzaComprensorio(codiceComprensorio)) {
            mappaComprensori.remove(codiceComprensorio);
            salva();
            System.out.println(COMPRENSORIO_RIMOSSO_CON_SUCCESSO);
        } else {
            System.out.println(CODICE_DEL_COMPRENSORIO_NON_TROVATO);
        }
    }

    @Override
    public void aggiungi() {
        ComprensorioGeografico comprensorioNuovo = creaComprensorioGeografico();
        if (controlloComprensorioDuplicato(comprensorioNuovo) != null) {
            System.out.println(COMPRENSORIO_GIA_ESISTENTE);
        } else {
            mappaComprensori.put(comprensorioNuovo.getCodice(), comprensorioNuovo);
            salva();
            System.out.println(COMPRENSORIO_AGGIUNTO);
        }
    }

    @Override
    public void salva() {
        gestoreDati.setComprensori(mappaComprensori);
    }
}