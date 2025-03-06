package it.ingbs.ingegneria_software.model;

import java.io.File;
import java.util.HashMap;

public class GestoreUtente {

    //mappa che contiene configuratori e fruitori con il loro nome utente 
    HashMap<Utente,String> mappaUtenti = new HashMap<Utente,String>();
    

    /**
     * Aggiungo un utente alla mappa 
     * @param utente utente aggiunto 
     * @param nomeUtente nome utente dell'utente 
     */
    public void aggiungiUtente(Utente utente, String nomeUtente){
        mappaUtenti.put(utente,nomeUtente);
    }

    /**
     * Controlla tutti i nomi utente e verifica se il nome inserito è univoco 
     * @param nomeUtente nome inserito 
     * @return true se il nome è già presente, false se il nome non è presente 
     */
    public boolean controlloUtente(String nomeUtente){

        for(String nome : mappaUtenti.values()){
            if(nome.equals(nomeUtente)){
                return true;
            }
        }
        return false; 

    }

    /**
     * Deve leggere il file configuratori e fruitori per aggiungere i nomi + utente alla mappa
     * @param nomeFile
     */
    public void riempiMappa(File nomeFile){


    }




    
    
}
