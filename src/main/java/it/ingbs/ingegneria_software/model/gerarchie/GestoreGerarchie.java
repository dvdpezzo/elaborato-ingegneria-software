package it.ingbs.ingegneria_software.model.gerarchie;

import java.util.HashMap;

import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;
import it.ingbs.ingegneria_software.utilita_generale.UtilityHandler;

public class GestoreGerarchie implements UtilityHandler {
    private static final String[] VOCI_LAVORO = {"aggiungi categoria", "rimuovi categoria","modifica campi categoria", "visualizza Gerarchia"};
    private static final String ERRORE = "ERRORE: si sta tentando di %s";
    private static final String NOME_RADICE = "Nome della radice: ";
    private static final String VUOI_PROSEGUIRE = "Vuoi %s altre Gerarchie? ";
    
    private final HashMap<String, Gerarchia> radici;
    private final GestoreDati gestoreDati;

    public GestoreGerarchie(GestoreDati gestoreDati) {
        this.gestoreDati = gestoreDati;
        this.radici = gestoreDati.getGerarchie();
    }

    public HashMap<String, Gerarchia> getRadici() {
        return radici;
    }

    /**
     * Modifica una gerarchia esistente.
     * Permette di aggiungere o rimuovere categorie e campi.
     */
    public void modificaGerarchia() {
        System.out.println(radici.keySet());
        String nomeGerarchia = InputDati.leggiStringaNonVuota("Nome della gerarchia da modificare: ");
        Gerarchia gerarchia = getGerarchiaByName(nomeGerarchia);
        
        if (gerarchia == null) {
            System.out.println(String.format(ERRORE, "modificare una gerarchia inesistente"));
            return;
        }

        MenuUtil menuLavoro = new MenuUtil("Modifica Gerarchia", VOCI_LAVORO);
        int scelta;
        do {
            scelta = menuLavoro.scegli();
            eseguiAzione(gerarchia, scelta);
        } while (scelta != 0);
    }

    private void eseguiAzione(Gerarchia gerarchia, int scelta) {
        switch (scelta) {
            case 1: gerarchia.aggiungiCategoria(); break;
            case 2: gerarchia.rimuoviCategoria(); break;
            case 3: gerarchia.modificaCampiCategoria(); break;
            case 4: System.out.println(gerarchia.toString()); break;
        }
    }

    public Gerarchia getGerarchiaByName(String nomeGerarchia) {
        Gerarchia gerarchia = radici.get(nomeGerarchia.toUpperCase());
        return gerarchia;
    }

    @Override
    public void view() {
        for (Gerarchia gerarchia : radici.values()) {
            stampaGerarchia(gerarchia.getCategoriaRadice(), 0);
        }
    }
    
    /**
     * Stampa una gerarchia a partire dalla categoria radice.
     *
     * @param categoria la categoria radice
     * @param livello il livello di indentazione
     */
    private void stampaGerarchia(Categoria categoria, int livello) {
        for (int i = 0; i < livello; i++) {
            System.out.print("  ");
        }
        System.out.println(categoria.getNome());
        for (Categoria sottocategoria : categoria.getFigli()) {
            stampaGerarchia(sottocategoria, livello + 1);
        }
    }

    @Override
    public void rimuovi() {
        do {
            System.out.println(radici.keySet());
            String nomeRadice = InputDati.leggiStringaNonVuota(NOME_RADICE);
            
            if (!radici.containsKey(nomeRadice.toUpperCase())) {
                System.out.println(String.format(ERRORE, "rimuovere una radice inesistente"));
                continue;
            }
            
            radici.remove(nomeRadice.toUpperCase());
            System.out.printf("Radice %s rimossa!%n", nomeRadice);
        } while (InputDati.yesOrNo(String.format(VUOI_PROSEGUIRE, "rimuovere")));
    }

    @Override
    public void aggiungi() {
        do {
            String nomeRadice = InputDati.leggiStringaNonVuota(NOME_RADICE);
            String descrizione = InputDati.yesOrNo("Vuoi aggiungere una descrizione?") ? 
                               InputDati.leggiStringaNonVuota("Descrizione della radice: ") : "";
            
            if (radici.containsKey(nomeRadice.toUpperCase())) {
                System.out.println(String.format(ERRORE, "aggiungere una radice omonima"));
                continue;
            }
            
            radici.put(nomeRadice.toUpperCase(), new Gerarchia(nomeRadice, descrizione));
            System.out.println("Radice aggiunta!");
        } while (InputDati.yesOrNo(String.format(VUOI_PROSEGUIRE, "aggiungere")));
    }

    @Override
    public void salva() {
        gestoreDati.setGerarchie(radici);
    }

    
}

