package it.ingbs.ingegneria_software.model;

import java.io.IOException;

import it.ingbs.ingegneria_software.controller.ServiceProvider;
import it.ingbs.ingegneria_software.controller.UserMenuController;
import it.ingbs.ingegneria_software.gestione_accesso.AuthenticationHandler;
import it.ingbs.ingegneria_software.gestione_accesso.GestoreAccessoConfiguratore;
import it.ingbs.ingegneria_software.gestione_accesso.GestoreAccessoFruitore;
import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.gestione_file.GestoreFile;
import it.ingbs.ingegneria_software.model.utenti.Configuratore;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class Sistema {
    
    private static Sistema instance;
    private final ServiceProvider serviceFactory;
    private final GestoreFile gestoreFile;
    private final GestoreDati gestoreDati;

    
    private Sistema() {
        this.gestoreFile = new GestoreFile();
        this.gestoreDati = GestoreDati.getInstance();
        this.serviceFactory = new ServiceProvider(gestoreDati);
    }

    
    public static Sistema getInstance() {
        if (instance == null) {
            instance = new Sistema();
        }
        return instance;
    }

    
     public void caricaSalvataggi() {
        
        try {
            gestoreFile.caricaSalvataggio();
        } catch (IOException ex) {
            System.err.println("Errore durante il caricamento dei salvataggi: " + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Errore imprevisto durante il caricamento dei salvataggi: " + e.getMessage());
        }        
        System.out.println("Salvataggi caricati.");
    }

    public String login() {
        // Logica per gestire il login e determinare il tipo di utente
        System.out.println("Effettua il login: configuratore o fruitore?");
        MenuUtil menuLogin = new MenuUtil("Login", new String[]{"Configuratore", "Fruitore"});
        int scelta = menuLogin.scegli();
        switch (scelta) {
            case 1:
                return "configuratore";
            case 2:
                return "fruitore";
            default:
                System.out.println("Scelta non valida.");
                return null;
        }
    }

    public void mostraMenu(String tipoUtente) {
        AuthenticationHandler controlloAccesso = new AuthenticationHandler();
        if ("configuratore".equalsIgnoreCase(tipoUtente)) {
            GestoreAccessoConfiguratore gestoreAccessoConfiguratore = new GestoreAccessoConfiguratore(serviceFactory);
            Configuratore configuratore = controlloAccesso.login(gestoreAccessoConfiguratore);
            if (configuratore != null) {
                UserMenuController gestoreMenu = serviceFactory.getSistemaGenerale();
                gestoreMenu.backEnd();
            }
        } else if ("fruitore".equalsIgnoreCase(tipoUtente)) {
            GestoreAccessoFruitore gestoreAccessoFruitore = new GestoreAccessoFruitore(serviceFactory);
            Fruitore fruitore = controlloAccesso.login(gestoreAccessoFruitore);
            if (fruitore != null) {
                UserMenuController gestoreMenu = serviceFactory.getSistemaGenerale();
                gestoreMenu.frontEnd(fruitore);
            }
        } else {
            System.out.println("Tipo utente non riconosciuto.");
        }
    }

    public void salvaDati() {
        gestoreFile.creaSalvataggio();
        System.out.println("Dati salvati.");
    }

}
