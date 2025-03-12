package it.ingbs.ingegneria_software.gestione_file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

import it.ingbs.ingegneria_software.model.comprensori.ComprensorioGeografico;

/**
 * Classe che gestisce la lettura e scrittura dei comprensori geografici su file.
 */
public class GestoreFileComprensori implements GestoreFile {

    private static final String ERRORE_LETTURA_FILE = "Errore durante la lettura del file";
    private static final String SPAZIO = " ";

    private HashMap<Integer, ComprensorioGeografico> mappaComprensori;

    /**
     * Costruttore della classe GestoreFileComprensori.
     *
     * @param mappaComprensori la mappa dei comprensori geografici
     */
    public GestoreFileComprensori(HashMap<Integer, ComprensorioGeografico> mappaComprensori) {
        this.mappaComprensori = mappaComprensori;
    }

    /**
     * Salva i comprensori geografici su file.
     *
     * @param file il file su cui salvare i comprensori
     * @throws IOException se si verifica un errore durante la scrittura del file
     */
    @Override
    public void salvaSuFile(File file) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (HashMap.Entry<Integer, ComprensorioGeografico> entry : mappaComprensori.entrySet()) {
                String comprensorio = entry.getKey() + " [" + entry.getValue().getListaComuni().stream()
                    .map(String::toUpperCase)
                    .collect(Collectors.joining(", ")) + "]";
                bw.write(comprensorio);
                bw.newLine();
            }
        } catch (IOException ex) {
            throw new IOException(ERRORE_LETTURA_FILE, ex);
        }
    }

    /**
     * Legge i comprensori geografici da un file.
     *
     * @param file il file da cui leggere i comprensori
     * @return la mappa dei comprensori geografici letti dal file
     * @throws IOException se si verifica un errore durante la lettura del file
     */
    @Override
    public HashMap<Integer, ComprensorioGeografico> leggiFile(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null && !linea.isEmpty()) {
                String[] parti = linea.split(SPAZIO);
                int codice = Integer.parseInt(parti[0]);
                String listaComuni = linea.substring(parti[0].length() + 1);
                ComprensorioGeografico comprensorio = new ComprensorioGeografico(codice, listaComuni.toUpperCase());
                mappaComprensori.put(codice, comprensorio);
            }
        } catch (IOException e) {
            System.out.println(ERRORE_LETTURA_FILE);
            throw e;
        }
        return mappaComprensori;
    }
}