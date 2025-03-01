package it.ingbs.ingegneria_software.gestione_file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * classe usata per la gestione del file di accesso dei configuratori e fruitori:
 * deve solo leggere i file e inserirli nella mappa, o viceversa leggere la mappa e inserirli nel file 
*/
public class GestoreFileCredenziali implements GestoreFile{
    
    private HashMap<String, String> mappaCredenziali; 

    public GestoreFileCredenziali(HashMap<String, String> mappaCredenziali) {
        this.mappaCredenziali = mappaCredenziali;
    }

    /**
     * Permette di aggiungere nuove credenziali dalla mappa al file
     * @throws IOException file delle credenziali non esiste
     */
    @Override
    public void salvaSuFile(File nomeFile) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeFile))) {
            for (HashMap.Entry<String,String> entry : mappaCredenziali.entrySet()) {
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
    @Override
    public HashMap<String, String> leggiFile(File nomeFile) throws IOException {
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
        return mappaCredenziali;
    }   

    /**
     * Getter della mappa delle credenziali
     * @return
     */
    public HashMap<String, String> getMappaCredenziali() {
        return mappaCredenziali;
    }

    /**
     * Setter della mappa delle credenziali
     * @param mappaCredenziali
     */
    public void setMappaCredenziali(HashMap<String, String> mappaCredenziali) {
        this.mappaCredenziali = mappaCredenziali;
    }

}