package it.ingbs.ingegneria_software.model.gerarchie;

import java.io.File;
import java.util.HashMap;

import it.ingbs.ingegneria_software.gestione_file.GestoreFileGerarchie;
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

    private final HashMap<String, Gerarchia> radici;
    private final File fileSalvataggio;

    /**
     * Costruttore del GestoreGersrchie
     *
     * @param radici HshMap contenente tutte le gerarchie
     * @param salvataggio file di salvataggio delle gerarchie
     */
    public MenuGerarchie(HashMap <String,Gerarchia> radici, File salvataggio) {
        this.menuPrincipale = new MenuUtil("Gestione delle gerarchie", VOCI_PRINCIPALI);
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
            scelta = menuPrincipale.scegli();
            switch (scelta) {
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

    public void stampaGerarchie() {
        for (Gerarchia gerarchia : radici.values()) {
            stampaGerarchia(gerarchia.getCategoriaRadice(), 0);
        }
    }

    private void stampaGerarchia(Categoria categoria, int livello) {
        // Stampa l'indentazione in base al livello
        for (int i = 0; i < livello; i++) {
            System.out.print("  ");
        }
        // Stampa il nome della categoria
        System.out.println(categoria.getNome());
        // Stampa le sottocategorie ricorsivamente
        for (Categoria sottocategoria : categoria.getFigli()) {
            stampaGerarchia(sottocategoria, livello + 1);
        }
    }
}
