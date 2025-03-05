package it.ingbs.ingegneria_software.gestione_file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.model.gerarchie.Gerarchia;

/**
 * Classe che gestisce la lettura e scrittura delle gerarchie su file.
 */
public class GestoreFileGerarchie {
    
    private static final HashMap<String, Gerarchia> ALBERO_GERARCHIA = new HashMap<>();
    private static final File FILE_SALVATAGGIO = new File("src\\Data_File\\elencoGerarchie.txt");
    private static final String ERRORE_FILE_NON_TROVATO = "File non trovato!";
    private static final String ERRORE_LETTURA_FILE = "Errore di lettura del file";
    private static final String ERRORE_RECUPERO_ALBERO = "Non è stato possibile recuperare l'albero. Errore di lettura: la sintassi del file potrebbe non essere corretta";
    private static final String SALVATAGGIO_RIUSCITO = "Salvataggio riuscito";
    private static final String SPAZIO = ", ";
    private static final String PREFISSO_GERARCHIA = "/";
    private static final String SEPARATORE_CAMPI = ">, <";
    private static final String SEPARATORE_PADRE = "/";
    private static final String SEPARATORE_CAMPI_NATIVI = " #";

    /**
     * Recupera gli alberi di tutte le gerarchie da un file.
     *
     * @return l'albero sotto-forma di HashMap, nel quale ogni elemento è rappresentato dalla coppia
     * nome della categoria-categoria
     */
    public static HashMap<String, Gerarchia> recuperaAlbero() {
        try (BufferedReader fileSalvato = new BufferedReader(new FileReader(FILE_SALVATAGGIO))) {
            String linea;
            while ((linea = fileSalvato.readLine()) != null) {
                caricaCategoria(linea);
            }
        } catch (FileNotFoundException e) {
            System.out.println(ERRORE_FILE_NON_TROVATO);
        } catch (IOException e) {
            System.out.println(ERRORE_LETTURA_FILE);
        }
        return ALBERO_GERARCHIA;
    }

    /**
     * Metodo di supporto al metodo recuperaAlbero, permette di caricare la singola categoria.
     *
     * @param linea la linea del file dell'albero
     */
    private static void caricaCategoria(String linea) {
        linea = linea.substring(1, linea.length() - 1);
        ArrayList<String> elementi = new ArrayList<>(Arrays.stream(linea.split(SEPARATORE_CAMPI))
                .collect(Collectors.toList()));

        try {
            if (elementi.get(0).startsWith(PREFISSO_GERARCHIA)) {
                String nome = elementi.get(0).substring(1);
                ALBERO_GERARCHIA.put(nome, new Gerarchia(nome.toUpperCase(), elementi.get(1)));
            } else {
                Categoria nuovaCategoria = new Categoria(elementi.get(0), elementi.get(1));
                for (String campo : recuperaCampi(elementi.get(3))) {
                    nuovaCategoria.addCampoNativo(campo.startsWith("!") ? campo.substring(1) : campo, campo.startsWith("!"));
                }
                ArrayList<String> padre = new ArrayList<>(Arrays.stream(elementi.get(2).split(SEPARATORE_PADRE))
                        .collect(Collectors.toList()));
                ALBERO_GERARCHIA.get(padre.get(0)).addSottocategoria(nuovaCategoria, padre.get(1));
            }
        } catch (Exception e) {
            System.out.println(ERRORE_RECUPERO_ALBERO);
        }
    }

    /**
     * Metodo di supporto per recuperare i campi nativi di una categoria.
     *
     * @param lista la lista dei campi nativi
     * @return una lista di campi nativi
     */
    private static ArrayList<String> recuperaCampi(String lista) {
        ArrayList<String> campi = new ArrayList<>(Arrays.stream(lista.split(SEPARATORE_CAMPI_NATIVI))
                .collect(Collectors.toList()));
        campi.set(0, campi.get(0).substring(1));
        return campi;
    }

    /**
     * Salva gli alberi di tutte le gerarchie su file.
     *
     * @param albero collezione di tutte le gerarchie presenti
     */
    public static void salvaAlbero(Collection<Gerarchia> albero) {
        try (FileWriter out = new FileWriter(FILE_SALVATAGGIO)) {
            for (Gerarchia gerarchia : albero) {
                Categoria radice = gerarchia.getCategoriaRadice();
                out.append(scriviRadice(radice)).append("\n");
                System.out.println(SALVATAGGIO_RIUSCITO );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo di supporto al metodo salvaAlbero, stampa l'albero della gerarchia a partire dalla categoria radice
     * passata come parametro.
     *
     * @param radice categoria della radice
     * @return stringa con l'albero della gerarchia che ha come radice la categoria passata come parametro
     */
    private static String scriviRadice(Categoria radice) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("</%s>", radice.getNome().toUpperCase()));
        sb.append(", ");
        sb.append(String.format("<%s>", radice.getDescrizione()));
        sb.append(", ");
        sb.append(scriviCampi(radice));
        for (Categoria figlio : radice.getFigli()) {
            sb.append("\n");
            sb.append(scriviCategoria(figlio, radice));
        }
        return sb.toString();
    }

    /**
     * Metodo di supporto a scriviRadice, restituisce una stringa con una descrizione della categoria passata.
     *
     * @param categoria categoria per la quale si vuole una descrizione
     * @param radice radice dell'albero della categoria selezionata
     * @return restituisce una stringa con una descrizione della categoria passata
     */
    private static String scriviCategoria(Categoria categoria, Categoria radice) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<%s>", categoria.getNome().toUpperCase()));
        sb.append(SPAZIO);
        sb.append(String.format("<%s>", categoria.getDescrizione()));
        sb.append(SPAZIO);
        sb.append(String.format("<%s/%s>", radice.getNome(), categoria.getPadre().getNome()));
        sb.append(SPAZIO);
        sb.append(scriviCampi(categoria));
        for (Categoria figlio : categoria.getFigli()) {
            sb.append("\n");
            sb.append(scriviCategoria(figlio, radice));
        }
        return sb.toString();
    }

    /**
     * Metodo di supporto a scriviRadice e scriviCategoria, restituisce una stringa con i campi della categoria passata.
     *
     * @param categoria categoria della quale si vogliono i campi
     * @return restituisce una stringa con i campi della categoria passata
     */
    private static String scriviCampi(Categoria categoria) {
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        for (Object campo : categoria.getCampiNativi()) {
            sb.append(String.format("%s ", campo.toString()));
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(">");
        return sb.toString();
    }
}