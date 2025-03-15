package it.ingbs.ingegneria_software.gestione_file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import it.ingbs.ingegneria_software.model.utenti.Fruitore;

public class GestoreFileDatiFruitori {

    private final File fileDati;

    public GestoreFileDatiFruitori(String DATI_FRUITORI) {
        this.fileDati = new File(DATI_FRUITORI);
    }

    /**
     * Salva i dati dei fruitori su file 
     */
    public void salvaSuFile(HashMap<String,Fruitore> mappaFruitori){
        try(
           BufferedWriter bw = new BufferedWriter(new FileWriter(fileDati))
           ){
            for(HashMap.Entry<String,Fruitore> entry : mappaFruitori.entrySet()){
                bw.write(entry.getKey() + " " +entry.getValue().getPassword() +" "+entry.getValue().getEmail()+" "+
                entry.getValue().getComprensorio());
                bw.newLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

     /*
     * legge da file i dati dei fruitori
     */
    public HashMap<String, Fruitore> leggiFile() {
        HashMap<String, Fruitore> mappaFruitori = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileDati))) {
            String parola = br.readLine();
            while (parola != null && !parola.equals("\n")) {
                String[] dati = parola.split(" ");
                String nome = dati[0];
                String pass = dati[1];
                String mail = dati[2];
                int codice = Integer.parseInt(dati[3]);
                mappaFruitori.put(nome, new Fruitore(nome, pass, codice, mail));
                parola = br.readLine();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mappaFruitori;
    }

}
