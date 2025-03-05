package it.ingbs.ingegneria_software.Eccezioni;

public class PadreNotFoundException extends Exception{

    public PadreNotFoundException(){
        super("Categoria padre Non Presente!");
    }

    @Override
    public String toString() {
        return getMessage();
    }
}