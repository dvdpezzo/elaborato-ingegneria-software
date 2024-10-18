package it.ingbs.ingegneria_software.gestione_accesso;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.ingbs.ingegneria_software.model.Configuratore;
import it.ingbs.ingegneria_software.model.GestoreConfiguratori;

/**
 * Classe che si occupa solo della gestione della mappa delle credenziali
 */
public class GestoreAccesso {

    // private static final String FILE_DI_ACCESSO_CREDENZIALI_FRUITORI_TXT = "src\\File di accesso\\credenzialiFruitori.txt";
    private static final String FILE_DI_ACCESSO_CREDENZIALI_CONFIGURATORI_TXT = "src\\File di accesso\\credenzialiConfiguratori.txt";
    private static final String UTENTE_DEFAULT="admin";
    private static final String PASS_DEFAULT="admin";
    
    private static final Logger LOGGER = Logger.getLogger(GestoreAccesso.class.getName());
    private File fileConfiguratori = new File(FILE_DI_ACCESSO_CREDENZIALI_CONFIGURATORI_TXT);
    // private File fileFruitori = new File(FILE_DI_ACCESSO_CREDENZIALI_FRUITORI_TXT);
    private HashMap<String, String> mappaCredenzialiUtenti = new HashMap<>();
    private GestoreFileCredenziali gestoreFile;
    private final GestoreConfiguratori gestoreConfiguratori ;
    

    public GestoreAccesso() {       
        this.gestoreFile = new GestoreFileCredenziali(mappaCredenzialiUtenti);        
        try {
            gestoreFile.configuraMappaCredenzialiDaFile(fileConfiguratori);
        } catch (IOException e) {
            LOGGER.log(Level.INFO, String.format("File non trovato: %s", e.getMessage()));
        }
        this.gestoreConfiguratori = new GestoreConfiguratori(gestoreFile);
    }
    /**
     * permette l'accesso al configuratore: controlla prima di tutto che non sia il primo accesso,
     * altrimenti controlla le credenziali e se sono valide, permette l'accesso
     * @param nomeUtente
     * @param passUtente
     * @return nuovo configuratore se è il primo accesso, existingConfiguratore se l'accesso è andato a buon fine, null se le credenziali sono errate
     */
    public Configuratore accessoConfiguratore (String nomeUtente, String passUtente ){
        if(nomeUtente.equals(UTENTE_DEFAULT) && passUtente.equals(PASS_DEFAULT)){
            return registrazioneNuovoConfiguratore();
        }else
        {
            if(controlloEsistenzaCredenziali(nomeUtente, passUtente)){                
                Configuratore existingConfiguratore = gestoreConfiguratori.trovaConfiguratore(nomeUtente);
                LOGGER.info("Accesso effettuato corretamente!");
                return existingConfiguratore;
            }
            else{
                LOGGER.info("ERRORE! Nome Utente o password errati!");
                return null;
            }
        }

    }
    /**
     * Effettua la registrazione di un nuovo configuratore: inserisce le credenziali nella mappa e salva sul file
     * @return nuovo configuratore
     */
    private Configuratore registrazioneNuovoConfiguratore () {
        LOGGER.info("Sei stasto reindirizzato alla creazione del tuo Nome utente e Password personali:");
        Configuratore newUtente = gestoreConfiguratori.creaUtenteConfiguratore();
        aggiungiCredenzialiAllaMappa(newUtente.getNomeUtente(), newUtente.getPassword());
        return newUtente;
    }
    /**
     * Aggiunge nome utente e password nella mappa e le salva sul file
     * @param nomeUtente 
     * @param pass password
     * @throws IOException 
     */
    private void aggiungiCredenzialiAllaMappa(String nomeUtente, String pass) {
        mappaCredenzialiUtenti.put(nomeUtente, pass);
        try {
            gestoreFile.salvaMappaCredenzialiSuFile(fileConfiguratori);
        } catch (IOException e) {
           LOGGER.log(Level.SEVERE, "File assente");
        }
    }

    /**
     * Controlla se i dati inseriti dall'utente (nome utente e password) sono corretti
     * @param nomeUtente
     * @param pass password
     * @return restituisce vero se li trova, altrimenti falso
     */
    private boolean controlloEsistenzaCredenziali (String nomeUtente, String pass){
        return mappaCredenzialiUtenti.containsKey(nomeUtente) && mappaCredenzialiUtenti.containsValue(pass);
    }   
}
