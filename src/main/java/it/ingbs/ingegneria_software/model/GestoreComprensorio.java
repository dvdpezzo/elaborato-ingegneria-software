package it.ingbs.ingegneria_software.model;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import it.ingbs.ingegneria_software.gestione_file.GestoreFileComprensori;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;


//classe che gestisce l'elenco dei comprensori inseriti dal configuratore
public class GestoreComprensorio {

    
    private static final String ELENCO_COMPRENSORI_TXT = "src\\Data_File\\elencoComprensori.txt";
    private static final int MIN_NUMERO_COMUNI_COMPRENSORIO = 3;
    
    private HashMap<Integer,ComprensorioGeografico> mappaComprensori;
    private final File fileComprensori = new File(ELENCO_COMPRENSORI_TXT);
    private final GestoreFileComprensori gestoreFileComprensori;

    //costruttore che legge i comprensori dal file e li carica nella mappa
    public GestoreComprensorio() {
        this.mappaComprensori = new HashMap<>();
        this.gestoreFileComprensori = new GestoreFileComprensori(this.mappaComprensori);
        configuraMappaComprensoriDaFile();
    }

    public HashMap<Integer, ComprensorioGeografico> getMappaComprensori() {
        return mappaComprensori;
    }


    //aggiungo un comprensorio alla mappa
    public void aggiungiComprensorio( ){
        ComprensorioGeografico comprensorioNuovo = creaComprensorioGeografico();
        if(controlloComprensorioDuplicato(comprensorioNuovo) != null){
            System.out.println("Comprensorio già esistente.");
        }
        else{
            mappaComprensori.put(comprensorioNuovo.getCodice(), comprensorioNuovo);
            salvaMappaComprensoriSuFile();
            System.out.println( "Comprensorio aggiunto.");
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
    private void salvaMappaComprensoriSuFile() {
        try {
            gestoreFileComprensori.salvaSuFile(fileComprensori);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   


    /*legge la mappa dei comprensori da file 
    OSS: Non ho idea di come svilupparlo visto che devo passare un argomento Comprensorio.
    */    
    private void configuraMappaComprensoriDaFile() {
       try {
        mappaComprensori = (HashMap<Integer, ComprensorioGeografico>) gestoreFileComprensori.leggiFile(fileComprensori);
    } catch (IOException e) {
        e.printStackTrace();
    }
    }


    // OSS: PARTE MODIFICATA DOPO INCONTRO IN PRESENZA 15/11
    /*
    * visualizza tutti i comprensori esistenti
    */

    public void visualizzaComprensori(){
        for (ComprensorioGeografico comprensorio : mappaComprensori.values()) {
            System.out.println(comprensorio.toString());
        }
    }

    /*
    * controlla se il comprensorio esiste in base al codice
    */
    public boolean controllaEsistenzaComprensorio(int codiceComprensorio) {
        return getComprensorio(codiceComprensorio)!= null;
    }

    


    /*
    * Metodo che aggiunge un comune ad un comprensorio già esistente
    */
    private void aggiungiComuneAlComprensorio() throws IOException{
        visualizzaComprensori();
        boolean risposta=true;
        int codiceComprensorio = InputDati.leggiIntero("Inserisci il codice del comprensorio al quale si vuole aggiungere il comune:");					
            do{
                if(controllaEsistenzaComprensorio(codiceComprensorio)){
                ComprensorioGeografico comprensorio = getComprensorio(codiceComprensorio);
                if (comprensorio != null) {
                    comprensorio.aggiungiComuneNuovo(InputDati.leggiStringa("Inserisci il nome del comune:"));
                    salvaMappaComprensoriSuFile();
                }
                risposta = InputDati.yesOrNo("Vuoi aggiungere un altro comune?");
                }
                else{
                System.out.println("Codice del comprensorio sbagliato!");
                risposta=false;
                }
            }while (risposta);	
        }
        

    /*
    * crea un comprensorio geografico 
    */
    public ComprensorioGeografico creaComprensorioGeografico(){

        List<String> listaComuni = new LinkedList<>();
        GestoreComuni gc = new GestoreComuni();

        gc.stampaComuni();

        //un comprensorio deve avere minimo n=3 ? comuni limitrofi
        gc.inserimentoComuni(listaComuni,MIN_NUMERO_COMUNI_COMPRENSORIO);

        // Riordina in ordine alfabetico la lista dei comuni, onde evitare Comprensori geogradici duplicati
        Collections.sort(listaComuni);
        return new ComprensorioGeografico(listaComuni);
    }

     
    /*
     * Menu per la scelta delle operazioni da eseguore sui Comprensori 
     * -Agggiungi comune al comprensorio
     * -Aggiungi comprensorio
     * -Rimuovi comprensorio
     * -Salva Cambiamenti
     */
    public void modificaComprensori() throws IOException {

        String [] voci = {"Aggiungi comune al comprensorio: ",  "Aggiungi Comprensorio: ","Rimuovi Comprensorio: ","Salva Cambiamenti: "};
        MenuUtil menuComprensorio = new MenuUtil("AZIONI SUI COMPRENSORI",voci);
        int scelta=0;
        do{
            scelta = menuComprensorio.scegli();
            switch(scelta){
                case 1: aggiungiComuneAlComprensorio();
                break;
            
                case 2: aggiungiComprensorio();
                break;

           //   case 3: rimuoviComprensorio();

                case 4: salvaMappaComprensoriSuFile();
                break;
            }

        }while(scelta!=0);
    }
}


    

    
    


    



