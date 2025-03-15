package it.ingbs.ingegneria_software.gestione_file;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import it.ingbs.ingegneria_software.model.gerarchie.Gerarchia;
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

    public GestoreDati getGestoreDati() {
        return gestoreDati;
    }

    public void creaSalvataggio(){
        try {
            gestoreFileComuni.salvaSuFile(gestoreDati.getComuni());
            gestoreFileComprensori.salvaSuFile(gestoreDati.getComprensori());
            gestoreFileGerarchie.salvaAlbero((Collection<Gerarchia>) gestoreDati.getGerarchie());
            gestoreFileCredConfiguratori.salvaSuFile(gestoreDati.getCredenzialiConfiguratori());
            gestoreFileCredFruitori.salvaSuFile(gestoreDati.getCredenzialiFruitori());      
            gestoreFileDatiFruitori.salvaSuFile(gestoreDati.getDatiFruitori());      
            gestoreFileFattori.salvaSuFile(gestoreDati.getFattori());
            gestoreFileRichieste.salvaSuFile(gestoreDati.getRichieste());
        } catch (IOException ex) {
        }
    }

    public void salvaComuni() {
        try {
            gestoreFileComuni.salvaSuFile(gestoreDati.getComuni());
        } catch (IOException ex) {
            // Gestisci l'eccezione
        }
    }

    public void salvaComprensori() {
        try {
            gestoreFileComprensori.salvaSuFile(gestoreDati.getComprensori());
        } catch (IOException ex) {
            // Gestisci l'eccezione
        }
    }

    public void salvaGerarchie() {
        gestoreFileGerarchie.salvaAlbero(gestoreDati.getGerarchie().values());
    }

    public void salvaCredenzialiConfiguratori() {
        try {
            gestoreFileCredConfiguratori.salvaSuFile(gestoreDati.getCredenzialiConfiguratori());
        } catch (IOException ex) {
            // Gestisci l'eccezione
        }
    }

    public void salvaCredenzialiFruitori() {
        try {
            gestoreFileCredFruitori.salvaSuFile(gestoreDati.getCredenzialiFruitori());
        } catch (IOException ex) {
            // Gestisci l'eccezione
        }
    }

    public void salvaFattori() {
        try {
            gestoreFileFattori.salvaSuFile(gestoreDati.getFattori());
        } catch (IOException ex) {
            // Gestisci l'eccezione
        }
    }

    public void salvaRichieste() {
        gestoreFileRichieste.salvaSuFile(gestoreDati.getRichieste());
    }
    
    public void salvaDatiFruitori() {
        gestoreFileDatiFruitori.salvaSuFile(gestoreDati.getDatiFruitori());
    }

    public HashMap<String, Fruitore> caricaDatiFruitori() {
        return gestoreFileDatiFruitori.leggiFile();
    }
}
