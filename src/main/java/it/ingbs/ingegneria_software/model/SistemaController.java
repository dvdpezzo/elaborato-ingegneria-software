package it.ingbs.ingegneria_software.model;

import it.ingbs.ingegneria_software.Eccezioni.LoadException;
import it.ingbs.ingegneria_software.Eccezioni.ReadException;
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
     * @throws LoadException 
     */
    public void caricaSalvataggi() throws LoadException {
        try {
            gestoreFile.caricaSalvataggio();
        } catch (ReadException e) {
            throw e;
        } catch (Exception e) {
            throw new LoadException();
        }
    }

    /**
     * Gestisce il processo di login dell'utente.
     * @return il tipo di utente ("configuratore" o "fruitore")
     * @throws ReadException se la scelta non è valida
     */
    public String login() {
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
                throw new ReadException();
        }
        return tipoUtente;
    }

    /**
     * Mostra il menu appropriato in base al tipo di utente.
     * @param tipoUtente il tipo di utente ("configuratore" o "fruitore")
     * @throws ReadException se il tipo utente non è valido
     * @throws LoadException se si verifica un errore durante la gestione del menu
     */
    public void mostraMenu(String tipoUtente) throws LoadException {
        if (tipoUtente == null) {
            throw new ReadException();
        }

        AuthenticationHandler controlloAccesso = new AuthenticationHandler();

        try {
            if ("configuratore".equalsIgnoreCase(tipoUtente)) {
                gestisciMenuConfiguratore(controlloAccesso);
            } else if ("fruitore".equalsIgnoreCase(tipoUtente)) {
                gestisciMenuFruitore(controlloAccesso);
            } else {
                throw new ReadException();
            }
        } catch (ReadException e) {
            throw e;
        } catch (Exception e) {
            throw new LoadException();
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
     * @throws LoadException se si verifica un errore durante il salvataggio
     */
    public void salvaDati() throws LoadException {
        try {
            gestoreFile.creaSalvataggio();
        } catch (Exception e) {
            throw new LoadException();
        }
    }
}
