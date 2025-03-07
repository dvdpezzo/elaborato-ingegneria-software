package it.ingbs.ingegneria_software.gestione_accesso;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import it.ingbs.ingegneria_software.gestione_file.GestoreFileCredenziali;
import it.ingbs.ingegneria_software.model.Configuratore;
import it.ingbs.ingegneria_software.model.Fruitore;
import it.ingbs.ingegneria_software.model.GestoreConfiguratori;
import it.ingbs.ingegneria_software.model.GestoreFruitori;
import it.ingbs.ingegneria_software.model.GestoreUtente;

/**
 * Classe che si occupa solo della gestione della mappa delle credenziali
 */
public class GestoreAccesso {

    private static final String FILE_ASSENTE = "File assente";
    private static final String SEI_STASTO_REINDIRIZZATO_ALLA_CREAZIONE_DEL_TUO_NOME_UTENTE_E_PASSWORD_PERSONALI = "Sei stasto reindirizzato alla creazione del tuo Nome utente e Password personali:";
    private static final String ERRORE_NOME_UTENTE_O_PASSWORD_ERRATI = "ERRORE! Nome Utente o password errati!";
    private static final String ACCESSO_EFFETTUATO_CORRETAMENTE = "Accesso effettuato corretamente!";
    private static final String FILE_NON_TROVATO = "File non trovato: %s";
    private static final String FILE_DI_ACCESSO_CREDENZIALI_FRUITORI_TXT = "src\\File_di_accesso\\credenzialiFruitori.txt";
    private static final String FILE_DI_ACCESSO_CREDENZIALI_CONFIGURATORI_TXT = "src\\File_di_accesso\\credenzialiConfiguratori.txt";
    private static final String UTENTE_DEFAULT="admin";
    private static final String PASS_DEFAULT="admin";
    
    private File fileConfiguratori = new File(FILE_DI_ACCESSO_CREDENZIALI_CONFIGURATORI_TXT);
    private File fileFruitori = new File(FILE_DI_ACCESSO_CREDENZIALI_FRUITORI_TXT);
    private HashMap<String, String> mappaCredenzialiConf = new HashMap<>();
    private HashMap<String,String> mappaCredenzialiFrui = new HashMap<>();
    private GestoreFileCredenziali gestoreFileConf;
    private GestoreFileCredenziali gestoreFileFruit;
    private final GestoreConfiguratori gestoreConfiguratori;
    private final GestoreFruitori gestoreFruitori; 
    private final GestoreUtente gestoreUtente = new GestoreUtente(); 
    

    public GestoreAccesso() {       
        this.gestoreFileConf = new GestoreFileCredenziali(mappaCredenzialiConf);
        this.gestoreFileFruit = new GestoreFileCredenziali(mappaCredenzialiFrui);        
        try {
            mappaCredenzialiConf = gestoreFileConf.leggiFile(fileConfiguratori);
            mappaCredenzialiFrui = gestoreFileFruit.leggiFile(fileFruitori);
        } catch (IOException e) {
            System.out.println(String.format(FILE_NON_TROVATO, e.getMessage()));
        }
        this.gestoreConfiguratori = new GestoreConfiguratori(gestoreFileConf);
        this.gestoreFruitori = new GestoreFruitori(gestoreFileFruit);  
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
            if(controlloEsistenzaConfiguratore(nomeUtente, passUtente)){                
                Configuratore existingConfiguratore = gestoreConfiguratori.trovaConfiguratore(nomeUtente);
                System.out.println(ACCESSO_EFFETTUATO_CORRETAMENTE);
                return existingConfiguratore;
            }
            else{
                System.out.println(ERRORE_NOME_UTENTE_O_PASSWORD_ERRATI);
                return null;
            }
        }

    }
    /**
     * Effettua la registrazione di un nuovo configuratore: inserisce le credenziali nella mappa e salva sul file
     * @return nuovo configuratore
     */
    private Configuratore registrazioneNuovoConfiguratore () {
        System.out.println(SEI_STASTO_REINDIRIZZATO_ALLA_CREAZIONE_DEL_TUO_NOME_UTENTE_E_PASSWORD_PERSONALI);
        Configuratore newUtente = gestoreConfiguratori.creaUtenteConfiguratore();
        aggiungiCredenzialiConfAllaMappa(newUtente.getNomeUtente(), newUtente.getPassword());
        //aggiunge utente e nickname alla mappa del controllo di GestoreUtenti 
        gestoreUtente.aggiungiUtente(newUtente.getNomeUtente());   
        return newUtente;
    }
    /**
     * Aggiunge nome utente e password nella mappa e le salva sul file
     * @param nomeUtente 
     * @param pass password
     * @throws IOException 
     */
    private void aggiungiCredenzialiConfAllaMappa(String nomeUtente, String pass) {
        mappaCredenzialiConf.put(nomeUtente, pass);
        try {
            gestoreFileConf.salvaSuFile(fileConfiguratori);
        } catch (IOException e) {
            System.out.println(FILE_ASSENTE);
        }
    }

    /**
     * Controlla se i dati inseriti dall'utente (nome utente e password) sono corretti
     * @param nomeUtente
     * @param pass password
     * @return restituisce vero se li trova, altrimenti falso
     */
    private boolean controlloEsistenzaConfiguratore (String nomeUtente, String pass){
        return mappaCredenzialiConf.containsKey(nomeUtente) && mappaCredenzialiConf.containsValue(pass);
    }   


//-------------------------------------------------- PARTE SUL FRUITORE -----------------------------------------------

    public Fruitore accessoFruitore(String nomeUtente, String pass){
        if(controlloEsistenzaFruitore(nomeUtente,pass)){
            Fruitore existingFruitore = gestoreFruitori.trovaFruitore(nomeUtente);
                System.out.println(ACCESSO_EFFETTUATO_CORRETAMENTE);
                return existingFruitore;
        }
        else{
            System.out.println(ERRORE_NOME_UTENTE_O_PASSWORD_ERRATI);
            return null;
        }
    }

    /**
     * Controlla se i dati inseriti sono corretti
     * @param nomeUtente
     * @param pass password
     * @return restituisce vero se li trova falso se non li trova 
     */
    private boolean controlloEsistenzaFruitore(String nomeUtente,String pass){
        return mappaCredenzialiFrui.containsKey(nomeUtente) && mappaCredenzialiFrui.containsValue(pass);
    }


     /**
     * Aggiunge nome utente e password nella mappa e le salva sul file
     * @param nomeUtente 
     * @param pass password
     * @throws IOException 
     */
    private void aggiungiCredenzialiFruitAllaMappa(String nomeUtente, String pass) {
        mappaCredenzialiFrui.put(nomeUtente, pass);
        try {
            gestoreFileFruit.salvaSuFile(fileFruitori);
        } catch (IOException e) {
            System.out.println(FILE_ASSENTE);
        }
    }

    /**
     * Effettua la registrazione di un nuovo utente fruitore 
     * @return
     */
    public Fruitore registrazioneNuovoFruitore () {
        System.out.println(SEI_STASTO_REINDIRIZZATO_ALLA_CREAZIONE_DEL_TUO_NOME_UTENTE_E_PASSWORD_PERSONALI);
        Fruitore newUtente = gestoreFruitori.creaUtenteFruitore();
        aggiungiCredenzialiFruitAllaMappa(newUtente.getNomeUtente(), newUtente.getPassword());
        //aggiunge utente e nickname alla mappa del controllo di GestoreUtenti
        gestoreUtente.aggiungiUtente(newUtente.getNomeUtente());   
        return newUtente;
    }


    /**
     * DEVO MODIFICARE ALCUNI DI QUESTI METODI IN MODO DA PERMETTERE IL SALVATAGGIO SU FILE DI TUTTI I DATI DEL FRUITORE 
     */
}
