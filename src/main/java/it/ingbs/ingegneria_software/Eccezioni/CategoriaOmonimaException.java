package it.ingbs.ingegneria_software.Eccezioni;

public class CategoriaOmonimaException extends Exception{
    public CategoriaOmonimaException(){
        super("Categoria già presente:");
    }

    @Override
    public String toString() {
        return getMessage() + " impossibile da aggiungere";
    }
}