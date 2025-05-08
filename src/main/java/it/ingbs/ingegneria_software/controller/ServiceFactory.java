package it.ingbs.ingegneria_software.controller;

import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.model.comprensori.GestoreComprensorio;
import it.ingbs.ingegneria_software.model.fattori.GestoreFattori;
import it.ingbs.ingegneria_software.model.gerarchie.GestoreGerarchie;
import it.ingbs.ingegneria_software.model.richieste.GestoreRichieste;

public class ServiceFactory {

    private GestoreDati gestoreDati;
    private GestoreGerarchie gestoreGerarchie;
    private GestoreFattori gestoreFattori;
    private GestoreComprensorio gestoreComprensorio;
    private GestoreRichieste gestoreRichieste;
    private GestoreMenu sistemaGenerale;

    /**
     * Costruttore vuoto per inizializzare il Service Locator.
     * Gli oggetti verranno creati solo quando richiesti.
     */
    public ServiceFactory(GestoreDati gestoreDati) {
        this.gestoreDati = gestoreDati;
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

    public GestoreMenu getSistemaGenerale() {
        if (sistemaGenerale == null) {
            sistemaGenerale = new GestoreMenu(getGestoreGerarchie(), getGestoreFattori(), getGestoreComprensorio(), getGestoreRichieste());
        }
        return sistemaGenerale;
    }

}
