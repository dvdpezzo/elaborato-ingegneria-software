package it.ingbs.ingegneria_software.model.utenti;

import java.util.HashMap;
import java.util.Map;

import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;


public class GestoreConfiguratori {

    private final Map<String, Configuratore> mappaConfiguratori = new HashMap<>();
    private final GestoreDati gestoreDati;

    public GestoreConfiguratori(GestoreDati gestoreDati) {
        this.gestoreDati = gestoreDati;
        setMappaConfiguratori(gestoreDati.getCredenzialiConfiguratori());
    }

    /**
     * Converte la mappa nome utente e pass in una mappa nomeUtente e configuratore
     * @param mappaCredenziali mappa string - string con nome utente e pass
     * @return mappa contenente nomeUtente e configuratore 
     */
    private Map<String, Configuratore> setMappaConfiguratori (Map<String, String> mappaCredenziali) {
        mappaCredenziali.forEach((nome, pass) ->  
          mappaConfiguratori.put(nome, new Configuratore(nome, pass)));
        return mappaConfiguratori;
    }

    
    public Map<String, Configuratore> getMappaConfiguratori() {
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
        
        Configuratore newUtente = new Configuratore(nomeUtente, 
            InputDati.leggiStringaNonVuota("Inserire password: "));
        mappaConfiguratori.put(newUtente.getNomeUtente(), newUtente);
        HashMap<String, String> credenzialiMap = new HashMap<>();
        for (Map.Entry<String, Configuratore> entry : mappaConfiguratori.entrySet()) {
            credenzialiMap.put(entry.getKey(), entry.getValue().getPassword());
        }
        gestoreDati.setCredenzialiConfiguratori(credenzialiMap);
        return newUtente;        
    }



}
