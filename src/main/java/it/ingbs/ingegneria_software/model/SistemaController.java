package it.ingbs.ingegneria_software.model;

import java.util.logging.Logger;
import java.util.logging.Level;

import it.ingbs.ingegneria_software.controller.UserMenuController;
import it.ingbs.ingegneria_software.gestione_accesso.AuthenticationHandler;
import it.ingbs.ingegneria_software.gestione_accesso.GestoreAccessoConfiguratore;
import it.ingbs.ingegneria_software.gestione_accesso.GestoreAccessoFruitore;
import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.gestione_file.GestoreFile;
import it.ingbs.ingegneria_software.model.utenti.Configuratore;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;
import it.ingbs.ingegneria_software.utilita_generale.ServiceProvider;

/**
 * Controller principale del sistema che gestisce le operazioni di base
 * come login, caricamento dati e gestione menu.
 */
public class SistemaController {
    
    private static final Logger LOGGER = Logger.getLogger(SistemaController.class.getName());
    private static SistemaController instance;
    private final ServiceProvider serviceFactory;
    private final GestoreFile gestoreFile;
    private final GestoreDati gestoreDati;

    private SistemaController() {
        this.gestoreFile = new GestoreFile();
        this.gestoreDati = GestoreDati.getInstance();
        this.serviceFactory = new ServiceProvider(gestoreDati);
    }

    /**
     * Restituisce l'istanza singleton del controller.
     * @return l'istanza del SistemaController
     */
    public static SistemaController getInstance() {
        if (instance == null) {
            instance = new SistemaController();
        }
        return instance;
    }

    /**
     * Carica i dati salvati dal sistema.
     * Gestisce le eccezioni e registra gli errori nel log.
     */
    public void caricaSalvataggi() {
        try {
            gestoreFile.caricaSalvataggio();
            LOGGER.info("Salvataggi caricati con successo.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante il caricamento dei salvataggi", e);
            throw new RuntimeException("Errore durante il caricamento dei salvataggi", e);
        }
    }

    /**
     * Gestisce il processo di login dell'utente.
     * @return il tipo di utente ("configuratore" o "fruitore") o null se il login fallisce
     */
    public String login() {
        LOGGER.info("Avvio processo di login");
        MenuUtil menuLogin = new MenuUtil("Login", new String[]{"Configuratore", "Fruitore"});
        int scelta = menuLogin.scegli();
        
        String tipoUtente;
        switch (scelta) {
            case 1:
                tipoUtente = "configuratore";
                break;
            case 2:
                tipoUtente = "fruitore";
                break;
            default:
                tipoUtente = null;
                break;
        }

        if (tipoUtente == null) {
            LOGGER.warning("Scelta login non valida");
        }
        
        return tipoUtente;
    }

    /**
     * Mostra il menu appropriato in base al tipo di utente.
     * @param tipoUtente il tipo di utente ("configuratore" o "fruitore")
     */
    public void mostraMenu(String tipoUtente) {
        if (tipoUtente == null) {
            LOGGER.warning("Tipo utente non specificato");
            return;
        }

        AuthenticationHandler controlloAccesso = new AuthenticationHandler();

        try {
            if ("configuratore".equalsIgnoreCase(tipoUtente)) {
                gestisciMenuConfiguratore(controlloAccesso);
            } else if ("fruitore".equalsIgnoreCase(tipoUtente)) {
                gestisciMenuFruitore(controlloAccesso);
            } else {
                LOGGER.warning("Tipo utente non riconosciuto: " + tipoUtente);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante la gestione del menu", e);
        }
    }

    private void gestisciMenuConfiguratore(AuthenticationHandler controlloAccesso) {
        GestoreAccessoConfiguratore gestoreAccessoConfiguratore = new GestoreAccessoConfiguratore(serviceFactory);
        Configuratore configuratore = controlloAccesso.login(gestoreAccessoConfiguratore);
        if (configuratore != null) {
            UserMenuController gestoreMenu = serviceFactory.getSistemaGenerale();
            gestoreMenu.backEnd();
        }
    }

    private void gestisciMenuFruitore(AuthenticationHandler controlloAccesso) {
        GestoreAccessoFruitore gestoreAccessoFruitore = new GestoreAccessoFruitore(serviceFactory);
        Fruitore fruitore = controlloAccesso.login(gestoreAccessoFruitore);
        if (fruitore != null) {
            UserMenuController gestoreMenu = serviceFactory.getSistemaGenerale();
            gestoreMenu.frontEnd(fruitore);
        }
    }

    /**
     * Salva i dati del sistema.
     */
    public void salvaDati() {
        try {
            gestoreFile.creaSalvataggio();
            LOGGER.info("Dati salvati con successo");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante il salvataggio dei dati", e);
            throw new RuntimeException("Impossibile salvare i dati", e);
        }
    }
}
