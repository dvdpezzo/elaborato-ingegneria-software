package it.ingbs.ingegneria_software.model.gerarchie;

import java.util.HashMap;

import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;
import it.ingbs.ingegneria_software.utilita_generale.UtilityHandler;

public class GestoreGerarchie implements UtilityHandler {

    private static final String VUOI_AGGIUNGERE_UNA_DESCRIZIONE = "Vuoi aggiungere una descrizione?";
    private static final String ERRORE_RADICE_OMONIMA = "ERRORE: si sta tentando di aggiungere una radice omonima";
    private static final String[] VOCI_LAVORO = {"aggiungi categoria", "rimuovi categoria","modifica categoria", "visualizza Gerarchia"};
    private static final String NOME_DELLA_RADICE = "Nome della radice: ";
    private static final String DESCRIZIONE_DELLA_RADICE = "Descrizione della radice: ";
    private static final String VUOI_AGGIUNGERE_ALTRE_GERARCHIE = "Vuoi aggiungere altre Gerarchie? ";
    private static final String ERRORE_RIMOZIONE_RADICE_INESISTENTE = "ERRORE: si sta tentando di rimuovere una radice inesistente";
    private static final String VUOI_RIMUOVERE_ALTRE_GERARCHIE = "Vuoi rimuovere altre Gerarchie? ";
    private static final String QUALE_GERARCHIA_VUOI_MODIFICARE = "Quale gerarchia vuoi modificare? ";
    private static final String ERRORE_MODIFICA_GERARCHIA_INESISTENTE = "ERRORE: si sta tentando di modificare una gerarchia inesistente";
    private static final String RADICE_AGGIUNTA = "Radice aggiunta!";
    private static final String RADICE_S_RIMOSSA = "Radice %s rimossa!";
    private static final String TITOLO_MENU_MODIFICA_GERARCHIA = "Cosa desideri fare?";
    
    private final HashMap<String, Gerarchia> radici;
    private final GestoreDati gestoreDati;

    public GestoreGerarchie(GestoreDati gestoreDati) {
        this.gestoreDati = gestoreDati;
        this.radici = gestoreDati.getGerarchie();
    }

    public HashMap<String, Gerarchia> getRadici() {
        return radici;
    }

    public void modificaGerarchia() {
        Gerarchia gerarchia;
        MenuUtil menuLavoro = new MenuUtil(TITOLO_MENU_MODIFICA_GERARCHIA, VOCI_LAVORO);
        int scelta;

        System.out.println(radici.keySet());
        gerarchia = getGerarchiaByName();

        if (gerarchia != null) {
            do {
                scelta = menuLavoro.scegli();
                switch (scelta) {
                    case 1:
                        gerarchia.aggiungi();
                        break;
                    case 2:
                        gerarchia.rimuovi();
                        break;
                    case 3:
                        gerarchia.modificaCampiCategoria();                      
                        break;
                    case 4:
                        gerarchia.view();
                        break;
                }

            } while (scelta != 0);
        } else {
            System.out.println(ERRORE_MODIFICA_GERARCHIA_INESISTENTE);
        }
    }

    @Override
    public void view() {
        for (Gerarchia gerarchia : radici.values()) {
            gerarchia.view();
        }
    }

    @Override
    public void rimuovi() {
        String nomeRadice;
        do {
            System.out.println(radici.keySet());
            nomeRadice = InputDati.leggiStringaNonVuota(NOME_DELLA_RADICE);
            if (radici.containsKey(nomeRadice.toUpperCase())) {
                radici.remove(nomeRadice.toUpperCase());
                System.out.printf((RADICE_S_RIMOSSA) + "%n", nomeRadice);
            } else {
                System.out.println(ERRORE_RIMOZIONE_RADICE_INESISTENTE);
            }

        } while (InputDati.yesOrNo(VUOI_RIMUOVERE_ALTRE_GERARCHIE));
    }

    @Override
    public void aggiungi() {
        Gerarchia nuovaGerarchia;
        String nomeRadice;
        String descrizioneRadice;

        do {
            nomeRadice = InputDati.leggiStringaNonVuota(NOME_DELLA_RADICE);
            boolean aggiungiDescrizione = InputDati.yesOrNo(VUOI_AGGIUNGERE_UNA_DESCRIZIONE);
            if (aggiungiDescrizione) {
                descrizioneRadice = InputDati.leggiStringaNonVuota(DESCRIZIONE_DELLA_RADICE);
            } else {
                descrizioneRadice = "";
            }
            nuovaGerarchia = new Gerarchia(nomeRadice, descrizioneRadice);

            if (radici.containsKey(nomeRadice.toUpperCase())) {
                System.out.println(ERRORE_RADICE_OMONIMA);
            } else {
                radici.put(nomeRadice.toUpperCase(), nuovaGerarchia);
                System.out.println(RADICE_AGGIUNTA);
            }

        } while (InputDati.yesOrNo(VUOI_AGGIUNGERE_ALTRE_GERARCHIE));
    }

    @Override
    public void salva() {
        gestoreDati.setGerarchie(radici);
    }

    private Gerarchia getGerarchiaByName() {
        String nomeGerarchia;
        nomeGerarchia = InputDati.leggiStringaNonVuota(QUALE_GERARCHIA_VUOI_MODIFICARE);
        Gerarchia gerarchia = radici.get(nomeGerarchia.toUpperCase());
        return gerarchia;
    }
    
}

