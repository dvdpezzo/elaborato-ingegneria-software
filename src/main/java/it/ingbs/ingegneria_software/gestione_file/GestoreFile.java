package it.ingbs.ingegneria_software.gestione_file;

import java.io.IOException;
import java.util.HashMap;

import it.ingbs.ingegneria_software.model.utenti.Fruitore;

public class GestoreFile {

    private static final String FILE_CREDENZIALI_CONFIGURATORI = "src\\File_di_accesso\\credenzialiConfiguratori.txt";
    private static final String FILE_CREDENZIALI_FRUITORI = "src\\File_di_accesso\\credenzialiFruitori.txt";
    private static final String DATI_FRUITORI = "src\\File_di_accesso\\datiFruitori.txt";
    private static final String FILE_COMPRENSORI = "src\\Data_File\\elencoComprensori.txt";
    private static final String FILE_COMUNI = "src\\Data_File\\elencoComuni.txt";
    private static final String FILE_GERARCHIE = "src\\Data_File\\elencoGerarchie.txt";
    private static final String FILE_RICHIESTE = "src\\Data_File\\elencoRichieste.txt";
    private static final String FILE_FATTORI = "src\\Data_File\\elencoFattoriConversione.txt";

    private final GestoreFileComuni gestoreFileComuni = new GestoreFileComuni(FILE_COMUNI);
    private final GestoreFileComprensori gestoreFileComprensori = new GestoreFileComprensori(FILE_COMPRENSORI);
    private final GestoreFileCredenziali gestoreFileCredConfiguratori = new GestoreFileCredenziali(FILE_CREDENZIALI_CONFIGURATORI);
    private final GestoreFileCredenziali gestoreFileCredFruitori = new GestoreFileCredenziali(FILE_CREDENZIALI_FRUITORI);
    private final GestoreFileDatiFruitori gestoreFileDatiFruitori = new GestoreFileDatiFruitori(DATI_FRUITORI);
    private final GestoreFileGerarchie gestoreFileGerarchie = new GestoreFileGerarchie(FILE_GERARCHIE);
    private final GestoreFileRichieste gestoreFileRichieste = new GestoreFileRichieste(FILE_RICHIESTE);
    private final GestoreFileFattori gestoreFileFattori = new GestoreFileFattori(FILE_FATTORI);

    private final GestoreDati gestoreDati = new GestoreDati();

    /**
     * Carica i dati salvati su file.
     * @throws IOException se si verifica un errore durante la lettura dei file.
     */
    public void caricaSalvataggio() throws IOException {
        gestoreDati.setComuni(gestoreFileComuni.leggiFile());
        gestoreDati.setComprensori(gestoreFileComprensori.leggiFile());
        gestoreDati.setGerarchie(gestoreFileGerarchie.recuperaAlbero());
        gestoreDati.setCredenzialiConfiguratori(gestoreFileCredConfiguratori.leggiFile());
        gestoreDati.setCredenzialiFruitori(gestoreFileCredFruitori.leggiFile());
        gestoreDati.setDatiFruitori(gestoreFileDatiFruitori.leggiFile());
        gestoreDati.setFattori(gestoreFileFattori.leggiFile());
        gestoreDati.setRichieste(gestoreFileRichieste.leggiFile());
    }

    /**
     * Restituisce il gestore dei dati.
     * @return il gestore dei dati.
     */
    public GestoreDati getGestoreDati() {
        return gestoreDati;
    }

    /**
     * Crea un salvataggio dei dati su file.
     */
    public void creaSalvataggio(){
        try {
            gestoreFileComuni.salvaSuFile(gestoreDati.getComuni());
            gestoreFileComprensori.salvaSuFile(gestoreDati.getComprensori());
            gestoreFileGerarchie.salvaAlbero(gestoreDati.getGerarchie().values());
            gestoreFileCredConfiguratori.salvaSuFile(gestoreDati.getCredenzialiConfiguratori());
            gestoreFileCredFruitori.salvaSuFile(gestoreDati.getCredenzialiFruitori());      
            gestoreFileDatiFruitori.salvaSuFile(gestoreDati.getDatiFruitori());      
            gestoreFileFattori.salvaSuFile(gestoreDati.getFattori());
            gestoreFileRichieste.salvaSuFile(gestoreDati.getRichieste());
        } catch (IOException ex) {
            System.err.println("Errore durante il salvataggio dei dati: " + ex.getMessage());
        }
    }

    /**
     * Salva i dati dei comuni su file.
     */
    public void salvaComuni() {
        try {
            gestoreFileComuni.salvaSuFile(gestoreDati.getComuni());
        } catch (IOException ex) {
            System.err.println("Errore durante il salvataggio dei comuni: " + ex.getMessage());
        }
    }

    /**
     * Salva i dati dei comprensori su file.
     */
    public void salvaComprensori() {
        try {
            gestoreFileComprensori.salvaSuFile(gestoreDati.getComprensori());
        } catch (IOException ex) {
            System.err.println("Errore durante il salvataggio dei comprensori: " + ex.getMessage());
        }
    }

    /**
     * Salva i dati delle gerarchie su file.
     */
    public void salvaGerarchie() {
        gestoreFileGerarchie.salvaAlbero(gestoreDati.getGerarchie().values());
    }

    /**
     * Salva le credenziali dei configuratori su file.
     */
    public void salvaCredenzialiConfiguratori() {
        try {
            gestoreFileCredConfiguratori.salvaSuFile(gestoreDati.getCredenzialiConfiguratori());
        } catch (IOException ex) {
            System.err.println("Errore durante il salvataggio delle credenziali dei configuratori: " + ex.getMessage());
        }
    }

    /**
     * Salva le credenziali dei fruitori su file.
     */
    public void salvaCredenzialiFruitori() {
        try {
            gestoreFileCredFruitori.salvaSuFile(gestoreDati.getCredenzialiFruitori());
        } catch (IOException ex) {
            System.err.println("Errore durante il salvataggio delle credenziali dei fruitori: " + ex.getMessage());
        }
    }

    /**
     * Salva i fattori di conversione su file.
     */
    public void salvaFattori() {
        try {
            gestoreFileFattori.salvaSuFile(gestoreDati.getFattori());
        } catch (IOException ex) {
            System.err.println("Errore durante il salvataggio dei fattori di conversione: " + ex.getMessage());
        }
    }

    /**
     * Salva le richieste su file.
     */
    public void salvaRichieste() {
        gestoreFileRichieste.salvaSuFile(gestoreDati.getRichieste());
    }
    
    /**
     * Salva i dati dei fruitori su file.
     */
    public void salvaDatiFruitori() {
        gestoreFileDatiFruitori.salvaSuFile(gestoreDati.getDatiFruitori());
    }

    /**
     * Carica i dati dei fruitori da file.
     * @return una mappa contenente i dati dei fruitori.
     */
    public HashMap<String, Fruitore> caricaDatiFruitori() {
        return gestoreFileDatiFruitori.leggiFile();
    }
}
