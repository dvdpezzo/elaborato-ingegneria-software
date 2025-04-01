package it.ingbs.ingegneria_software.model.gerarchie;

import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class MenuGerarchie implements Runnable {

    private static final String TITOLO_MENU = "Gestione delle gerarchie";
    private static final String[] VOCI_PRINCIPALI = {
            "Salva gerarchie",
            "Aggiungi gerarchia",
            "Rimuovi gerarchia",
            "Visualizza gerarchie presenti",
            "Modifica gerarchia"
    };

    private final MenuUtil menuPrincipale;
    private final GestoreGerarchie gestoreGerarchie;

    /**
     * Costruttore del Menu Gerarchie.
     */
    public MenuGerarchie(GestoreGerarchie gestoreGerarchie) {
        this.menuPrincipale = new MenuUtil(TITOLO_MENU, VOCI_PRINCIPALI);
        this.gestoreGerarchie = gestoreGerarchie;
    }

    /**
     * Menù per la gestione delle gerarchie.
     */
    @Override
    public void run() {
        int sceltaUtente;
        do {
            sceltaUtente = menuPrincipale.scegli();
            switch (sceltaUtente) {
                case 1:
                    gestoreGerarchie.salvaGerarchie();
                    break;
                case 2:
                    gestoreGerarchie.aggiungiGerarchia();
                    break;
                case 3:
                    gestoreGerarchie.rimuoviGerarchia();
                    break;
                case 4:
                    gestoreGerarchie.stampaGerarchie();
                    break;
                case 5:
                    gestoreGerarchie.modificaGerarchia();
                    break;
                default:
                    break;
            }
        } while (sceltaUtente != 0);
    }
}