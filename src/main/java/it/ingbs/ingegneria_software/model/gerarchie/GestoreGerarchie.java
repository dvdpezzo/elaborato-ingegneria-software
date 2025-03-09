package it.ingbs.ingegneria_software.model.gerarchie;

import java.util.HashMap;

import it.ingbs.ingegneria_software.Eccezioni.CategoriaNotFoundException;
import it.ingbs.ingegneria_software.Eccezioni.CategoriaOmonimaException;
import it.ingbs.ingegneria_software.Eccezioni.IllegalCampoException;
import it.ingbs.ingegneria_software.Eccezioni.PadreNotFoundException;
import it.ingbs.ingegneria_software.gestione_file.GestoreFileGerarchie;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class GestoreGerarchie {

    private static final String VUOI_AGGIUNGERE_UNA_DESCRIZIONE = "Vuoi aggiungere una descrizione?";
    private static final String ERRORE_RADICE_OMONIMA = "ERRORE: si sta tentando di aggiungere una radice omonima";
    private static final String[] VOCI_LAVORO = {"aggiungi categoria", "rimuovi categoria", "sposta categoria", "aggiungi campi a Categoria", "rimuovi campi a Categoria", "visualizza Gerarchia"};
    private static final String NOME_DELLA_RADICE = "Nome della radice: ";
    private static final String DESCRIZIONE_DELLA_RADICE = "Descrizione della radice: ";
    private static final String VUOI_AGGIUNGERE_ALTRE_GERARCHIE = "Vuoi aggiungere altre Gerarchie? ";
    private static final String ERRORE_RIMOZIONE_RADICE_INESISTENTE = "ERRORE: si sta tentando di rimuovere una radice inesistente";
    private static final String VUOI_RIMUOVERE_ALTRE_GERARCHIE = "Vuoi rimuovere altre Gerarchie? ";
    private static final String QUALE_GERARCHIA_VUOI_MODIFICARE = "Quale gerarchia vuoi modificare? ";
    private static final String ERRORE_MODIFICA_GERARCHIA_INESISTENTE = "ERRORE: si sta tentando di modificare una gerarchia inesistente";
    private static final String NOME_CATEGORIA = "Nome Categoria: ";
    private static final String DESCRIZIONE_CATEGORIA = "Descrizione Categoria: ";
    private static final String CHI_E_IL_PADRE = "A quale categoria desideri aggiungerla? ";
    private static final String CATEGORIA_DA_ELIMINARE = "Nome Categoria da eliminare: ";
    private static final String CATEGORIA_DA_MODIFICARE = "Nome Categoria che si vuole modificare: ";
    private static final String NOME_DEL_CAMPO = "Nome del campo: ";
    private static final String OBBLIGATORIO = "È obbligatorio? ";
    private static final String RADICE_AGGIUNTA = "Radice aggiunta!";
    private static final String RADICE_S_RIMOSSA = "Radice %s rimossa!";
    private static final String TITOLO_MENU_MODIFICA_GERARCHIA = "Cosa desideri fare?";
    private static final String CATEGORIA_S_AGGIUNTA = "Categoria %s aggiunta!";
    private static final String CATEGORIA_S_RIMOSSA = "Categoria %s rimossa!";
    private static final String QUALE_CATEGORIA_VUOI_SPOSTARE = "Quale categoria vuoi spostare? ";
    private static final String NUOVA_CATEGORIA_PADRE = "Nuova categoria padre: ";
    private static final String CATEGORIA_S_ORA_E_UNA_SOTTO_CATEGORIA_DI_S = "Categoria %s ora è una sotto-categoria di %s";
    private static final String CAMPO_S_CORRETTAMENTE_AGGIUNTO = "Campo %s correttamente aggiunto!";
    private static final String SCELTA_ELIMINAZ_CAMPI = "1- Eliminare tutti i campi nativi\n2- Eliminare un campo nativo specifico\n";
    private static final String CAMPI_NATIVI_ELIMINATI = "Campi nativi eliminati";
    private static final String CAMPO_NATIVO_ELIMINATO = "Campo nativo eliminato";
    private static final String VUOI_AGGIUNGERE_ALTRE_CATEGORIE = "Vuoi aggiungere altre categorie? ";
    private static final String VUOI_AGGIUNGERE_ALTRI_CAMPI = "Vuoi aggiungere altri campi? ";
    private static final String VUOI_ELIMINARE_ALTRI_CAMPI = "Vuoi eliminare altri campi? ";

    private final HashMap<String, Gerarchia> radici;

    public GestoreGerarchie() {
        this.radici = GestoreFileGerarchie.recuperaAlbero();
    }

    /**
     * Aggiunge le gerarchie al programma.
     */
    protected void aggiungiGerarchia() {
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

    /**
     * Rimuove le gerarchie dal programma.
     */
    protected void rimuoviGerarchia() {
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

    /**
     * Menù per gestire le categorie.
     */
    public void modificaGerarchia() {
        String nomeGerarchia;
        MenuUtil menuLavoro = new MenuUtil(TITOLO_MENU_MODIFICA_GERARCHIA, VOCI_LAVORO);
        int scelta;

        System.out.println(radici.keySet());
        nomeGerarchia = InputDati.leggiStringaNonVuota(QUALE_GERARCHIA_VUOI_MODIFICARE);

        if (radici.containsKey(nomeGerarchia.toUpperCase())) {
            do {
                scelta = menuLavoro.scegli();
                switch (scelta) {
                    case 1:
                        aggiungiCategoria(radici.get(nomeGerarchia.toUpperCase()));
                        break;
                    case 2:
                        rimuoviCategoria(radici.get(nomeGerarchia.toUpperCase()));
                        break;
                    case 3:
                        spostaCategoria(radici.get(nomeGerarchia.toUpperCase()));
                        break;
                    case 4:
                        aggiungiCampi(radici.get(nomeGerarchia.toUpperCase()));
                        break;
                    case 5:
                        rimuoviCampi(radici.get(nomeGerarchia.toUpperCase()));
                        break;
                    case 6:
                        System.out.println(radici.get(nomeGerarchia.toUpperCase()));
                        break;
                }

            } while (scelta != 0);
        } else {
            System.out.println(ERRORE_MODIFICA_GERARCHIA_INESISTENTE);
        }
    }

    /**
     * Aggiunge una categoria alla gerarchia.
     *
     * @param gerarchia la gerarchia a cui aggiungere la categoria
     */
    private void aggiungiCategoria(Gerarchia gerarchia) {
        do {
            System.out.println(gerarchia.toString());
            String nomeCategoria, descrizioneCategoria;
            nomeCategoria = InputDati.leggiStringaNonVuota(NOME_CATEGORIA);
            boolean aggiungiDescrizione = InputDati.yesOrNo(VUOI_AGGIUNGERE_UNA_DESCRIZIONE);
            if (aggiungiDescrizione) {
                descrizioneCategoria = InputDati.leggiStringaNonVuota(DESCRIZIONE_CATEGORIA);
            } else {
                descrizioneCategoria = " ";
            }
            String nomePadre = InputDati.leggiStringaNonVuota(CHI_E_IL_PADRE);
            try {
                gerarchia.addSottocategoria(nomeCategoria.toUpperCase(), descrizioneCategoria, nomePadre);
                gerarchia.getCategoria(nomeCategoria.toUpperCase()).addCampoNativo(" "); // Aggiunge un campo nativo vuoto
                System.out.printf((CATEGORIA_S_AGGIUNTA) + "%n", nomeCategoria);
            } catch (PadreNotFoundException | CategoriaOmonimaException | CategoriaNotFoundException | IllegalCampoException e) {
                System.out.println(e.getMessage());
            }
        } while (InputDati.yesOrNo(VUOI_AGGIUNGERE_ALTRE_CATEGORIE));
    }

    /**
     * Rimuove una categoria dalla gerarchia.
     *
     * @param gerarchia la gerarchia da cui rimuovere la categoria
     */
    private void rimuoviCategoria(Gerarchia gerarchia) {
        System.out.println(gerarchia.toString());
        String nomeCategoria = InputDati.leggiStringaNonVuota(CATEGORIA_DA_ELIMINARE);
        try {
            gerarchia.rimuoviCategoria(nomeCategoria);
            System.out.printf((CATEGORIA_S_RIMOSSA) + "%n", nomeCategoria);
        } catch (CategoriaNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Sposta una categoria da un nodo a un altro, cambiando il riferimento al suo padre.
     *
     * @param gerarchia la gerarchia contenente la categoria che si vuole spostare
     */
    private void spostaCategoria(Gerarchia gerarchia) {
        System.out.println(gerarchia.toString());
        String nomeCategoria, nomeNuovoPadre;
        nomeCategoria = InputDati.leggiStringaNonVuota(QUALE_CATEGORIA_VUOI_SPOSTARE);
        nomeNuovoPadre = InputDati.leggiStringaNonVuota(NUOVA_CATEGORIA_PADRE);
        try {
            gerarchia.spostaCategoria(nomeCategoria, nomeNuovoPadre);
            System.out.printf((CATEGORIA_S_ORA_E_UNA_SOTTO_CATEGORIA_DI_S) + "%n", nomeCategoria, nomeNuovoPadre);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Aggiunge campi a una categoria della gerarchia.
     *
     * @param gerarchia la gerarchia contenente la categoria a cui aggiungere i campi
     */
    private void aggiungiCampi(Gerarchia gerarchia) {
        String nomeCategoria, nomeCampo;
        System.out.println(gerarchia.toString());
        nomeCategoria = InputDati.leggiStringaNonVuota(CATEGORIA_DA_MODIFICARE);
        do {
            nomeCampo = InputDati.leggiStringaNonVuota(NOME_DEL_CAMPO);
            try {
                gerarchia.getCategoria(nomeCategoria).addCampoNativo(nomeCampo);
                System.out.printf((CAMPO_S_CORRETTAMENTE_AGGIUNTO) + "%n", nomeCampo);
            } catch (IllegalCampoException | CategoriaNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } while (InputDati.yesOrNo(VUOI_AGGIUNGERE_ALTRI_CAMPI));
    }

    /**
     * Rimuove campi da una categoria della gerarchia.
     *
     * @param gerarchia la gerarchia contenente la categoria da cui rimuovere i campi
     */
    private void rimuoviCampi(Gerarchia gerarchia) {
        String nomeCategoria, nomeCampo;
        System.out.println(gerarchia.toString());
        int scelta = InputDati.leggiIntero(SCELTA_ELIMINAZ_CAMPI, 1, 2);
        nomeCategoria = InputDati.leggiStringaNonVuota(CATEGORIA_DA_MODIFICARE);
        try {
            if (scelta == 1) {
                gerarchia.getCategoria(nomeCategoria).eliminaCampiNativi();
                gerarchia.getCategoria(nomeCategoria).addCampoNativo(" "); // Aggiunge un campo vuoto
                System.out.println(CAMPI_NATIVI_ELIMINATI);
            } else {
                System.out.println(gerarchia.getCategoria(nomeCategoria).stampaCampiNativi());
                do {
                    nomeCampo = InputDati.leggiStringaNonVuota(NOME_DEL_CAMPO);
                    gerarchia.getCategoria(nomeCategoria).eliminaCampoNativo(nomeCampo);
                    System.out.println(CAMPO_NATIVO_ELIMINATO);
                } while (InputDati.yesOrNo(VUOI_ELIMINARE_ALTRI_CAMPI));
                if (gerarchia.getCategoria(nomeCategoria).getCampiNativi().isEmpty()) {
                    gerarchia.getCategoria(nomeCategoria).addCampoNativo(" "); // Aggiunge un campo vuoto se non ci sono più campi
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Restituisce la gerarchia con il nome specificato.
     *
     * @param nomeGerarchia il nome della gerarchia
     * @return la gerarchia con il nome specificato
     */
    public Gerarchia getGerarchia(String nomeGerarchia) {
        return radici.get(nomeGerarchia.toUpperCase());
    }

    /**
     * Stampa tutte le gerarchie.
     */
    public void stampaGerarchie() {
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

    /**
     * Salva tutte le gerarchie su file.
     */
    public void salvaGerarchie() {
        GestoreFileGerarchie.salvaAlbero(radici.values());
    }

    /**
     * Restituisce la mappa delle radici.
     *
     * @return la mappa delle radici
     */
    public HashMap<String, Gerarchia> getRadici() {
        return radici;
    }
}
