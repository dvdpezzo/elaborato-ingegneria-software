package it.ingbs.ingegneria_software.Eccezioni;

public class ReadException  extends RuntimeException {

    public ReadException() {
        super("Errore durante la lettura del file");
    }

    @Override
    public String toString() {
        return getMessage();
    }


    
}
