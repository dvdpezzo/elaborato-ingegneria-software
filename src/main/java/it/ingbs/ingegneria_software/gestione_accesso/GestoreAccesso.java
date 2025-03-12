package it.ingbs.ingegneria_software.gestione_accesso;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import it.ingbs.ingegneria_software.gestione_file.GestoreFileCredenziali;
import it.ingbs.ingegneria_software.model.utenti.Configuratore;
import it.ingbs.ingegneria_software.model.utenti.GestoreConfiguratori;

/**
 * Classe che si occupa della gestione della mappa delle credenziali.
 */
public class GestoreAccesso {

    private static final String FILE_ASSENTE = "File assente";
    private static final String MESSAGGIO_CREAZIONE_CREDENZIALI = "Sei stato reindirizzato alla creazione del tuo Nome utente e Password personali:";
    private static final String ERRORE_CREDENZIALI_ERRATE = "ERRORE! Nome Utente o password errati!";
    private static final String MESSAGGIO_ACCESSO_CORRETTO = "Accesso effettuato correttamente!";
    private static final String MESSAGGIO_FILE_NON_TROVATO = "File non trovato: %s";
    private static final String PERCORSO_FILE_CREDENZIALI = "src\\File_di_accesso\\credenzialiConfiguratori.txt";
    private static final String UTENTE_DEFAULT = "admin";
    private static final String PASS_DEFAULT = "admin";

    private File fileConfiguratori = new File(PERCORSO_FILE_CREDENZIALI);
    private HashMap<String, String> mappaCredenzialiUtenti = new HashMap<>();
    private GestoreFileCredenziali gestoreFile;
    private final GestoreConfiguratori gestoreConfiguratori;

    /**
     * Costruttore della classe GestoreAccesso.
     * Inizializza il gestore delle credenziali e legge le credenziali dal file.
     */
    public GestoreAccesso() {
        this.gestoreFile = new GestoreFileCredenziali(mappaCredenzialiUtenti);
        try {
            mappaCredenzialiUtenti = gestoreFile.leggiFile(fileConfiguratori);
        } catch (IOException e) {
            System.out.println(String.format(MESSAGGIO_FILE_NON_TROVATO, e.getMessage()));
        }
        this.gestoreConfiguratori = new GestoreConfiguratori(gestoreFile);
    }

    /**
     * Permette l'accesso al configuratore: controlla prima di tutto che non sia il primo accesso,
     * altrimenti controlla le credenziali e se sono valide, permette l'accesso.
     *
     * @param nomeUtente il nome utente inserito
     * @param passwordUtente la password inserita
     * @return nuovo configuratore se è il primo accesso, configuratore esistente se l'accesso è andato a buon fine, null se le credenziali sono errate
     */
    public Configuratore accessoConfiguratore(String nomeUtente, String passwordUtente) {
        if (nomeUtente.equals(UTENTE_DEFAULT) && passwordUtente.equals(PASS_DEFAULT)) {
            return registrazioneNuovoConfiguratore();
        } else {
            if (controlloEsistenzaCredenziali(nomeUtente, passwordUtente)) {
                Configuratore configuratoreEsistente = gestoreConfiguratori.trovaConfiguratore(nomeUtente);
                System.out.println(MESSAGGIO_ACCESSO_CORRETTO);
                return configuratoreEsistente;
            } else {
                System.out.println(ERRORE_CREDENZIALI_ERRATE);
                return null;
            }
        }
    }

    /**
     * Effettua la registrazione di un nuovo configuratore: inserisce le credenziali nella mappa e salva sul file.
     *
     * @return nuovo configuratore
     */
    private Configuratore registrazioneNuovoConfiguratore() {
        System.out.println(MESSAGGIO_CREAZIONE_CREDENZIALI);
        Configuratore nuovoConfiguratore = gestoreConfiguratori.creaUtenteConfiguratore();
        aggiungiCredenzialiAllaMappa(nuovoConfiguratore.getNomeUtente(), nuovoConfiguratore.getPassword());
        return nuovoConfiguratore;
    }

    /**
     * Aggiunge nome utente e password nella mappa e le salva sul file.
     *
     * @param nomeUtente il nome utente da aggiungere
     * @param password la password da aggiungere
     */
    private void aggiungiCredenzialiAllaMappa(String nomeUtente, String password) {
        mappaCredenzialiUtenti.put(nomeUtente, password);
        try {
            gestoreFile.salvaSuFile(fileConfiguratori);
        } catch (IOException e) {
            System.out.println(FILE_ASSENTE);
        }
    }

    /**
     * Controlla se i dati inseriti dall'utente (nome utente e password) sono corretti.
     *
     * @param nomeUtente il nome utente inserito
     * @param password la password inserita
     * @return true se le credenziali sono corrette, false altrimenti
     */
    private boolean controlloEsistenzaCredenziali(String nomeUtente, String password) {
        return mappaCredenzialiUtenti.containsKey(nomeUtente) && mappaCredenzialiUtenti.get(nomeUtente).equals(password);
    }
}
