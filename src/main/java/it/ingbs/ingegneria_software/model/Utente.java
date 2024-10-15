package it.ingbs.ingegneria_software.model;

public class Utente {
    private String nomeUtente ;
    private String password;   

    public Utente(String nomeUtente, String password) {
        this.nomeUtente = nomeUtente;
        this.password = password;        
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String userName) {
        this.nomeUtente = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
