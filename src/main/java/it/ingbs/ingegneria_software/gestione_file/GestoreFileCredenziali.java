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
public class GestoreFileCredenziali {
    
    private final File fileCredenziali;

    public GestoreFileCredenziali(String nomeFile) {
        this.fileCredenziali = new File(nomeFile);  
    }
   

    /**
     * Permette di aggiungere nuove credenziali dalla mappa al file
     * @param hashMap 
     * @throws IOException file delle credenziali non esiste
     */
    public void salvaSuFile(HashMap<String, String> mappaCredenziali) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileCredenziali))) {
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
    public HashMap<String, String> leggiFile() throws IOException {
        HashMap<String, String> mappaCredenziali = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileCredenziali))) {
            String parola;
            while ((parola = br.readLine()) != null) {
                String[] dati = parola.split(" ");
                if (dati.length == 2) {
                    String nome = dati[0];
                    String pass = dati[1];
                    mappaCredenziali.put(nome, pass);
                }
            }
        }
        return mappaCredenziali;
    }   
}