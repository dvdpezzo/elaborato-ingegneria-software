package it.ingbs.ingegneria_software.gestione_file;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.model.gerarchie.Gerarchia;

public class GestoreFileGerarchie {

    private static final HashMap<String, Gerarchia> albero = new HashMap<>();

    /**
     * Recupera gli alberi di tutte le gerarchie da un file
     *
     * @param fileAlbero file di testo contenente le gerarchie
     * @return l'albero sotto-forma di HashMap, nel quale ogni elemento è rappresentato dalla copia
     * nome della categoria-categoria
     *
     * @throws FileNotFoundException quando non trova il file di caricamento
     * @throws IOException quando si verifica un errore di lettura del file
     */
    public static HashMap<String, Gerarchia> recuperaAlbero(File fileAlbero) throws Exception {

        try (FileReader f = new FileReader(fileAlbero)) {
            BufferedReader fileSalvato = new BufferedReader(f);
            String line;
            while ((line = fileSalvato.readLine()) != null)
                caricaCategoria(line);

        } catch (FileNotFoundException e) {
            System.out.println("File non trovato!");
        } catch (IOException e) {
            System.out.println("Errore di lettura del file");
        }
        return albero;
    }

    /**
     * Metodo di supporto al metodo recuperaAlbero, permette di caricare la singola categoria
     *
     * @param line numero della linea del file dell'albero
     * @throws Exception quando non è stato possibile recuperare l'albero o c'è un errore di lettura
     */
    private static void caricaCategoria(String line) throws Exception {

        line = line.substring(1, line.length() - 1);
        ArrayList<String> elementi = new ArrayList<>(( Arrays.stream(line.split(">, <"))).toList());

        try {
            if (elementi.get(0).startsWith("/")) {
                String nome = elementi.get(0).substring(1);
                albero.put(nome, new Gerarchia(nome.toUpperCase(), elementi.get(1)));
            } else {
                Categoria nuova = new Categoria(elementi.get(0), elementi.get(1));
                for (String s : recuperaCampi(elementi.get(3)))
                    nuova.addCampoNativo(s.startsWith("!") ? s.substring(1) : s, s.startsWith("!"));
                ArrayList<String> padre = new ArrayList<>(Arrays.stream(elementi.get(2).split("/")).toList());
                albero.get(padre.get(0)).addSottocategoria(nuova, padre.get(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Non è stato possibile recuperare l'albero. " +
                    "Errore di lettura: la sintassi del file potrebbe non essere corretta");
        }

    }

    private static ArrayList<String> recuperaCampi(String lista) {
        ArrayList<String> campi = new ArrayList<>(Arrays.stream(lista.split(" #")).toList());
        campi.set(0, campi.get(0).substring(1));
        return campi;
    }

    /**
     * Salva gli alberi di tutte gerarchie su file
     *
     * @param albero collezione di tutte le gerarchie presenti
     * @param file file sul quale vengono salvati tutti gli alberi
     */
    public static void salvaAlbero(Collection<Gerarchia> albero, File file) {
        try (FileWriter out = new FileWriter(file)) {
            for (Gerarchia gerarchia : albero) {
                Categoria radice = gerarchia.getCategoriaRadice();
                out.append(scriviRadice(radice)).append("\n");
                System.out.println("salvataggio riuscito");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**Metodo di supporto al metodo salvaAlbero, stampa l'albero della gerarchia a partire dalla categoria radice
     * passata come parametro
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
     * Metodo di supporto a scriviRadice, restituisce una stringa con una descrizione della categoria passata
     *
     * @param categoria categoria per la quale si vuole una descrizione
     * @param radice radice dell'albero della categoria selezionata
     * @return restituisce una stringa con una descrizione della categoria passata
     */

    private static String scriviCategoria(Categoria categoria, Categoria radice) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<%s>", categoria.getNome().toUpperCase()));
        sb.append(", ");
        sb.append(String.format("<%s>", categoria.getDescrizione()));
        sb.append(", ");
        sb.append(String.format("<%s/%s>", radice.getNome(), categoria.getPadre().getNome()));
        sb.append(", ");
        sb.append(scriviCampi(categoria));
        for (Categoria figlio : categoria.getFigli()) {
            sb.append("\n");
            sb.append(scriviCategoria(figlio, radice));
        }
        return sb.toString();
    }

    /**
     * Metodo di supporto a scriviRadice e scriviCategoria, restituisce stringa con i campi della categoia passata
     * @param categoria categoria della quale si vogliono i campi
     * @return restituisce stringa con i campi della categoia passata
     */
    private static String scriviCampi(Categoria categoria) {
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        for (Object campo : categoria.getCampiNativi())
            sb.append(String.format("%s ", campo.toString()));
        sb.deleteCharAt(sb.length() - 1);
        sb.append(">");
        return sb.toString();
    }
}