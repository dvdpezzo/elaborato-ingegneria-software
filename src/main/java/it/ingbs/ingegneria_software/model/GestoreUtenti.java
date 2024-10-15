package it.ingbs.ingegneria_software.model;

import java.util.*;
import it.ingbs.ingegneria_software.gestioneAccesso.GestoreFileCredenziali;
import it.ingbs.ingegneria_software.utilitaGenerale.InputDati;


public class GestoreUtenti {

    private HashMap<String, Utente> mappaUtenti = new HashMap<>();

    public GestoreUtenti(GestoreFileCredenziali gestoreFileCredenziali) {
         this.mappaUtenti = setMappaUtenti(gestoreFileCredenziali.getMappaCredenziali());
    }
    private HashMap<String, Utente> setMappaUtenti(HashMap<String, String> mappaCredenziali) {
        for (Map.Entry<String, String> entry : mappaCredenziali.entrySet()) {
            String nome = entry.getKey();
            String pass = entry.getValue();
            Utente utente = new Utente(nome, pass);
            mappaUtenti.put(nome, utente);
        }
        return mappaUtenti;
        
    }

    public Utente trovaUtente(String nomeUtente) {
        Utente utente = mappaUtenti.get(nomeUtente);
        if (utente == null) {
            throw new IllegalArgumentException("Utente non trovato: " + nomeUtente);
        }
        return utente;
    }

    public Configuratore creaUtenteConfiguratore() {
        String nomeUtente;
        do {
            nomeUtente = InputDati.leggiStringaNonVuota("Inserire nome utente: ");
        } while (mappaUtenti.containsKey(nomeUtente));
        String passwordUtente = InputDati.leggiStringaNonVuota("Inserire password: ");
        Configuratore newUtente = new Configuratore(nomeUtente, passwordUtente);
        mappaUtenti.put(newUtente.getNomeUtente(), newUtente);
        return newUtente;        
    }


}
