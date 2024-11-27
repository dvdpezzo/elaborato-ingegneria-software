package it.ingbs.ingegneria_software.gestione_file;

import java.io.*;
import java.util.Map;

/**
 * classe usata per la gestione del file di accesso dei configuratori e fruitori:
 * deve solo leggere i file e inserirli nella mappa, o viceversa leggere la mappa e inserirli nel file 
*/
public class GestoreFileCredenziali implements GestoreFile{
    
    private Map<String, String> mappaCredenziali; 

    public GestoreFileCredenziali(Map<String, String> mappaCredenziali) {
        this.mappaCredenziali = mappaCredenziali;
    }

    /**
     * Permette di aggiungere nuove credenziali dalla mappa al file
     * @throws IOException file delle credenziali non esiste
     */
    public void salvaSuFile(File nomeFile) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeFile))) {
            for (Map.Entry<String,String> entry : mappaCredenziali.entrySet()) {
                String utente = entry.getKey() + " " + entry.getValue();
                bw.write(utente);
                bw.newLine();
            }
        }
    }
    /**
     * Effettua configurazione iniziale della mappa: legge il file delle credenziali e imposta per ogni riga
     * nome utente e password
     * @throws IOException file delle credenziali non esiste
     */
    public void leggiFile(File nomeFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeFile))) {
            String parola = br.readLine();
            do {
                String [] dati = parola.split(" ");
                String nome = dati[0];
                String pass = dati[1];
                mappaCredenziali.put(nome,pass); 
                parola = br.readLine();               
            } while (parola!=null && !parola.equals("\n"));
        }
    }   

    /**
     * Getter della mappa delle credenziali
     * @return
     */
    public Map<String, String> getMappaCredenziali() {
        return mappaCredenziali;
    }

    /**
     * Setter della mappa delle credenziali
     * @param mappaCredenziali
     */
    public void setMappaCredenziali(Map<String, String> mappaCredenziali) {
        this.mappaCredenziali = mappaCredenziali;
    }

}