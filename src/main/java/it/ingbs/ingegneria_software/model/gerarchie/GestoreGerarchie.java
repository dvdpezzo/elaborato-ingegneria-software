package it.ingbs.ingegneria_software.model.gerarchie;


import java.util.*;

import it.ingbs.ingegneria_software.Eccezioni.*;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class GestoreGerarchie {

    private static final String ERRORE_RADICE_OMONIMA = "ERRORE: si sta tentanto di aggiungere una radice omonima";
    private final static String[] VOCI_LAVORO = {"aggiungi categoria", "rimuovi categoria", "sposta categoria", "aggiungi campi a Categoria", "rimuovi campi a Categoria", "visualizza Gerarchia"};
    private static final String NOME_DELLA_RADICE = "Nome della radice: ";
    private static final String DESCRIZIONE_DELLA_RADICE = "Descrizione della radice: ";
    private static final String VUOI_AGGIUNGERE_ALTRE_GERARCHIE = "vuoi aggiungere altre Gerarchie? ";
    private static final String ERRORE_RIMOZIONE_RADICE_INESISTENTE = "ERRORE: si sta tentanto di rimuovere una radice inesistente";
    private static final String VUOI_RIMUOVERE_ALTRE_GERARCHIE = "vuoi rimuovere altre Gerarchie? ";
    private static final String QUALE_GERARCHIA_VUOI_MODIFICARE = "quale gerarchia vuoi modificare? ";
    private static final String ERRORE_MODIFICA_GERARCHIA_INESISTENTE = "ERRORE: si sta tentanto di modificare una gerarchia inesistente ";
    private static final String NOME_CATEGORIA = "nome Categoria: ";
    private static final String DESCRIZIONE_CATEGORIA = "descrizione Categoria: ";
    private static final String CHI_E_IL_PADRE = "a quale categoria desideri aggiungerla? ";
    private static final String CATEGORIA_DA_ELIMINARE = "nome Categoria da eliminare: ";
    private static final String CATEGORIA_DA_MODIFICARE = "nome Categoria che si vuole modificare: ";
    private static final String NOME_DEL_CAMPO = "Nome del campo: ";
    private static final String OBBLIGATORIO = "E' obbligatorio? ";
    private static final String RADICE_AGGIUNTA = "Radice aggiunta!";
    private static final String RADICE_S_RIMOSSA = "radice %s rimossa!";
    private static final String TITOLO_MENU_MODIFICA_GERARCHIA = "cosa desideri fare?";
    private static final String CATEGORIA_S_AGGIUNTA = "categoria %s aggiunta!";
    private static final String CATEGORIA_S_RIMOSSA = "categoria %s rimossa!";
    private static final String QUALE_CATEGORIA_VUOI_SPOSTARE = "quale categoria vuoi spostare? ";
    private static final String NUOVA_CATEGORIA_PADRE = "nuova categoria padre: ";
    private static final String CATEGORIA_S_ORA_E_UNA_SOTTO_CATEGORIA_DI_S = "categoria %s ora e' una sotto-categoria di %s";
    private static final String CAMPO_S_CORRETTAMENTE_AGGIUNTO = "campo %s correttamente aggiunto!";
    private static final String SCELTA_ELIMINAZ_CAMPI = "1- Eliminare tutti i campi nativi\n2- Eliminare un campo nativo specifico\n";
    private static final String CAMPI_NATIVI_ELIMINATI = "campi nativi eliminati";
    private static final String CAMPO_NATIVO_ELIMINATO = "campo nativo eliminato";
    private static final String VUOI_AGGIUNGERE_ALTRE_CATEGORIE = "vuoi aggiungere altre categorie? ";
    private static final String VUOI_AGGIUNGERE_ALTRI_CAMPI = "vuoi aggiungere altri Campi? ";
    private static final String VUOI_ELIMINARE_ALTRI_CAMPI = "vuoi eliminare altri campi? ";

    /**
     * Aggiunge le gerarchie al programma
     *
     * @param radici HashMap di tutte le radici presenti. HashMap fatta da copie nome della gerarchia-gerarchia
     */
    protected static void aggiungiGerarchia(HashMap<String, Gerarchia> radici) {
        Gerarchia daAgg;
        String nome;
        String desc;

        do {
            nome = InputDati.leggiStringaNonVuota(NOME_DELLA_RADICE);
            desc = InputDati.leggiStringaNonVuota(DESCRIZIONE_DELLA_RADICE);
            daAgg = new Gerarchia(nome, desc);

            if (radici.containsKey(nome.toUpperCase()))
                System.out.println(ERRORE_RADICE_OMONIMA);
            else {
                radici.put(nome.toUpperCase(), daAgg);
                System.out.println(RADICE_AGGIUNTA);
            }

        } while (InputDati.yesOrNo(VUOI_AGGIUNGERE_ALTRE_GERARCHIE));
    }

    protected static void rimuoviGerarchia(HashMap<String, Gerarchia> radici) {
        String daRimuovere;
        do {
            System.out.println(radici.keySet());
            daRimuovere = InputDati.leggiStringaNonVuota(NOME_DELLA_RADICE);
            if (radici.containsKey(daRimuovere.toUpperCase())) {
                radici.remove(daRimuovere.toUpperCase());
                System.out.printf((RADICE_S_RIMOSSA) + "%n", daRimuovere);
            } else System.out.println(ERRORE_RIMOZIONE_RADICE_INESISTENTE);

        } while (InputDati.yesOrNo(VUOI_RIMUOVERE_ALTRE_GERARCHIE));

    }

    /**
     * Menù per gestire le categorie
     * @param radici HashMap contenente tutte le radici
     */

    public static void modificaGerarchia(HashMap<String, Gerarchia> radici) {
        String nomeGer;
        MenuUtil menuLavoro = new MenuUtil(TITOLO_MENU_MODIFICA_GERARCHIA, VOCI_LAVORO);
        int scelta;

        System.out.println(radici.keySet());
        nomeGer = InputDati.leggiStringaNonVuota(QUALE_GERARCHIA_VUOI_MODIFICARE);

        if (radici.containsKey(nomeGer.toUpperCase())) {
            do {
                switch (scelta = menuLavoro.scegli()) {
                    case 1 -> addCategoria(radici.get(nomeGer.toUpperCase()));
                    case 2 -> rimuoviCategoria(radici.get(nomeGer.toUpperCase()));
                    case 3 -> spostaCategoria(radici.get(nomeGer.toUpperCase()));
                    case 4 -> aggiungiCampi(radici.get(nomeGer.toUpperCase()));
                    case 5 -> rimuoviCampi(radici.get(nomeGer.toUpperCase()));
                    case 6 -> System.out.println(radici.get(nomeGer.toUpperCase()));
                    default -> {
                    }
                }

            } while (scelta != 0);
        } else System.out.println(ERRORE_MODIFICA_GERARCHIA_INESISTENTE);
    }

    private static void addCategoria(Gerarchia radice) {
        do {
            System.out.println(radice.toString());
            String nome, desc;
            nome = InputDati.leggiStringaNonVuota(NOME_CATEGORIA);
            desc = InputDati.leggiStringaNonVuota(DESCRIZIONE_CATEGORIA);
            String padre = InputDati.leggiStringaNonVuota(CHI_E_IL_PADRE);
            try {
                radice.addSottocategoria(nome, desc, padre);
                System.out.printf((CATEGORIA_S_AGGIUNTA) + "%n", nome);
            } catch (PadreNotFoundException | CategoriaOmonimaException e) {
                System.out.println(e.getMessage());
            }
        } while (InputDati.yesOrNo(VUOI_AGGIUNGERE_ALTRE_CATEGORIE));
    }

    private static void rimuoviCategoria(Gerarchia radice) {
        System.out.println(radice.toString());
        String nome = InputDati.leggiStringaNonVuota(CATEGORIA_DA_ELIMINARE);
        try {
            radice.rimuoviCategoria(nome); //da gestire se la categoria non c'è
            System.out.printf((CATEGORIA_S_RIMOSSA) + "%n", nome);
        } catch (CategoriaNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Sposta una categoria da un nodo a un altro, cambiando il riferimento al suo padre
     * @param radice gerarchia contente la categoria che si vuole spostare
     */
    private static void spostaCategoria(Gerarchia radice) {
        System.out.println(radice.toString());
        String nome, padre;
        nome = InputDati.leggiStringaNonVuota(QUALE_CATEGORIA_VUOI_SPOSTARE);
        padre = InputDati.leggiStringaNonVuota(NUOVA_CATEGORIA_PADRE);
        try {
            radice.spostaCategoria(nome, padre);
            System.out.printf((CATEGORIA_S_ORA_E_UNA_SOTTO_CATEGORIA_DI_S) + "%n", nome, padre);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void aggiungiCampi(Gerarchia radice) {
        String nomeCat, nomeCampo;
        boolean obb;
        System.out.println(radice.toString());
        nomeCat = InputDati.leggiStringaNonVuota(CATEGORIA_DA_MODIFICARE);
        do {
            nomeCampo = InputDati.leggiStringaNonVuota(NOME_DEL_CAMPO);
            obb = InputDati.yesOrNo(OBBLIGATORIO);
            try {
                radice.getCategoria(nomeCat).addCampoNativo(nomeCampo, obb);
                System.out.printf((CAMPO_S_CORRETTAMENTE_AGGIUNTO) + "%n", nomeCampo);
            } catch (IllegalCampoException | CategoriaNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } while (InputDati.yesOrNo(VUOI_AGGIUNGERE_ALTRI_CAMPI));

    }

    private static void rimuoviCampi(Gerarchia radice) {
        String nomeCat, nomeCampo;
        System.out.println(radice.toString());
        int scelta = InputDati.leggiIntero(SCELTA_ELIMINAZ_CAMPI, 1, 2);
        nomeCat = InputDati.leggiStringaNonVuota(CATEGORIA_DA_MODIFICARE);
        try {
            if (scelta == 1) {
                radice.getCategoria(nomeCat).eliminaCampiNativi();
                System.out.println(CAMPI_NATIVI_ELIMINATI);
            } else {
                System.out.println(radice.getCategoria(nomeCat).stampaCampiNativi());
                do {
                    nomeCampo = InputDati.leggiStringaNonVuota(NOME_DEL_CAMPO);
                    radice.getCategoria(nomeCat).eliminaCampoNativo(nomeCampo);
                    System.out.println(CAMPO_NATIVO_ELIMINATO);
                } while (InputDati.yesOrNo(VUOI_ELIMINARE_ALTRI_CAMPI));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
}
