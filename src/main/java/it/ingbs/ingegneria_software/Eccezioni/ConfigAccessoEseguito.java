package it.ingbs.ingegneria_software.Eccezioni;


public class ConfigAccessoEseguito extends Throwable{

    public ConfigAccessoEseguito(){
        super("Accesso Eseguito");
    }

    @Override
    public String toString(){
        return getMessage();
    }
}