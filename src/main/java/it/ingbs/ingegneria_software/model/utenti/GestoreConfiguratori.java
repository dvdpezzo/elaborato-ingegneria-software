package it.ingbs.ingegneria_software.model.utenti;

import java.util.HashMap;
import java.util.Map;

import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;


public class GestoreConfiguratori {

    private Map<String, Configuratore> mappaConfiguratori = new HashMap<>();
    private GestoreDati gestoreDati;

    public GestoreConfiguratori(GestoreDati gestoreDati) {
        this.gestoreDati = gestoreDati;
        this.mappaConfiguratori = setMappaConfiguratori(gestoreDati.getCredenzialiConfiguratori());
    }

    /**
     * Converte la mappa nome utente e pass in una mappa nomeUtente e configuratore
     * @param mappaCredenziali mappa string - string con nome utente e pass
     * @return mappa contenente nomeUtente e configuratore 
     */
    private Map<String, Configuratore> setMappaConfiguratori (Map<String, String> mappaCredenziali) {
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
