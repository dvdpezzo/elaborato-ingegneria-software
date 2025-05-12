package it.ingbs.ingegneria_software.controller;

import it.ingbs.ingegneria_software.model.comprensori.GestoreComprensorio;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class MenuComprensorio implements Runnable {
    private final GestoreComprensorio gestoreComprensorio;
    private static final String TITOLO_MENU = "Gestione Comprensori";
    private static final String[] VOCI_MENU = {
        "Visualizza comprensori",
        "Aggiungi comune al comprensorio:",
        "Aggiungi Comprensorio", 
        "Rimuovi Comprensorio", 
        "Salva Cambiamenti" 
    };

    public MenuComprensorio(GestoreComprensorio gestoreComprensorio) {
        this.gestoreComprensorio = gestoreComprensorio;
    }

    @Override
    public void run() {
        MenuUtil menuComprensorio = new MenuUtil(TITOLO_MENU, VOCI_MENU);
        gestoreComprensorio.impostaComuni();
        int scelta;
        do {
            scelta = menuComprensorio.scegli();
            switch (scelta) {
                case 1:
                    gestoreComprensorio.visualizzaComprensori();
                    break;
                case 2:
                gestoreComprensorio.aggiungiComuneAlComprensorio();
                    break;
                case 3:
                gestoreComprensorio.aggiungiComprensorio();
                    break;
                case 4:
                gestoreComprensorio.rimuoviComprensorio();
                    break;
                case 5:
                gestoreComprensorio.salvaMappaComprensoriSuFile();
                    break;
            }
        } while (scelta != 0);
    }

}
