package it.ingbs.ingegneria_software.Eccezioni;

public class CampoNotFoundException extends Exception{
    public CampoNotFoundException(){
        super("Campo inesistente");
    }

    @Override
    public String toString() {
        return getMessage();
    }
}