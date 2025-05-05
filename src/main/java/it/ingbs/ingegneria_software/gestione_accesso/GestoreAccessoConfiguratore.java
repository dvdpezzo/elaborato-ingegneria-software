package it.ingbs.ingegneria_software.gestione_accesso;

import it.ingbs.ingegneria_software.model.utenti.Configuratore;
import it.ingbs.ingegneria_software.model.utenti.GestoreConfiguratori;

public class GestoreAccessoConfiguratore implements GestoreAccessoUtente<Configuratore> {
    private final GestoreConfiguratori gestoreConfiguratori;

    public GestoreAccessoConfiguratore(GestoreConfiguratori gestoreConfiguratori) {
        this.gestoreConfiguratori = gestoreConfiguratori;
    }

    /**
     * Permette l'accesso al configuratore.
     * @param nomeUtente Nome utente del configuratore.
     * @param password Password del configuratore.
     * @return Il configuratore se le credenziali sono corrette, null altrimenti.
     */
    @Override
    public Configuratore accesso(String nomeUtente, String password) {
        if(nomeUtente.equals("admin") && password.equals("admin")) {
            return registrazioneNuovoUtente();
        }else if (controlloEsistenzaConfiguratore(nomeUtente, password)) {
            System.out.println("Accesso effettuato correttamente!");
            return gestoreConfiguratori.trovaConfiguratore(nomeUtente);
        } else {
            System.out.println("Errore! Nome utente o password errati!");
            return null;
        }
    }

    /**
     * Permette la registrazione di un nuovo configuratore.
     */
    @Override
    public Configuratore registrazioneNuovoUtente() {
        System.out.println("Sei stato reindirizzato alla creazione del tuo Nome utente e Password personali:");
        return gestoreConfiguratori.creaUtenteConfiguratore(); 
    }

    /**
     * Controlla se il configuratore esiste e se la password è corretta.
     * @param nomeUtente Nome utente del configuratore.
     * @param pass Password del configuratore.
     * @return true se il configuratore esiste e la password è corretta, false altrimenti.
     */
    private boolean controlloEsistenzaConfiguratore (String nomeUtente, String pass){
        return gestoreConfiguratori.getMappaConfiguratori().containsKey(nomeUtente) && 
                gestoreConfiguratori.getMappaConfiguratori().get(nomeUtente).getPassword().equals(pass);
    } 
}
