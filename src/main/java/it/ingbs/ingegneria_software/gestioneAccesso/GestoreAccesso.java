package it.ingbs.ingegneria_software.gestioneAccesso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import it.ingbs.ingegneria_software.model.Configuratore;
import it.ingbs.ingegneria_software.model.GestoreConfiguratori;

/**
 * Classe che si occupa solo della gestione della mappa delle credenziali
 */
public class GestoreAccesso {

    private static final String FILE_DI_ACCESSO_CREDENZIALI_FRUITORI_TXT = "src\\File di accesso\\credenzialiFruitori.txt";
    private static final String FILE_DI_ACCESSO_CREDENZIALI_CONFIGURATORI_TXT = "src\\File di accesso\\credenzialiConfiguratori.txt";
    private static final String UTENTE_DEFAULT="admin";
    private static final String PASS_DEFAULT="admin";
    
    private File file_configuratori = new File(FILE_DI_ACCESSO_CREDENZIALI_CONFIGURATORI_TXT);
    private File file_fruitori = new File(FILE_DI_ACCESSO_CREDENZIALI_FRUITORI_TXT);
    private HashMap<String, String> mappaCredenzialiUtenti = new HashMap<>();
    private GestoreFileCredenziali gestoreFile;
    private GestoreConfiguratori gestoreConfiguratori ;
    

    public GestoreAccesso() {       
        this.gestoreFile = new GestoreFileCredenziali(mappaCredenzialiUtenti);        
        try {
            gestoreFile.configuraMappaCredenzialiDaFile(file_configuratori);
        } catch (Exception e) {
            System.out.println("File non trovato: "+e.getMessage());
        }
        this.gestoreConfiguratori = new GestoreConfiguratori(gestoreFile);
    }
    /**
     * permette l'accesso all'utente: controlla prima di tutto che non sia il primo accesso,
     * altrimenti controlla le credenziali e se sono valide, permette l'accesso
     * @param nomeUtente
     * @param passUtente
     * @return nuovoUtente se è il primo accesso, existingUtente se l'accesso è andato a buon fine, null se le credenziali sono errate
     */
    public Configuratore accessoConfiguratore (String nomeUtente, String passUtente ){
        if(nomeUtente.equals(UTENTE_DEFAULT) && passUtente.equals(PASS_DEFAULT)){
            return registrazioneNuovoConfiguratore();
        }else
        {
            if(controlloEsistenzaCredenziali(nomeUtente, passUtente)){                
                Configuratore existingUtente = gestoreConfiguratori.trovaConfiguratore(nomeUtente);
                System.out.println("Accesso effettuato corretamente!");
                return existingUtente;
            }
            else{
                System.out.println("ERRORE! Nome Utente o password errati!");
                return null;
            }
        }

    }
    /**
     * Inserisce le credenziali nella mappa e salva sul file
     * @return
     */
    private Configuratore registrazioneNuovoConfiguratore () {
        System.out.println("Sei stasto reindirizzato alla creazione del tuo Nome utente e Password personali:");
        Configuratore newUtente = gestoreConfiguratori.creaUtenteConfiguratore();
        aggiungiCredenzialiAllaMappa(newUtente.getNomeUtente(), newUtente.getPassword());
        return newUtente;
    }
    /**
     * Permette di aggiungere nome utente e password nella mappa e le salva sul file
     * @param nomeUtente 
     * @param pass password
     * @throws IOException 
     */
    private void aggiungiCredenzialiAllaMappa(String nomeUtente, String pass) {
        mappaCredenzialiUtenti.put(nomeUtente, pass);
        try {
            gestoreFile.salvaMappaCredenzialiSuFile(file_configuratori);
        } catch (IOException e) {
            e.printStackTrace();
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
