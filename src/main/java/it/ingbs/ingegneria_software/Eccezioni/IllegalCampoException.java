package it.ingbs.ingegneria_software.Eccezioni;

public class IllegalCampoException extends Exception{
    public IllegalCampoException(){
        super("il campo che si vuole aggiungere è già presente ( nativo/ereditato )");
    }

    @Override
    public String toString() {
        return getMessage();
    }

}