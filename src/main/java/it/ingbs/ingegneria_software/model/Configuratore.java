package it.ingbs.ingegneria_software.model;

import java.util.List;

public class Configuratore extends Utente  {
    private static final String DEFAULT = "admin";

    

    public Configuratore(String nomeUtente, String passwordUtente) {
        super(nomeUtente, passwordUtente);
    }


    public ComprensorioGeografico creaComprensorioGeografico(){
        return null;
    }    

    public void salvaCambiamenti(){}

    public void visualizzaComprensori(){}
    public void visualizzaGerarchia(){}
    public void visualizzaFattoriConversione(){}

    List<Categoria> creaGerarchia() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    Categoria creaCategoria() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
