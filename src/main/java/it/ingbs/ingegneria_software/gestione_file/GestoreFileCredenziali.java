package it.ingbs.ingegneria_software.gestione_file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * Classe usata per la gestione del file di accesso dei configuratori e fruitori:
 * deve solo leggere i file e inserirli nella mappa, o viceversa leggere la mappa e inserirli nel file.
 */
public class GestoreFileCredenziali implements GestoreFile {

    private static final String SPAZIO = " ";
    private static final String ERRORE_FILE_CREDENZIALI = "Errore durante la lettura del file delle credenziali";

    private HashMap<String, String> mappaCredenziali;

    /**
     * Costruttore della classe GestoreFileCredenziali.
     *
     * @param mappaCredenziali la mappa delle credenziali
     */
    public GestoreFileCredenziali(HashMap<String, String> mappaCredenziali) {
        this.mappaCredenziali = mappaCredenziali;
    }

    /**
     * Permette di aggiungere nuove credenziali dalla mappa al file.
     *
     * @param file il file su cui salvare le credenziali
     * @throws IOException se si verifica un errore durante la scrittura del file
     */
    @Override
    public void salvaSuFile(File file) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (HashMap.Entry<String, String> entry : mappaCredenziali.entrySet()) {
                String utente = entry.getKey() + SPAZIO + entry.getValue();
                bw.write(utente);
                bw.newLine();
            }
        }
    }

    /**
     * Effettua configurazione iniziale della mappa: legge il file delle credenziali e imposta per ogni riga
     * nome utente e password.
     *
     * @param file il file da cui leggere le credenziali
     * @return la mappa delle credenziali lette dal file
     * @throws IOException se si verifica un errore durante la lettura del file
     */
    @Override
    public HashMap<String, String> leggiFile(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null && !linea.isEmpty()) {
                String[] dati = linea.split(SPAZIO);
                String nomeUtente = dati[0];
                String password = dati[1];
                mappaCredenziali.put(nomeUtente, password);
            }
        } catch (IOException e) {
            System.out.println(ERRORE_FILE_CREDENZIALI);
            throw e;
        }
        return mappaCredenziali;
    }

    /**
     * Getter della mappa delle credenziali.
     *
     * @return la mappa delle credenziali
     */
    public HashMap<String, String> getMappaCredenziali() {
        return mappaCredenziali;
    }

    /**
     * Setter della mappa delle credenziali.
     *
     * @param mappaCredenziali la nuova mappa delle credenziali
     */
    public void setMappaCredenziali(HashMap<String, String> mappaCredenziali) {
        this.mappaCredenziali = mappaCredenziali;
    }
}