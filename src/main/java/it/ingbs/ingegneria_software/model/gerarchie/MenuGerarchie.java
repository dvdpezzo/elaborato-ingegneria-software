package it.ingbs.ingegneria_software.model.gerarchie;

import java.io.File;
import java.util.HashMap;

import it.ingbs.ingegneria_software.gestione_file.GestoreFileGerarchie;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class MenuGerarchie implements Runnable {

private final static String[] VOCI_PRINCIPALI = {
            "salva gerarchie",
            "aggiungi gerarchia",
            "rimuovi gerarchia",
            "visualizza gerarchie presenti",
            "modifica gerarchia"
    };

    private static MenuUtil menuPrincipale;

    private static HashMap<String, Gerarchia> radici;
    private File fileSalvataggio;

    /**
     * Costruttore del GestoreGersrchie
     *
     * @param radici HshMap contenente tutte le gerarchie
     * @param salvataggio file di salvataggio delle gerarchie
     */
    public MenuGerarchie(HashMap <String,Gerarchia> radici, File salvataggio) {
        menuPrincipale = new MenuUtil("Gestione delle gerarchie", VOCI_PRINCIPALI);
        this.radici = radici;
        this.fileSalvataggio = salvataggio;
    }

    /**
     * Menù per la gestione delle gerarchie
     */
    @Override
    public void run() {
        int scelta;
        do {
            switch (scelta = menuPrincipale.scegli()) {
                case 1: GestoreFileGerarchie.salvaAlbero(radici.values(), fileSalvataggio);
                break;
                case 2: GestoreGerarchie.aggiungiGerarchia(radici);
                break;
                case 3: GestoreGerarchie.rimuoviGerarchia(radici);
                break;
                case 4: stampaGerarchie();
                break;
                case 5: GestoreGerarchie.modificaGerarchia(radici);
                break;
            }
        } while (scelta != 0);
    }

    private void stampaGerarchie() {
        for (String g : radici.keySet())
            System.out.println(g + " ");

    }
}
