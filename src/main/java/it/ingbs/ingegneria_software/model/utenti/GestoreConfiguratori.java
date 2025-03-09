package it.ingbs.ingegneria_software.model.utenti;

import java.util.HashMap;
import java.util.Map;

import it.ingbs.ingegneria_software.gestione_file.GestoreFileCredenziali;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;

public class GestoreConfiguratori {

    private static final String INSERIRE_NOME_UTENTE = "Inserire nome utente: ";
    private static final String INSERIRE_PASSWORD = "Inserire password: ";
    private static final String UTENTE_NON_TROVATO = "Utente non trovato: ";

    private Map<String, Configuratore> mappaConfiguratori = new HashMap<>();

    /**
     * Costruttore del GestoreConfiguratori.
     *
     * @param gestoreFileCredenziali il gestore delle credenziali da cui recuperare i dati
     */
    public GestoreConfiguratori(GestoreFileCredenziali gestoreFileCredenziali) {
        this.mappaConfiguratori = setMappaConfiguratori(gestoreFileCredenziali.getMappaCredenziali());
    }

    /**
     * Imposta la mappa dei configuratori a partire dalla mappa delle credenziali.
     *
     * @param mappaCredenziali la mappa delle credenziali
     * @return la mappa dei configuratori
     */
    private Map<String, Configuratore> setMappaConfiguratori(Map<String, String> mappaCredenziali) {
        for (Map.Entry<String, String> entry : mappaCredenziali.entrySet()) {
            String nomeUtente = entry.getKey();
            String password = entry.getValue();
            Configuratore configuratore = new Configuratore(nomeUtente, password);
            mappaConfiguratori.put(nomeUtente, configuratore);
        }
        return mappaConfiguratori;
    }

    /**
     * Trova un configuratore dato il nome utente.
     *
     * @param nomeUtente il nome utente del configuratore
     * @return il configuratore trovato
     * @throws IllegalArgumentException se il configuratore non viene trovato
     */
    public Configuratore trovaConfiguratore(String nomeUtente) {
        Configuratore configuratore = mappaConfiguratori.get(nomeUtente);
        if (configuratore == null) {
            throw new IllegalArgumentException(UTENTE_NON_TROVATO + nomeUtente);
        }
        return configuratore;
    }

    /**
     * Crea un nuovo utente configuratore.
     *
     * @return il nuovo configuratore creato
     */
    public Configuratore creaUtenteConfiguratore() {
        String nomeUtente;
        do {
            nomeUtente = InputDati.leggiStringaNonVuota(INSERIRE_NOME_UTENTE);
        } while (mappaConfiguratori.containsKey(nomeUtente));
        String passwordUtente = InputDati.leggiStringaNonVuota(INSERIRE_PASSWORD);
        Configuratore nuovoConfiguratore = new Configuratore(nomeUtente, passwordUtente);
        mappaConfiguratori.put(nuovoConfiguratore.getNomeUtente(), nuovoConfiguratore);
        return nuovoConfiguratore;
    }
}
