package it.ingbs.ingegneria_software.model;


import java.util.*;

public class GestoreUtente {

    //mappa che contiene configuratori e fruitori con il loro nome utente 
    ArrayList<String> elencoNomi = new ArrayList<String>();

    

    /**
     * Aggiungo un utente alla mappa 
     * @param utente utente aggiunto 
     * @param nomeUtente nome utente dell'utente 
     */
    public void aggiungiUtente(String nomeUtente){
        elencoNomi.add(nomeUtente);
    }

    /**
     * Controlla tutti i nomi utente e verifica se il nome inserito è univoco 
     * @param nomeUtente nome inserito 
     * @return true se il nome è già presente, false se il nome non è presente 
     */
    public boolean controlloUtente(String nomeUtente){

        for(int i=0; i<elencoNomi.size();i++){
            if(elencoNomi.get(i).equals(nomeUtente)){
                return true;
            }
        }
        return false; 
    }    
}
