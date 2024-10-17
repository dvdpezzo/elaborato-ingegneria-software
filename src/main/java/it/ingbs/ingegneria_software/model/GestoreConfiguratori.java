package it.ingbs.ingegneria_software.model;

import java.util.HashMap;
import java.util.Map;

import it.ingbs.ingegneria_software.gestione_accesso.GestoreFileCredenziali;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;


public class GestoreConfiguratori {

    private HashMap<String, Configuratore> mappaConfiguratori = new HashMap<>();

    public GestoreConfiguratori(GestoreFileCredenziali gestoreFileCredenziali) {
         this.mappaConfiguratori = setMappaConfiguratori(gestoreFileCredenziali.getMappaCredenziali());
    }
    private HashMap<String, Configuratore> setMappaConfiguratori (HashMap<String, String> mappaCredenziali) {
        for (Map.Entry<String, String> entry : mappaCredenziali.entrySet()) {
            String nome = entry.getKey();
            String pass = entry.getValue();
            Configuratore utente = new Configuratore(nome, pass);
            mappaConfiguratori.put(nome, utente);
        }
        return mappaConfiguratori;
        
    }

    public Configuratore trovaConfiguratore (String nomeUtente) {
        Configuratore utente = mappaConfiguratori.get(nomeUtente);
        if (utente == null) {
            throw new IllegalArgumentException("Utente non trovato: " + nomeUtente);
        }
        return utente;
    }

    public Configuratore creaUtenteConfiguratore() {
        String nomeUtente;
        do {
            nomeUtente = InputDati.leggiStringaNonVuota("Inserire nome utente: ");
        } while (mappaConfiguratori.containsKey(nomeUtente));
        String passwordUtente = InputDati.leggiStringaNonVuota("Inserire password: ");
        Configuratore newUtente = new Configuratore(nomeUtente, passwordUtente);
        mappaConfiguratori.put(newUtente.getNomeUtente(), newUtente);
        return newUtente;        
    }


}
