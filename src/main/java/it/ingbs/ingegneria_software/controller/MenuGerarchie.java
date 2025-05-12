package it.ingbs.ingegneria_software.controller;

import it.ingbs.ingegneria_software.model.gerarchie.GestoreGerarchie;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class MenuGerarchie implements Runnable {

    private final GestoreGerarchie gestoreGerarchie;
    private static final String TITOLO_MENU = "Gestione Gerarchie";
    private static final String[] VOCI_MENU = {
        "Aggiungi Gerarchia",
        "Rimuovi Gerarchia",
        "Modifica Gerarchia",
        "Visualizza Gerarchie",
        "Salva Gerarchie"
    };

    public MenuGerarchie(GestoreGerarchie gestoreGerarchie) {
        this.gestoreGerarchie = gestoreGerarchie;
    }

    @Override
    public void run() {
        MenuUtil menu = new MenuUtil(TITOLO_MENU, VOCI_MENU);
        int scelta;
        do {
            scelta = menu.scegli();
            switch (scelta) {
                case 1:
                    gestoreGerarchie.aggiungi();
                    break;
                case 2:
                    gestoreGerarchie.rimuovi();
                    break;
                case 3:
                    gestoreGerarchie.modificaGerarchia();
                    break;
                case 4:
                    gestoreGerarchie.view();
                    break;
                case 5:
                    gestoreGerarchie.salva();
                    break;
            }
        } while (scelta != 0);
    }
}
