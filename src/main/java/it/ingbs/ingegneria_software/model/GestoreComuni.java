package it.ingbs.ingegneria_software.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.ingbs.ingegneria_software.utilita_generale.InputDati;




//classe che gestisce l'elenco dei comuni inseriti dal configuratore
public class GestoreComuni {

    private static final String MSG_ERRORE_COMUNE_DUPLICE ="Comune già presente nella lista";
    private static final String ERRORE_COMUNE_NON_TROVATO ="Comune non trovato!";
    private final File fileComuni = new File("src\\Data File\\elencoComuni.txt");   
    private static final Logger LOGGER = Logger.getLogger(GestoreComuni.class.getName());
    

    private Map<Integer,String> mappaComuni = new HashMap<>();

    public GestoreComuni() {
        try {
            leggiComuni();
        } catch (IOException e){
            LOGGER.severe("Errore durante la lettura dei comuni dal file.");
        }
    }


    //aggiunge un comune alla lista
    public void aggiungiComune(Comuni comune){
        if(controlloComuni(comune)){
            LOGGER.info(MSG_ERRORE_COMUNE_DUPLICE);
        }
        else{
            mappaComuni.put(comune.getNumero(),comune.getNome());
        }
    }


    //controllo inserimento del comune in base al nome 
    public boolean controlloComuni (Comuni comune){
        return mappaComuni.containsValue(comune.getNome());
    }


    //visualizza elenco comuni
    public void visualizzaComuni(){
        for (Map.Entry<Integer, String> entry : mappaComuni.entrySet())
        {
            LOGGER.info(String.format("%d %s \n", entry.getKey(), entry.getValue()));
        }
    }



    public String scegliComune(int n) {
        while (n < 1 || n > mappaComuni.size()) {
            n = InputDati.leggiIntero("Input out of range. Please enter a valid comune number:");
        }

        for (Map.Entry<Integer, String> entry : mappaComuni.entrySet()) {
            if (entry.getKey() == n) {
                return entry.getValue();
            }
        }

        return ERRORE_COMUNE_NON_TROVATO;
    }



    //metodo che salva la mappa dei comuni su un file 
    public void scriviComuni() throws IOException{
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileComuni))) {
            for (Map.Entry<Integer,String> entry : mappaComuni.entrySet()) {
                String comune = entry.getKey() +" "+entry.getValue();
                bw.write(comune);
                bw.newLine();
            }
        }
    }

    /*metodo che legge la mappa dei comuni da file
      OSS: non so se funziona, da testare*/ 
    public void leggiComuni()throws IOException{ 
        try (BufferedReader br = new BufferedReader(new FileReader(fileComuni))){
            String parola = br.readLine();
            do{
                String [] datiComune = parola.split(" ");
                Integer num = Integer.valueOf(datiComune[0]);
                String nome = datiComune[1];
                mappaComuni.put(num,nome); 
                parola = br.readLine();

            }while(parola!=null && !parola.equals("\n"));
         }
    }

    public void stampaComuni() {
        int position = 1;
        for (Map.Entry<Integer, String> entry : mappaComuni.entrySet()) {
            LOGGER.info(String.format("Position: %d, Comune: %s", position, entry.getValue()));
            position++;
        }
    }

    //controlla quale sia il numero più alto di conune e lo ritorna 
    public int getLastNumero(){
        int max=0;
        for(Map.Entry<Integer,String> entry : mappaComuni.entrySet()){
            if(entry.getKey()>max){
                max=entry.getKey();
            }
         }
         return max; 
    }


    
}
