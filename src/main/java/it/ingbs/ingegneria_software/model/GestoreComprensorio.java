package it.ingbs.ingegneria_software.model;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import it.ingbs.ingegneria_software.gestione_file.GestoreFileComprensori;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

// Classe che gestisce l'elenco dei comprensori inseriti dal configuratore
public class GestoreComprensorio {

    private static final String INSERISCI_IL_CODICE_DEL_COMPRENSORIO_DA_RIMUOVERE = "Inserisci il codice del comprensorio da rimuovere:";
    private static final String COMPRENSORIO_AGGIUNTO = "Comprensorio aggiunto.";
    private static final String COMPRENSORIO_GIA_ESISTENTE = "Comprensorio già esistente.";
    private static final String CODICE_DEL_COMPRENSORIO_SBAGLIATO = "Codice del comprensorio sbagliato!";
    private static final String COMPRENSORIO_RIMOSSO_CON_SUCCESSO = "Comprensorio rimosso con successo.";
    private static final String CODICE_DEL_COMPRENSORIO_NON_TROVATO = "Codice del comprensorio non trovato.";
    private static final String INSERISCI_IL_CODICE_DEL_COMPRENSORIO_AL_QUALE_SI_VUOLE_AGGIUNGERE_IL_COMUNE = "Inserisci il codice del comprensorio al quale si vuole aggiungere il comune:";
    private static final String INSERISCI_IL_NOME_DEL_COMUNE = "Inserisci il nome del comune:";
    private static final String VUOI_AGGIUNGERE_UN_ALTRO_COMUNE = "Vuoi aggiungere un altro comune?";
    private static final String ELENCO_COMPRENSORI_TXT = "src\\Data_File\\elencoComprensori.txt";
    private static final int MIN_NUMERO_COMUNI_COMPRENSORIO = 3;

    private HashMap<Integer, ComprensorioGeografico> mappaComprensori;
    private final File fileComprensori = new File(ELENCO_COMPRENSORI_TXT);
    private final GestoreFileComprensori gestoreFileComprensori;

    /**
     * Costruttore che legge i comprensori dal file e li carica nella mappa.
     */
    public GestoreComprensorio() {
        this.mappaComprensori = new HashMap<>();
        this.gestoreFileComprensori = new GestoreFileComprensori(this.mappaComprensori);
        configuraMappaComprensoriDaFile();
    }

    /**
     * Restituisce la mappa dei comprensori.
     *
     * @return mappa dei comprensori
     */
    public HashMap<Integer, ComprensorioGeografico> getMappaComprensori() {
        return mappaComprensori;
    }

    /**
     * Aggiunge un comprensorio alla mappa.
     */
    public void aggiungiComprensorio() {
        ComprensorioGeografico comprensorioNuovo = creaComprensorioGeografico();
        if (controlloComprensorioDuplicato(comprensorioNuovo) != null) {
            System.out.println(COMPRENSORIO_GIA_ESISTENTE);
        } else {
            mappaComprensori.put(comprensorioNuovo.getCodice(), comprensorioNuovo);
            salvaMappaComprensoriSuFile();
            System.out.println(COMPRENSORIO_AGGIUNTO);
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
     * Salva la mappa dei comprensori su file.
     */
    private void salvaMappaComprensoriSuFile() {
        try {
            gestoreFileComprensori.salvaSuFile(fileComprensori);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Legge la mappa dei comprensori da file.
     */
    private void configuraMappaComprensoriDaFile() {
        try {
            mappaComprensori = gestoreFileComprensori.leggiFile(fileComprensori);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Visualizza tutti i comprensori esistenti.
     */
    public void visualizzaComprensori() {
        for (ComprensorioGeografico comprensorio : mappaComprensori.values()) {
            System.out.println(comprensorio.toString());
        }
    }

    /**
     * Controlla se il comprensorio esiste in base al codice.
     *
     * @param codiceComprensorio il codice del comprensorio da controllare
     * @return true se il comprensorio esiste, altrimenti false
     */
    public boolean controllaEsistenzaComprensorio(int codiceComprensorio) {
        return getComprensorio(codiceComprensorio) != null;
    }

    /**
     * Rimuove un comprensorio dalla mappa.
     */
    private void rimuoviComprensorio() {
        visualizzaComprensori();
        int codiceComprensorio = InputDati.leggiIntero(INSERISCI_IL_CODICE_DEL_COMPRENSORIO_DA_RIMUOVERE);

        if (controllaEsistenzaComprensorio(codiceComprensorio)) {
            mappaComprensori.remove(codiceComprensorio);
            salvaMappaComprensoriSuFile();
            System.out.println(COMPRENSORIO_RIMOSSO_CON_SUCCESSO);
        } else {
            System.out.println(CODICE_DEL_COMPRENSORIO_NON_TROVATO);
        }
    }

    /**
     * Aggiunge un comune a un comprensorio già esistente.
     *
     * @throws IOException se si verifica un errore di I/O
     */
    private void aggiungiComuneAlComprensorio() throws IOException {
        visualizzaComprensori();
        boolean risposta;
        int codiceComprensorio = InputDati.leggiIntero(INSERISCI_IL_CODICE_DEL_COMPRENSORIO_AL_QUALE_SI_VUOLE_AGGIUNGERE_IL_COMUNE);
        do {
            if (controllaEsistenzaComprensorio(codiceComprensorio)) {
                ComprensorioGeografico comprensorio = getComprensorio(codiceComprensorio);
                if (comprensorio != null) {
                    comprensorio.aggiungiComuneNuovo(InputDati.leggiStringa(INSERISCI_IL_NOME_DEL_COMUNE));
                    salvaMappaComprensoriSuFile();
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
        GestoreComuni gc = new GestoreComuni();

        gc.stampaComuni();

        gc.inserimentoComuni(listaComuni, MIN_NUMERO_COMUNI_COMPRENSORIO);

        Collections.sort(listaComuni);
        return new ComprensorioGeografico(listaComuni);
    }

    /**
     * Menu per la scelta delle operazioni da eseguire sui comprensori.
     *
     * @throws IOException se si verifica un errore di I/O
     */
    public void modificaComprensori() throws IOException {
        String[] voci = { "Aggiungi comune al comprensorio: ", "Aggiungi Comprensorio: ", "Rimuovi Comprensorio: ", "Salva Cambiamenti: " };
        MenuUtil menuComprensorio = new MenuUtil("AZIONI SUI COMPRENSORI", voci);
        int scelta = 0;
        do {
            scelta = menuComprensorio.scegli();
            switch (scelta) {
                case 1:
                    aggiungiComuneAlComprensorio();
                    break;
                case 2:
                    aggiungiComprensorio();
                    break;
                case 3:
                    rimuoviComprensorio();
                    break;
                case 4:
                    salvaMappaComprensoriSuFile();
                    break;
            }
        } while (scelta != 0);
    }
}












