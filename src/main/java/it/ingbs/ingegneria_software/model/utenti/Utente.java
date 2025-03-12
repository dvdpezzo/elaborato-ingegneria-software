package it.ingbs.ingegneria_software.model.utenti;

public class Utente {
    private String nomeUtente;
    private String password;

    /**
     * Costruttore della classe Utente.
     * 
     * @param nomeUtente il nome utente
     * @param password la password dell'utente
     */
    public Utente(String nomeUtente, String password) {
        this.nomeUtente = nomeUtente;
        this.password = password;
    }

    /**
     * Restituisce il nome utente.
     * 
     * @return il nome utente
     */
    public String getNomeUtente() {
        return nomeUtente;
    }

    /**
     * Imposta il nome utente.
     * 
     * @param nomeUtente il nuovo nome utente
     */
    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    /**
     * Restituisce la password.
     * 
     * @return la password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password.
     * 
     * @param password la nuova password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
