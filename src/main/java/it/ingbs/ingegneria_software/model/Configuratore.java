package it.ingbs.ingegneria_software.model;

import java.util.List;

public class Configuratore {
    private static final String DEFAULT = "admin";

    private String userName;
    private String password;   

    public Configuratore() {
        this.userName = DEFAULT;
        this.password = DEFAULT;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ComprensorioGeografico creaComprensorioGeografico(){
        return null;
    }

    public List<Categoria> creaGerarchia(){
        return null;
    }

    public Categoria creaCategoria(){
        return null;
    }

    public FattoreConversione creaFattoreConversione(){
        return null;
    }

    public void salvaCambiamenti(){}

    public void visualizzaComprensori(){}
    public void visualizzaGerarchia(){}
    public void visualizzaFattoriConversione(){}

}
