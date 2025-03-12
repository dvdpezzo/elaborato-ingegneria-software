package it.ingbs.ingegneria_software.model;

import java.util.HashMap;
import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;
import java.io.*;


public class GestoreFattori {
    
    public static String ERRORE_CATEGORIA ="Le categorie che hai inserito non sono categorie foglia!";
    private static String CATEGORIA_PARTENZA="Inserisci il nome della categoria di partenza:";
    private static String CATEGORIA_DESTINAZIONE="Inserisci il nome della categoria di destinazione:";

    private HashMap<String,FattoriConversione> mappaFattori = new HashMap<String,FattoriConversione>();

    File nomeFile = new File("src\\Data File\\elencoFattoriConversione.txt");


    /**
     * Creo un fattore di conversione e lo inserisco nella mappa dei fattori 
     * e in automatico creo quello opposto
     * 
     * @param cat1 prima categoria inserita 
     * @param cat2 seconda categoria inserita 
     * @param valore indica il valore di conversione tra la prima e la seconda categoria 
     */
    private void assegnaFattoreConversione(Categoria cat1, Categoria cat2, double valore){
        if(cat1.hasFiglio(cat1)==false && cat2.hasFiglio(cat2)==false){

            String chiave = cat1.getNome()+"->"+cat2.getNome();
            FattoriConversione fattore = new FattoriConversione(valore, cat1, cat2);
            mappaFattori.put(chiave,fattore);
            
            //creo in automatico il fattore opposto 
            fattoreOpposto(cat1,cat2,valore);

        }
        else{
            System.out.println(ERRORE_CATEGORIA);
        }
    }

    /**
     * Creo il fattore di conversione opposto rispetto a quello inserito nel metodo assegnaFattoreConversione()
     * 
     * @param cat1 
     * @param cat2
     * @param valore
     */
    private void fattoreOpposto(Categoria cat1, Categoria cat2, double valore){

        String chiaveOpposta = cat2.getNome()+"->"+cat1.getNome();
        FattoriConversione fatOpposto = new FattoriConversione(1/valore, cat2, cat1);
        mappaFattori.put(chiaveOpposta,fatOpposto);
    }


    /**
     * Creo un fattore di conversione derivandolo da due fattori già esistenti
     * @param cat1 categoria dalla quale prendo il valore
     * @param cat2 categoria dalla quale prendo il valore 
     * @param cat3 categoria delle quale voglio creare un valore 
     */
    public void fattoreDerivato(Categoria cat1, Categoria cat2, Categoria cat3){
        if(cat1.hasFiglio(cat1)==false && cat2.hasFiglio(cat2)==false && cat3.hasFiglio(cat3)==false){
            String controllo12 = cat1.getNome()+" -> "+cat2.getNome();
            String controllo23 = cat2.getNome()+" -> "+cat3.getNome();
            if(mappaFattori.containsKey(controllo12) && mappaFattori.containsKey(controllo23)){
                double val1 = mappaFattori.get(controllo12).getValore();
                double val2= mappaFattori.get(controllo23).getValore();
                double valDerivato =val1*val2;

                assegnaFattoreConversione(cat1, cat3, valDerivato);
            }
        }
    }

    /**
     * chiede al configuratore quale fattore di conversione vuole creare 
     */
    public void nuovoFattore(){
    
        String str1 = InputDati.leggiStringaNonVuota(CATEGORIA_PARTENZA);
        Categoria cat1 = null; 
        String str2 = InputDati.leggiStringaNonVuota(CATEGORIA_DESTINAZIONE);
        Categoria cat2=null;
        double valore = InputDati.leggiDoubleLimitato("Inserisci il valore di conversione:", 0.5, 2);
        assegnaFattoreConversione(cat1, cat2, valore);
    }

    /**
     * Chiede al configuratore quale fattore di convesione derivato vuole calcolare 
     */
    public void nuovoFattoreDerivato(){
        visualizzaFattori();
        String str1 = InputDati.leggiStringaNonVuota(CATEGORIA_PARTENZA);
        Categoria cat1 = null; //metodo che ritorna la categoria
        
        String str2 =InputDati.leggiStringaNonVuota("Inserisci il nome della seconda categoria:");
        Categoria cat2= null;

        String str3 = InputDati.leggiStringaNonVuota(CATEGORIA_DESTINAZIONE);
        
        Categoria cat3 = null; 
        fattoreDerivato(cat1, cat2, cat3);
    }


    /**
     * legge da file i fattori e li aggiunge alla mappa dei fattori 
     * @param nomeFile nome del file dove vengono salvati i fattori di conversione
     * @throws IOException
     */

    public void leggiFileFattori(String nomeFile) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))) {
        String line;
        while ((line = reader.readLine()) != null) {
            // Processa ogni riga del file 
            String[] parts = line.split(" -> |: | [ | / | ]");  // Divide la riga in base ai separatori 
            if (parts.length == 3) {
                String categoria1 = parts[0].trim();
                String categoria2 = parts[1].trim();
                double valore = Double.parseDouble(parts[2].trim());
                String descrizione1 = parts[3].trim();
                String descrizione2 = parts[4].trim();

                // Crea le categorie 
                Categoria c1 = new Categoria(categoria1,descrizione1); 
                Categoria c2 = new Categoria(categoria2,descrizione2);
                // Aggiungi il fattore di conversione alla mappa
                FattoriConversione fattore = new FattoriConversione(valore, c2, c1);
                mappaFattori.put(categoria1 + "->" + categoria2, fattore);
                 }
             }
           }
      }


      /**
       * Scrive su file i vari fattori di conversione
       * @param nomeFile2 nome del file sul quale vengono salvati i fattori 
              * @throws IOException
              */
             public void salvaFattori(File nomeFile2) throws IOException {
               try (FileWriter writer = new FileWriter(nomeFile2)) {
            // Itera sulla mappa dei fattori di conversione e scrive ogni coppia su una riga
            for (FattoriConversione fattore : mappaFattori.values()) {
                // Scrive la riga con le categorie e il valore del fattore
                writer.append(fattore.getCat1().getNome() + " -> " +
                              fattore.getCat2().getNome() + ": " +
                              fattore.getValore() + "\n");
            }
        }
    }


    /**
     * visualizza tutti i fattori di conversione
     */
    private void visualizzaFattori(){
        for (String stringa : mappaFattori.keySet() ) {
            System.out.println(stringa);
        }
     }



    /*
     * menu che permette di eseguire operazioni sui fattori di conversione
     */
    public void modificaFattori() throws IOException{
        String [] voci = {"Aggiungi nuovo fattore di conversione","Aggiunigi fattore di conversione derivato","Salva fattori di conversione",
        "Visualizza fattori di conversione"};
        MenuUtil menuFattori = new MenuUtil("AZIONI SUI FATTORI DI CONVERSIONE",voci);
        int scelta=0;
        do{
           scelta=menuFattori.scegli();
           switch(scelta){
            case 1:
              nuovoFattore();
            break;
            case 2: 
              nuovoFattoreDerivato();
            break;

            case 3:
             salvaFattori(nomeFile);
             break;

            case 4: 
             visualizzaFattori();
            break;
           }
        }while(scelta!=0);
    }
}





/*

PER IL METODO NUOVO FATTORE: 
Devo permettere al controllore di inserire il fattore di conversione
         PROBLEMA: come conosce le varie categorie? 
         SOLUZIONE: a) visualizzo le gerarchie ad ogni creazione, in questo modo posso vederle in tempo-reale e scrivere
                    b) le visualizzo una sola volta all'inizio del menu, in questo modo divido la creazione della visualizzazione
        
         PROBLEMA: serve un metodo che trovi una categoira e la ritorni controllando che sia foglia (uso hasFiglio)
                    */