package it.ingbs.ingegneria_software.controller;

import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.model.comprensori.GestoreComprensorio;
import it.ingbs.ingegneria_software.model.comprensori.GestoreComuni;
import it.ingbs.ingegneria_software.model.fattori.GestoreFattori;
import it.ingbs.ingegneria_software.model.gerarchie.GestoreGerarchie;
import it.ingbs.ingegneria_software.model.richieste.GestoreRichieste;
import it.ingbs.ingegneria_software.model.utenti.GestoreConfiguratori;
import it.ingbs.ingegneria_software.model.utenti.GestoreFruitori;
import it.ingbs.ingegneria_software.model.utenti.GestoreUtente;

public class ServiceProvider {

    private final GestoreDati gestoreDati;
    private GestoreConfiguratori gestoreConfiguratori;
    private GestoreFruitori gestoreFruitori;
    private GestoreGerarchie gestoreGerarchie;
    private GestoreFattori gestoreFattori;
    private GestoreComprensorio gestoreComprensorio;
    private GestoreRichieste gestoreRichieste;
    private GestoreUtente gestoreUtente;
    private GestoreComuni gestoreComuni;
    private UserMenuController sistemaGenerale;

    /**
     * Costruttore vuoto per inizializzare il Service Locator.
     * Gli oggetti verranno creati solo quando richiesti.
     */
    public ServiceProvider(GestoreDati gestoreDati) {
        this.gestoreDati = gestoreDati;
    }

    public GestoreConfiguratori getGestoreConfiguratori() {
        if (gestoreConfiguratori == null) {
            gestoreConfiguratori = new GestoreConfiguratori(gestoreDati);
        }
        return gestoreConfiguratori;
    }

    public GestoreFruitori getGestoreFruitori() {
        if (gestoreFruitori == null) {
            gestoreFruitori = new GestoreFruitori(gestoreDati);
        }
        return gestoreFruitori;
    }

    public GestoreGerarchie getGestoreGerarchie() {
        if (gestoreGerarchie == null) {
            gestoreGerarchie = new GestoreGerarchie(gestoreDati);
        }
        return gestoreGerarchie;
    }

    public GestoreFattori getGestoreFattori() {
        if (gestoreFattori == null) {
            gestoreFattori = new GestoreFattori(getGestoreGerarchie(), gestoreDati);
        }
        return gestoreFattori;
    }

    public GestoreComprensorio getGestoreComprensorio() {
        if (gestoreComprensorio == null) {
            gestoreComprensorio = new GestoreComprensorio(gestoreDati);
        }
        return gestoreComprensorio;
    }

    public GestoreRichieste getGestoreRichieste() {
        if (gestoreRichieste == null) {
            gestoreRichieste = new GestoreRichieste(gestoreDati);
        }
        return gestoreRichieste;
    }

    public UserMenuController getSistemaGenerale() {
        if (sistemaGenerale == null) {
            sistemaGenerale = new UserMenuController(this);
        }
        return sistemaGenerale;
    }

    public GestoreUtente getGestoreUtente() {
        if (gestoreUtente == null) {
            gestoreUtente = new GestoreUtente(gestoreDati);
        }
        return gestoreUtente;
    }

    public GestoreComuni getGestoreComuni() {
        if (gestoreComuni == null) {
            gestoreComuni = new GestoreComuni(gestoreDati);
        }
        return gestoreComuni;
    }

}
