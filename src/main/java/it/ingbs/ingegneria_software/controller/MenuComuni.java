package it.ingbs.ingegneria_software.controller;

import it.ingbs.ingegneria_software.model.comprensori.GestoreComuni;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class MenuComuni implements Runnable {
    
    private final GestoreComuni gestoreComuni;
    private static final String TITOLO_MENU = "Gestione Comuni";
    private static final String[] VOCI_MENU = {
        "Visualizza Comuni",
        "Aggiungi nuovo Comune", 
        "Rimuovi Comune", 
        "Salva modifiche"
    };

    public MenuComuni(GestoreComuni gestoreComuni) {
        this.gestoreComuni = gestoreComuni;
    }

    @Override
    public void run() {
        MenuUtil menuComuni = new MenuUtil(TITOLO_MENU, VOCI_MENU);
        int scelta;
        do {
            scelta = menuComuni.scegli();
            switch (scelta) {
                case 1:
                    gestoreComuni.view();
                    break;
                case 2:
                    gestoreComuni.aggiungi();
                    break;
                case 3:
                    gestoreComuni.rimuovi();
                    break;
                case 4:
                    gestoreComuni.salva();
                    break;
            }
        } while (scelta != 0);
    }

}
