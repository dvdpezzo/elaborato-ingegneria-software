package it.ingbs.ingegneria_software.model;

import java.util.HashMap;
import java.io.File;
import java.io.*;


//classe che gestisce l'elenco dei comprensori inseriti dal configuratore
public class GestoreComprensorio {

    public HashMap<Integer,ComprensorioGeografico> mappaComprensori;
    private File fileComprensori = new File("Data File\\elencoComprensori.txt");

    //costruttore che legge i comprensori dal file e li carica nella mappa
    public GestoreComprensorio() {
       this.mappaComprensori = new HashMap<>();
    }


    //aggiungo un comprensorio alla mappa
    public void aggiungiComprensorio(ComprensorioGeografico comprensorioNuovo ){
        if(getComprensorio(comprensorioNuovo.getCodice()) == null){
            System.out.println("COMPRENSORIO GIA ESISTENTE!");
        }
        else{
        mappaComprensori.put(comprensorioNuovo.getCodice(), comprensorioNuovo);
        }
    }


    //controlla se il compensorio sia già presente oppure no
    public ComprensorioGeografico getComprensorio (int codice) {
        return mappaComprensori.get(codice);
    }


    //salva la mappa sul file elencoComprensori
    public void salvaMappaComprensoriSuFile() throws IOException{
         BufferedWriter bw = new BufferedWriter(new FileWriter(fileComprensori));
        for (HashMap.Entry<Integer,ComprensorioGeografico> entry : mappaComprensori.entrySet()){
            String comprensorio = entry.getKey()+ " "+ entry.getValue().getListaComuni();
            bw.write(comprensorio);
            bw.newLine();
        }
        bw.close();
    }


    /*legge la mappa dei comprensori da file 
    OSS: Non ho idea di come svilupparlo visto che devo passare un argomento Comprensorio.
    */    
    public void configuraMappaComprensoriDaFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileComprensori))) {
            String parola;
            while ((parola = br.readLine()) != null && !parola.equals("\n")) {
                // Process each line here
                // For example:
                // String[] parts = parola.split(" ");
                // int codice = Integer.parseInt(parts[0]);
                // String listaComuni = parts[1];
                // ComprensorioGeografico comprensorio = new ComprensorioGeografico(codice, listaComuni);
                // mappaComprensori.put(codice, comprensorio);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


    

    
    


    



