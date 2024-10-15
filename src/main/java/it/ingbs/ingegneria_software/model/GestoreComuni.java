package it.ingbs.ingegneria_software.model;

import java.util.*;
import java.io.*;




//classe che gestisce l'elenco dei comuni inseriti dal configuratore
public class GestoreComuni {

    private final String MSG_ERRORE_COMUNE="Comune già presente nella lista";
    private final File fileComuni = new File("C:\\Users\\Mazza\\Desktop\\ign soft 2023 2024\\Elaborato-Ingegneria-del-software-2023_2024\\File di accesso\\elencoComuni");
    private final String ERRORE_COMUNE="Comune non trovato!";


    public HashMap<Integer,String> mappaComuni = new HashMap<>();




    //aggiunge un comune alla lista
    public void aggiungiComune(Comuni comune){
        if(conrtolloComuni(comune)){
            System.out.println(MSG_ERRORE_COMUNE);
        }
        else{
            mappaComuni.put(comune.getNumero(),comune.getNome());
        }
    }


    //controllo inserimento del comune in base al nome 
    public boolean conrtolloComuni(Comuni comune){
      if(mappaComuni.containsValue(comune.getNome())) return true;
      else return false;
    }


    //visualizza elenco comuni
    public void visualizzaComuni(){
        for (Map.Entry<Integer, String> entry : mappaComuni.entrySet())
        {
            System.out.println(entry.getKey()+" "+entry.getValue()+"\n");
        }
    }



    //metodo per la scelta del comune da inserire nel compensporio geografico, ritorna il nome del comune della mappa (se corrisponde il numero)
    public String scegliComune(int n){
        for (Map.Entry<Integer,String> entry : mappaComuni.entrySet()){
            if(entry.getKey()==n){
                return entry.getValue();
            }
        }
        return ERRORE_COMUNE;
    }


    //metodo che salva la mappa dei comuni su un file 
    public void scriviComuni() throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileComuni));
        for (HashMap.Entry<Integer,String> entry : mappaComuni.entrySet()){
            String comune = entry.getKey() +" "+entry.getValue();
            bw.write(comune);
            bw.newLine();
        }
        bw.close();
    }

    /*metodo che legge la mapa dei comuni da file
      OSS: non so se funziona, da testare*/ 
    public void leggiComuni()throws IOException{ 
        try (BufferedReader br = new BufferedReader(new FileReader(fileComuni))){
            String parola = br.readLine();
            do{
                String [] datiComune = parola.split(" ");
                String numero = datiComune[0];
                String nome = datiComune[1];
                Integer num = Integer.parseInt(numero);
                mappaComuni.put(num,nome); 
                parola = br.readLine();

            }while(parola!=null && !parola.equals("\n"));
         }
    }

    void stampaComuni() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
