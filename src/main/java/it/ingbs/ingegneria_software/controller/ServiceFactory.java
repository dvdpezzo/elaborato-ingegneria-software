package it.ingbs.ingegneria_software.controller;

import it.ingbs.ingegneria_software.gestione_accesso.ControlloAccesso;
import it.ingbs.ingegneria_software.gestione_accesso.GestoreAccessoConfiguratore;
import it.ingbs.ingegneria_software.gestione_accesso.GestoreAccessoFruitore;
import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.gestione_file.GestoreFile;
import it.ingbs.ingegneria_software.model.comprensori.GestoreComprensorio;
import it.ingbs.ingegneria_software.model.fattori.GestoreFattori;
import it.ingbs.ingegneria_software.model.gerarchie.GestoreGerarchie;
import it.ingbs.ingegneria_software.model.richieste.GestoreRichieste;

public class ServiceFactory {

    private GestoreFile gestoreFile;
    private GestoreDati gestoreDati;
    private GestoreGerarchie gestoreGerarchie;
    private GestoreFattori gestoreFattori;
    private GestoreComprensorio gestoreComprensorio;
    private GestoreRichieste gestoreRichieste;
    private GestoreMenu sistemaGenerale;
    private GestoreAccessoConfiguratore gestoreAccessoConfiguratore;
    private GestoreAccessoFruitore gestoreAccessoFruitore;
    private ControlloAccesso sistemaAccesso;

    /**
     * Costruttore vuoto per inizializzare il Service Locator.
     * Gli oggetti verranno creati solo quando richiesti.
     */
    public ServiceFactory() {
        // Nessuna inizializzazione immediata, tutto sarà lazy
    }

    public GestoreFile getGestoreFile() {
        if (gestoreFile == null) {
            gestoreFile = new GestoreFile();
        }
        return gestoreFile;
    }

    public GestoreDati getGestoreDati() {
        if (gestoreDati == null) {
            gestoreDati = getGestoreFile().getGestoreDati();
        }
        return gestoreDati;
    }

    public GestoreGerarchie getGestoreGerarchie() {
        if (gestoreGerarchie == null) {
            gestoreGerarchie = new GestoreGerarchie(getGestoreDati().getGerarchie(), getGestoreFile());
        }
        return gestoreGerarchie;
    }

    public GestoreFattori getGestoreFattori() {
        if (gestoreFattori == null) {
            gestoreFattori = new GestoreFattori(getGestoreDati().getFattori(), getGestoreGerarchie(), getGestoreFile());
        }
        return gestoreFattori;
    }

    public GestoreComprensorio getGestoreComprensorio() {
        if (gestoreComprensorio == null) {
            gestoreComprensorio = new GestoreComprensorio(getGestoreDati().getComprensori(), getGestoreFile());
        }
        return gestoreComprensorio;
    }

    public GestoreRichieste getGestoreRichieste() {
        if (gestoreRichieste == null) {
            gestoreRichieste = new GestoreRichieste(getGestoreFile(), getGestoreDati().getRichieste());
        }
        return gestoreRichieste;
    }

    public GestoreMenu getSistemaGenerale() {
        if (sistemaGenerale == null) {
            sistemaGenerale = new GestoreMenu(getGestoreGerarchie(), getGestoreFattori(), getGestoreComprensorio(), getGestoreRichieste());
        }
        return sistemaGenerale;
    }

    public GestoreAccessoConfiguratore getGestoreAccessoConfiguratore() {
        if (gestoreAccessoConfiguratore == null) {
            gestoreAccessoConfiguratore = new GestoreAccessoConfiguratore(getGestoreDati(), getGestoreFile(), getGestoreComprensorio());
        }
        return gestoreAccessoConfiguratore;
    }

    public GestoreAccessoFruitore getGestoreAccessoFruitore() {
        if (gestoreAccessoFruitore == null) {
            gestoreAccessoFruitore = new GestoreAccessoFruitore(getGestoreDati(), getGestoreFile(), getGestoreComprensorio());
        }
        return gestoreAccessoFruitore;
    }

    public ControlloAccesso getSistemaAccesso() {
        if (sistemaAccesso == null) {
            sistemaAccesso = new ControlloAccesso(getGestoreAccessoConfiguratore(), getGestoreAccessoFruitore(), getGestoreFile(), getGestoreDati(), getGestoreComprensorio());
        }
        return sistemaAccesso;
    }
}
