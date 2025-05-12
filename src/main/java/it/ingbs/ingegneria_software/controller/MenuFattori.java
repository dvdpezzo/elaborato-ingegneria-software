package it.ingbs.ingegneria_software.controller;

import it.ingbs.ingegneria_software.model.fattori.GestoreFattori;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class MenuFattori implements Runnable {

    private final GestoreFattori gestoreFattori;
    private static final String TITOLO_MENU = "Gestione Fattori di Conversione";
    private static final String[] VOCI_MENU = {
        "Visualizza gerarchie",
        "Aggiungi nuovo fattore di conversione", 
        "Aggiungi fattore di conversione derivato", 
        "Rimuovi fattore", 
        "Salva fattori di conversione",
        "Visualizza fattori di conversione" 
    };

    public MenuFattori(GestoreFattori gestoreFattori) {
        this.gestoreFattori = gestoreFattori;
    }

    @Override
    public void run() {
        MenuUtil menuFattori = new MenuUtil(TITOLO_MENU, VOCI_MENU);
        int scelta;
        do {
            scelta = menuFattori.scegli();
            switch (scelta) {
                case 1:
                    gestoreFattori.viewGerarchie();
                    break;
                case 2:
                    gestoreFattori.aggiungi();
                    break;
                case 3:
                    gestoreFattori.nuovoFattoreDerivato();
                    break;
                case 4:
                    gestoreFattori.rimuovi();
                    break;
                case 5:
                    gestoreFattori.salva();
                    break;
                case 6:
                    gestoreFattori.view();
                    break;
            }
        } while (scelta != 0);
    }
}
