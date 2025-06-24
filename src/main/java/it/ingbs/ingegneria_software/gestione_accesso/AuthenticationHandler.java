package it.ingbs.ingegneria_software.gestione_accesso;

import it.ingbs.ingegneria_software.utilita_generale.InputDati;

public class AuthenticationHandler {

    private static final String INSERISCI_PASSWORD = "Inserisci password: ";
    private static final String INSERIRE_NOME_UTENTE = "Inserire nome utente: ";


    /**
     * Metodo generico per il login di un utente.
     * @param gestoreAccesso Funzione che gestisce la logica specifica del login.
     * @param <T> Tipo dell'utente restituito (Configuratore o Fruitore).
     * @return Utente valido se le credenziali sono corrette.
     */
    public <T> T login(LoginStrategy<T> gestoreAccesso) {
        T result;
        do {
            String nomeUtente = InputDati.leggiStringaNonVuota(INSERIRE_NOME_UTENTE);
            String password = InputDati.leggiStringaNonVuota(INSERISCI_PASSWORD);
            result = gestoreAccesso.accesso(nomeUtente, password);
        } while (result == null);
        return result;
    }
}
