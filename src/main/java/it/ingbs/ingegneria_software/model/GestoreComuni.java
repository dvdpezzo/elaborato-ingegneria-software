package it.ingbs.ingegneria_software.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.ingbs.ingegneria_software.gestione_file.GestoreFileComuni;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;




//classe che gestisce l'elenco dei comuni inseriti dal configuratore
public class GestoreComuni {

    private static final String MSG_ERRORE_COMUNE_DUPLICE ="Comune già presente nella lista";
    private static final String ERRORE_COMUNE_NON_TROVATO ="Comune non trovato!";
    private final File fileComuni = new File("src\\Data_File\\elencoComuni.txt");    

    private HashMap<Integer,String> mappaComuni = new HashMap<>();
    private final GestoreFileComuni gestoreFileComuni;

    public GestoreComuni() {
        this.gestoreFileComuni = new GestoreFileComuni(this.mappaComuni);
        try {
            gestoreFileComuni.leggiFile(fileComuni);
        } catch (IOException e){
            System.out.println("Errore durante la lettura dei comuni dal file.");
        }
    }


    //aggiunge un comune alla lista
    public void aggiungiComune(Comuni comune){
        if(controlloComuni(comune)){
            System.out.println(MSG_ERRORE_COMUNE_DUPLICE);
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
            System.out.println(String.format("%d %s \n", entry.getKey(), entry.getValue()));
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

    //metodo che salva la mappa dei comuni su un file -> TEMPORANEO
    public void scriviComuni() throws IOException{
        gestoreFileComuni.salvaSuFile(fileComuni);
    }

    public void stampaComuni() {
        int position = 1;
        for (Map.Entry<Integer, String> entry : mappaComuni.entrySet()) {
            System.out.println(String.format("Position: %d, Comune: %s", position, entry.getValue()));
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


    /*
     * MODIFICA FATTA DOPO INCONTRO 15/11
     */

      /**
     * Inserisce n comuni da una lista di comuni disponibili.
     * Se un comune viene scelto più volte, lo ripete fino a quando non viene scelto un altro.
     */
    public void inserimentoComuni(List<String> listaComuni, int n) {
        for (int i = 0; i < n; i++) {
            boolean comuneValido = false;
            while (!comuneValido) {
                int numeroComune = InputDati.leggiIntero("Inserisci il numero del " + (i + 1) + "° comune:");
                String comune = scegliComune(numeroComune);
                if (listaComuni.contains(comune)) {
                    System.out.println("Questo comune è già stato inserito!");
                } else {
                    listaComuni.add(comune);
                    comuneValido = true;
                }
            }
        }
    }

    
}
