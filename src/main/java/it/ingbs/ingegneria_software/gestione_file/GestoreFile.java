package it.ingbs.ingegneria_software.gestione_file;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.logging.Level;

import it.ingbs.ingegneria_software.model.utenti.Fruitore;

public class GestoreFile {
    private static final Logger LOGGER = Logger.getLogger(GestoreFile.class.getName());

    private static final String FILE_CREDENZIALI_CONFIGURATORI = "src\\File_di_accesso\\credenzialiConfiguratori.txt";
    private static final String FILE_CREDENZIALI_FRUITORI = "src\\File_di_accesso\\credenzialiFruitori.txt";
    private static final String DATI_FRUITORI = "src\\File_di_accesso\\datiFruitori.txt";
    private static final String FILE_COMPRENSORI = "src\\Data_File\\elencoComprensori.txt";
    private static final String FILE_COMUNI = "src\\Data_File\\elencoComuni.txt";
    private static final String FILE_GERARCHIE = "src\\Data_File\\elencoGerarchie.txt";
    private static final String FILE_RICHIESTE = "src\\Data_File\\elencoRichieste.txt";
    private static final String FILE_FATTORI = "src\\Data_File\\elencoFattoriConversione.txt";

    private final GestoreFileComuni gestoreFileComuni;
    private final GestoreFileComprensori gestoreFileComprensori;
    private final GestoreFileCredenziali gestoreFileCredConfiguratori;
    private final GestoreFileCredenziali gestoreFileCredFruitori;
    private final GestoreFileDatiFruitori gestoreFileDatiFruitori;
    private final GestoreFileGerarchie gestoreFileGerarchie;
    private final GestoreFileRichieste gestoreFileRichieste;
    private final GestoreFileFattori gestoreFileFattori;
    private final GestoreDati gestoreDati;

    public GestoreFile() {
        this.gestoreFileComuni = new GestoreFileComuni(FILE_COMUNI);
        this.gestoreFileComprensori = new GestoreFileComprensori(FILE_COMPRENSORI);
        this.gestoreFileCredConfiguratori = new GestoreFileCredenziali(FILE_CREDENZIALI_CONFIGURATORI);
        this.gestoreFileCredFruitori = new GestoreFileCredenziali(FILE_CREDENZIALI_FRUITORI);
        this.gestoreFileDatiFruitori = new GestoreFileDatiFruitori(DATI_FRUITORI);
        this.gestoreFileGerarchie = new GestoreFileGerarchie(FILE_GERARCHIE);
        this.gestoreFileRichieste = new GestoreFileRichieste(FILE_RICHIESTE);
        this.gestoreFileFattori = new GestoreFileFattori(FILE_FATTORI);
        this.gestoreDati = GestoreDati.getInstance();
    }

    private boolean isFileEmpty(File file) {
        return file.length() == 0;
    }

    private <T> void leggiDati(File file, String messaggioErrore, 
            java.util.function.Consumer<T> setter, java.util.function.Supplier<T> reader) {
        try {
            if (!isFileEmpty(file)) {
                setter.accept(reader.get());
            } else {
                LOGGER.warning(messaggioErrore);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante la lettura del file: " + file.getName(), e);
        }
    }

    /**
     * Carica i dati salvati su file.
     * @throws IOException se si verifica un errore durante la lettura dei file.
     */
    public void caricaSalvataggio() {
        try {
            leggiDati(new File(FILE_COMUNI), "Il file dei comuni è vuoto.", 
                gestoreDati::setComuni, gestoreFileComuni::leggiFile);
            leggiDati(new File(FILE_COMPRENSORI), "Il file dei comprensori è vuoto.", 
                gestoreDati::setComprensori, gestoreFileComprensori::leggiFile);
            leggiDati(new File(FILE_GERARCHIE), "Il file delle gerarchie è vuoto.", 
                gestoreDati::setGerarchie, gestoreFileGerarchie::recuperaAlbero);
            leggiDati(new File(FILE_CREDENZIALI_CONFIGURATORI), "Il file delle credenziali dei configuratori è vuoto.", 
                gestoreDati::setCredenzialiConfiguratori, gestoreFileCredConfiguratori::leggiFile);
            leggiDati(new File(FILE_CREDENZIALI_FRUITORI), "Il file delle credenziali dei fruitori è vuoto.", 
                gestoreDati::setCredenzialiFruitori, gestoreFileCredFruitori::leggiFile);
            leggiDati(new File(DATI_FRUITORI), "Il file dei dati dei fruitori è vuoto.", 
                gestoreDati::setDatiFruitori, gestoreFileDatiFruitori::leggiFile);
            leggiDati(new File(FILE_FATTORI), "Il file dei fattori di conversione è vuoto.", 
                gestoreDati::setFattoriFile, gestoreFileFattori::leggiFile);
            leggiDati(new File(FILE_RICHIESTE), "Il file delle richieste è vuoto.", 
                gestoreDati::setRichiesteFile, gestoreFileRichieste::leggiFile);
            
            LOGGER.info("Caricamento dati completato con successo");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante il caricamento dei dati", e);
        }
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
            LOGGER.info("Salvataggio dati completato con successo");
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Errore durante il salvataggio dei dati", ex);
        }
    }

    /**
     * Salva i dati dei comuni su file.
     */
    public void salvaComuni() {
        try {
            gestoreFileComuni.salvaSuFile(gestoreDati.getComuni());
            LOGGER.info("Comuni salvati con successo");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il salvataggio dei comuni", e);
        }
    }

    /**
     * Salva i dati dei comprensori su file.
     */
    public void salvaComprensori() {
        try {
            gestoreFileComprensori.salvaSuFile(gestoreDati.getComprensori());
            LOGGER.info("Comprensori salvati con successo");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il salvataggio dei comprensori", e);
        }
    }

    /**
     * Salva i dati delle gerarchie su file.
     */
    public void salvaGerarchie() {
        gestoreFileGerarchie.salvaAlbero(gestoreDati.getGerarchie().values());
        LOGGER.info("Gerarchie salvate con successo");
    }

    /**
     * Salva le credenziali dei configuratori su file.
     */
    public void salvaCredenzialiConfiguratori() {
        try {
            gestoreFileCredConfiguratori.salvaSuFile(gestoreDati.getCredenzialiConfiguratori());
            LOGGER.info("Credenziali configuratori salvate con successo");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il salvataggio delle credenziali dei configuratori", e);
        }
    }

    /**
     * Salva le credenziali dei fruitori su file.
     */
    public void salvaCredenzialiFruitori() {
        try {
            gestoreFileCredFruitori.salvaSuFile(gestoreDati.getCredenzialiFruitori());
            LOGGER.info("Credenziali fruitori salvate con successo");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il salvataggio delle credenziali dei fruitori", e);
        }
    }

    /**
     * Salva i fattori di conversione su file.
     */
    public void salvaFattori() {
        try {
            gestoreFileFattori.salvaSuFile(gestoreDati.getFattori());
            LOGGER.info("Fattori di conversione salvati con successo");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il salvataggio dei fattori di conversione", e);
        }
    }

    /**
     * Salva le richieste su file.
     */
    public void salvaRichieste() {
        gestoreFileRichieste.salvaSuFile(gestoreDati.getRichieste());
        LOGGER.info("Richieste salvate con successo");
    }
    
    /**
     * Salva i dati dei fruitori su file.
     */
    public void salvaDatiFruitori() {
        try {
            gestoreFileDatiFruitori.salvaSuFile(gestoreDati.getDatiFruitori());
            LOGGER.info("Dati fruitori salvati con successo");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il salvataggio dei dati dei fruitori", e);
        }
    }

    /**
     * Carica i dati dei fruitori da file.
     * @return una mappa contenente i dati dei fruitori.
     */
    public HashMap<String, Fruitore> caricaDatiFruitori() {
        File file = new File(DATI_FRUITORI);
        if (isFileEmpty(file)) {
            LOGGER.warning("Il file dei dati dei fruitori è vuoto.");
            return new HashMap<>();
        }
        try {
            return gestoreFileDatiFruitori.leggiFile();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante il caricamento dei dati dei fruitori", e);
            return new HashMap<>();
        }
    }
}
