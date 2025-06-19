package it.ingbs.ingegneria_software.Eccezioni;

public class LoadException extends Exception {

    public LoadException() {
        super("Errore durante il caricamento del file");
    }

    @Override
    public String toString() {
        return getMessage();
    }
    
}
