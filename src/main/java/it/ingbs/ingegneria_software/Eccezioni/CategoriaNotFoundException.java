package it.ingbs.ingegneria_software.Eccezioni;

public class CategoriaNotFoundException extends Exception{

    public CategoriaNotFoundException(){
        super("Categoria non trovata!");
    }

    @Override
    public String toString() {
        return getMessage();
    }
}