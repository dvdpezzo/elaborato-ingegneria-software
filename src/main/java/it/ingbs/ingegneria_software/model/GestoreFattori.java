package it.ingbs.ingegneria_software.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import it.ingbs.ingegneria_software.Eccezioni.CategoriaNotFoundException;
import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.model.gerarchie.Gerarchia;
import it.ingbs.ingegneria_software.model.gerarchie.GestoreGerarchie;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;


public class GestoreFattori {
    
    private static final String INSERISCI_IL_NOME_DELLA_GERARCHIA = "Inserisci il nome della Gerarchia:";
    private static final String ERRORE_CATEGORIA ="Le categorie che hai inserito non sono categorie foglia!";
    private static final String CATEGORIA_RICERCATA ="Inserisci il nome della categoria ricercata:";

    private final HashMap<String,FattoriConversione> mappaFattori = new HashMap<>();
    private final GestoreGerarchie gestoreGerarchie = new GestoreGerarchie();
    private static final File NOME_FILE = new File("src\\Data_File\\elencoFattoriConversione.txt");


    /**
     * Creo un fattore di conversione e lo inserisco nella mappa dei fattori 
     * e in automatico creo quello opposto
     * 
     * @param cat1 prima categoria inserita 
     * @param cat2 seconda categoria inserita 
     * @param valore indica il valore di conversione tra la prima e la seconda categoria 
     */
    private void assegnaFattoreConversione(Categoria cat1, Categoria cat2, double valore){
        if(!(cat1.hasFiglio(cat1) && cat2.hasFiglio(cat2))){

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
        if(cat1.hasFiglio(cat1) && cat2.hasFiglio(cat2) && cat3.hasFiglio(cat3)){
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
    
        try {
            Categoria cat1, cat2;
            do { 
                
                cat1 = trovaCategoria();
                cat2 = trovaCategoria();
            } while (cat1.hasFiglio(cat1) && cat2.hasFiglio(cat2));
            
            double valore = InputDati.leggiDoubleLimitato("Inserisci il valore di conversione:", 0.5, 2);
            assegnaFattoreConversione(cat1, cat2, valore);
        } catch (CategoriaNotFoundException ex) {
            System.out.println("Categoria non trovata");
        }
    }

    /**
     * Chiede al configuratore quale fattore di convesione derivato vuole calcolare 
     */
    public void nuovoFattoreDerivato(){
        try {
            visualizzaFattori();
            
            Categoria cat1 = trovaCategoria();
            Categoria cat2= trovaCategoria();            
            Categoria cat3 = trovaCategoria();
            fattoreDerivato(cat1, cat2, cat3);
        } catch (CategoriaNotFoundException ex) {
            System.out.println("Categoria non trovata");
        }
    }


    /**
     * legge da file i fattori e li aggiunge alla mappa dei fattori 
     * @param NOME_FILE nome del file dove vengono salvati i fattori di conversione
     * @throws IOException
     */

    public void leggiFileFattori() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(NOME_FILE))) {
        String line;
        while ((line = reader.readLine()) != null) {
            // Processa ogni riga del file 
            String[] parts = line.split(" -> |: | [ | / | ]");  // Divide la riga in base ai separatori 
            if (parts.length == 3) {
                String categoria1 = parts[0].trim();
                String categoria2 = parts[1].trim();
                double valore = Double.parseDouble(parts[2].trim());
               

                // Crea le categorie 
                Categoria c1;
                Categoria c2;
                try {
                    c1= getCategoria(categoria1);
                    c2 = getCategoria(categoria2);
                    FattoriConversione fattore = new FattoriConversione(valore, c2, c1);
                    mappaFattori.put(categoria1 + "->" + categoria2, fattore);
                    
                } catch (CategoriaNotFoundException e) {
                   e.printStackTrace();
                }
                 }
             }
           }
      }


      /**
       * Scrive su file i vari fattori di conversione
       * @param nomeFile2 nome del file sul quale vengono salvati i fattori 
              * @throws IOException
              */
             public void salvaFattori() throws IOException {
               try (FileWriter writer = new FileWriter(NOME_FILE)) {
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
            System.out.println(stringa + " : " + mappaFattori.get(stringa).getValore());
        }
     }



    /*
     * menu che permette di eseguire operazioni sui fattori di conversione
     */
    public void modificaFattori() throws IOException{
        String [] voci = {"Visualizza gerarchie", "Aggiungi nuovo fattore di conversione","Aggiungi fattore di conversione derivato","Salva fattori di conversione",
        "Visualizza fattori di conversione"};
        MenuUtil menuFattori = new MenuUtil("AZIONI SUI FATTORI DI CONVERSIONE",voci);
        int scelta;
        do{
           scelta=menuFattori.scegli();
           leggiFileFattori();
           switch(scelta){
            case 1:
                gestoreGerarchie.stampaGerarchie();
            break;
            case 2:
              nuovoFattore();
            break;
            case 3: 
              nuovoFattoreDerivato();
            break;

            case 4:
             salvaFattori();
             break;

            case 5: 
             visualizzaFattori();
            break;
           }
        }while(scelta!=0);
    }

    //aggiungere controllo per gerarchie e categorie inesistenti
    private Categoria trovaCategoria () throws CategoriaNotFoundException {
        
        String nomeGerarchia = InputDati.leggiStringaNonVuota(INSERISCI_IL_NOME_DELLA_GERARCHIA);
        Gerarchia gerarchiaRicercata = gestoreGerarchie.getGerarchia(nomeGerarchia);
        String nomeCategoria = InputDati.leggiStringaNonVuota(CATEGORIA_RICERCATA);
        
        return gerarchiaRicercata.getCategoria(nomeCategoria);
    }


    private Categoria getCategoria(String nomeCategoria) throws CategoriaNotFoundException {
        for (Gerarchia gerarchia : gestoreGerarchie.getRadici().values()) {
            Categoria categoria = gerarchia.getCategoria(nomeCategoria);
            if (categoria != null) {
                return categoria;
            }
        }
        throw new CategoriaNotFoundException();
    }
}



