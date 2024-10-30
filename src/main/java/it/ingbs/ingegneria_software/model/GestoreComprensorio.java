package it.ingbs.ingegneria_software.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


//classe che gestisce l'elenco dei comprensori inseriti dal configuratore
public class GestoreComprensorio {

    private static final Logger LOGGER = Logger.getLogger(GestoreComprensorio.class.getName());
    private HashMap<Integer,ComprensorioGeografico> mappaComprensori;
    private File fileComprensori = new File("src\\Data File\\elencoComprensori.txt");

    //costruttore che legge i comprensori dal file e li carica nella mappa
    public GestoreComprensorio() {
       this.mappaComprensori = new HashMap<>();
       configuraMappaComprensoriDaFile();
    }

    public HashMap<Integer, ComprensorioGeografico> getMappaComprensori() {
        return mappaComprensori;
    }


    //aggiungo un comprensorio alla mappa
    public void aggiungiComprensorio(ComprensorioGeografico comprensorioNuovo ){
        if(controlloComprensorioDuplicato(comprensorioNuovo) != null){
            LOGGER.log(Level.SEVERE, "Comprensorio già esistente.");
        }
        else{
            mappaComprensori.put(comprensorioNuovo.getCodice(), comprensorioNuovo);
            salvaMappaComprensoriSuFile();
            LOGGER.log(Level.INFO, "Comprensorio aggiunto.");
        }
    }

    public void aggiungiComuneAlComprensorio(int codiceComprensorio, String nomeComune) throws IOException {
        ComprensorioGeografico comprensorio = getComprensorio(codiceComprensorio);
        if (comprensorio != null) {
            comprensorio.aggiungiComuneNuovo(nomeComune);
            salvaMappaComprensoriSuFile();
        }
    }
    //controlla se il comprensorio sia già presente oppure no
    private ComprensorioGeografico controlloComprensorioDuplicato (ComprensorioGeografico comprensorioNuovo) {
        for (ComprensorioGeografico comprensorio : mappaComprensori.values()) {
            if (comprensorio.equals(comprensorioNuovo)) {
                return comprensorio;
            }
        }
        return null;
    }


    //cerca comprensorio per codice
    public ComprensorioGeografico getComprensorio (int codice) {
        return mappaComprensori.get(codice);
    }

    //salva la mappa sul file elencoComprensori
    public void salvaMappaComprensoriSuFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileComprensori));
            for (HashMap.Entry<Integer,ComprensorioGeografico> entry : mappaComprensori.entrySet()){
                String comprensorio = entry.getKey()+ " "+ entry.getValue().getListaComuni();
                bw.write(comprensorio);
                bw.newLine();
            }
            bw.close();
        } catch (IOException ex) {
        }
    }
    


    /*legge la mappa dei comprensori da file 
    OSS: Non ho idea di come svilupparlo visto che devo passare un argomento Comprensorio.
    */    
    private void configuraMappaComprensoriDaFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileComprensori))) {
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
            LOGGER.log(Level.SEVERE, "Error reading file", e);
        }
    }

    



}


    

    
    


    



