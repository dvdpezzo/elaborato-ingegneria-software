package it.ingbs.ingegneria_software.gestione_accesso;

import java.io.IOException;
import java.util.HashMap;

import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.gestione_file.GestoreFile;
import it.ingbs.ingegneria_software.model.comprensori.GestoreComprensorio;
import it.ingbs.ingegneria_software.model.utenti.Configuratore;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;
import it.ingbs.ingegneria_software.model.utenti.GestoreConfiguratori;
import it.ingbs.ingegneria_software.model.utenti.GestoreFruitori;
import it.ingbs.ingegneria_software.model.utenti.GestoreUtente;

/**
 * Classe che si occupa solo della gestione della mappa delle credenziali
 */
public class GestoreAccesso {

    private static final String SEI_STASTO_REINDIRIZZATO_ALLA_CREAZIONE_DEL_TUO_NOME_UTENTE_E_PASSWORD_PERSONALI = "Sei stasto reindirizzato alla creazione del tuo Nome utente e Password personali:";
    private static final String ERRORE_NOME_UTENTE_O_PASSWORD_ERRATI = "ERRORE! Nome Utente o password errati!";
    private static final String ACCESSO_EFFETTUATO_CORRETAMENTE = "Accesso effettuato corretamente!";
    private static final String UTENTE_DEFAULT="admin";
    private static final String PASS_DEFAULT="admin";
    
    private final HashMap<String, String> mappaCredenzialiConf;
    private final HashMap<String,String> mappaCredenzialiFrui;
    private HashMap<String, Fruitore> mappaDatiFruitori;
    private final GestoreFile gestoreFile;
    private final GestoreConfiguratori gestoreConfiguratori;
    private final GestoreFruitori gestoreFruitori; 
    private final GestoreUtente gestoreUtente;
    private final GestoreComprensorio gestoreComprensorio;
    private final GestoreDati gestoreDati;
    

    public GestoreAccesso(GestoreDati gestoreDati, GestoreFile gestoreFile, GestoreComprensorio gestoreComprensorio) {
        this.gestoreDati = gestoreDati;
        this.gestoreUtente = new GestoreUtente(gestoreDati.getUtenti());
        this.mappaCredenzialiConf = gestoreDati.getCredenzialiConfiguratori();
        this.mappaCredenzialiFrui = gestoreDati.getCredenzialiFruitori();
        this.gestoreFile = gestoreFile;
        this.gestoreConfiguratori = new GestoreConfiguratori(gestoreDati);
        this.gestoreComprensorio = gestoreComprensorio;
        this.gestoreFruitori = new GestoreFruitori(gestoreUtente, gestoreDati, gestoreComprensorio);
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
        gestoreFile.salvaCredenzialiConfiguratori();
    }

    /**
     * Controlla se i dati inseriti dall'utente (nome utente e password) sono corretti
     * @param nomeUtente
     * @param pass password
     * @return restituisce vero se li trova, altrimenti falso
     */
    private boolean controlloEsistenzaConfiguratore (String nomeUtente, String pass){
        return mappaCredenzialiConf.containsKey(nomeUtente) && mappaCredenzialiConf.get(nomeUtente).equals(pass);
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
        return mappaCredenzialiFrui.containsKey(nomeUtente) && mappaCredenzialiFrui.get(nomeUtente).equals(pass);
    }


     /**
     * Aggiunge nome utente e password nella mappa e le salva sul file
     * @param nomeUtente 
     * @param pass password
     * @throws IOException 
     */
    private void aggiungiCredenzialiFruitAllaMappa(String nomeUtente, String pass) {
        mappaCredenzialiFrui.put(nomeUtente, pass);
        gestoreFile.salvaCredenzialiFruitori();
        gestoreFile.salvaDatiFruitori();
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

    public void caricaDatiFruitori() {
        this.mappaDatiFruitori = gestoreDati.getDatiFruitori();
    }


    
}
