package it.ingbs.ingegneria_software.gestione_file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import it.ingbs.ingegneria_software.model.ComprensorioGeografico;

public class GestoreFileComprensori implements GestoreFile {

    private HashMap<Integer, ComprensorioGeografico> mappaComprensori; 

    public GestoreFileComprensori(HashMap<Integer, ComprensorioGeografico> mappaComprensori) {
        this.mappaComprensori = mappaComprensori;
    }
    @Override
    public void salvaSuFile(File nomeFile) throws IOException {
       try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(nomeFile));
            for (HashMap.Entry<Integer,ComprensorioGeografico> entry : mappaComprensori.entrySet()){
                String comprensorio = entry.getKey()+ " "+ entry.getValue().getListaComuni();
                bw.write(comprensorio);
                bw.newLine();
            }
            bw.close();
        } catch (IOException ex) {
        }
    }

    @Override
    public HashMap leggiFile(File nomeFile) throws IOException {
         try (BufferedReader br = new BufferedReader(new FileReader(nomeFile))) {
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                String[] parts = line.split(" ");
                int codice = Integer.parseInt(parts[0]);
                String listaComuni = line.substring(parts[0].length() + 1); // Extract the list of comuni from the remaining part of the line
                ComprensorioGeografico comprensorio = new ComprensorioGeografico(codice, listaComuni);
                mappaComprensori.put(codice, comprensorio);
            }
        } catch (IOException e) {
            // Log the exception or handle it in a meaningful way
            System.out.println("Error reading file");
        }
        return mappaComprensori;
    }

}
