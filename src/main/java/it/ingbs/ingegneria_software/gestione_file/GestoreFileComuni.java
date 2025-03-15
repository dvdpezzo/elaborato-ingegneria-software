package it.ingbs.ingegneria_software.gestione_file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe che gestisce la lettura e scrittura dei comuni su file.
 */
public class GestoreFileComuni {
    
    private final File fileComuni;

    /**
     * Costruttore della classe GestoreFileComuni.
     *
     * @param mappaComuni la mappa dei comuni
     */
    public GestoreFileComuni(String nomeFile) {
        this.fileComuni = new File(nomeFile);
    }
    
    /**
     * Salva i comuni su file.
     *
     * @param file il file su cui salvare i comuni
     * @throws IOException se si verifica un errore durante la scrittura del file
     */
    public void salvaSuFile(HashMap<Integer, String> mappaComuni) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileComuni))){
            for (Map.Entry<Integer,String> entry : mappaComuni.entrySet()) {
                String comune = entry.getKey() +" "+entry.getValue();
                bw.write(comune);
                bw.newLine();
            }
        }
    }

    /**
     * Legge i comuni da un file.
     *
     * @param file il file da cui leggere i comuni
     * @return la mappa dei comuni letti dal file
     * @throws IOException se si verifica un errore durante la lettura del file
     */
    public HashMap<Integer, String> leggiFile() throws IOException {
        HashMap<Integer, String> mappaComuni = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileComuni))){
            String parola = br.readLine();
            do{
                String [] datiComune = parola.split(" ");
                int num = Integer.parseInt(datiComune[0]);
                String nome = datiComune[1];
                mappaComuni.put(num,nome.toUpperCase()); 
                parola = br.readLine();
            }while(parola!=null && !parola.equals("\n")&& !parola.equals(""));
        }
        return mappaComuni;
    }

}