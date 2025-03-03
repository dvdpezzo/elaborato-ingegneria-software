package it.ingbs.ingegneria_software.model.gerarchie;

import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class MenuGerarchie implements Runnable {

private static final String[] VOCI_PRINCIPALI = {
            "salva gerarchie",
            "aggiungi gerarchia",
            "rimuovi gerarchia",
            "visualizza gerarchie presenti",
            "modifica gerarchia"
    };

    private final MenuUtil menuPrincipale;

    private final GestoreGerarchie gestoreGerarchie;

    /**
     * Costruttore del GestoreGersrchie
     *
     * @param radici HshMap contenente tutte le gerarchie
     * @param salvataggio file di salvataggio delle gerarchie
     */
    public MenuGerarchie() {
        this.menuPrincipale = new MenuUtil("Gestione delle gerarchie", VOCI_PRINCIPALI);
        this.gestoreGerarchie = new GestoreGerarchie();        
    }

    /**
     * Menù per la gestione delle gerarchie
     */
    @Override
    public void run() {
        int scelta;
        do {
            scelta = menuPrincipale.scegli();
            switch (scelta) {
                case 1: gestoreGerarchie.salvaGerarchie();
                break;
                case 2: gestoreGerarchie.aggiungiGerarchia();
                break;
                case 3: gestoreGerarchie.rimuoviGerarchia();
                break;
                case 4: gestoreGerarchie.stampaGerarchie();
                break;
                case 5: gestoreGerarchie.modificaGerarchia();
                break;
            }
        } while (scelta != 0);
    }

   
}
