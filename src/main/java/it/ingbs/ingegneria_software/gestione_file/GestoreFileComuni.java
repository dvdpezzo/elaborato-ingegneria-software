package it.ingbs.ingegneria_software.gestione_file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GestoreFileComuni implements GestoreFile {

    private HashMap<Integer, String> mappaComuni; 

    public GestoreFileComuni(HashMap<Integer, String> mappaComuni) {
        this.mappaComuni = mappaComuni;
    }
    @Override
    public void salvaSuFile(File nomeFile) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeFile))) {
            for (Map.Entry<Integer,String> entry : mappaComuni.entrySet()) {
                String comune = entry.getKey() +" "+entry.getValue();
                bw.write(comune);
                bw.newLine();
            }
        }
    }

    @Override
    public HashMap leggiFile(File nomeFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeFile))){
            String parola = br.readLine();
            do{
                String [] datiComune = parola.split(" ");
                Integer num = Integer.parseInt(datiComune[0]);
                String nome = datiComune[1];
                mappaComuni.put(num,nome); 
                parola = br.readLine();
            }while(parola!=null && !parola.equals("\n")&& !parola.equals(""));
        }
        return mappaComuni;
    }

}