package it.ingbs.ingegneria_software.gestione_accesso;

public interface GestoreAccessoUtente<T> {
    
    /**
     * Metodo per l'accesso di un utente.
     * @param nomeUtente Nome utente dell'utente.
     * @param password Password dell'utente.
     * @return Utente se le credenziali sono corrette, null altrimenti.
     */
    T accesso(String nomeUtente, String password);

    /**
     * Metodo per la registrazione di un nuovo utente.
     * @return Nuovo utente registrato.
     */
    T registrazioneNuovoUtente();

}
