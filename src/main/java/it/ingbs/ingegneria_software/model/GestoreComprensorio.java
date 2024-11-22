package it.ingbs.ingegneria_software.model;

import java.io.*;
import java.util.*;

import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;


//classe che gestisce l'elenco dei comprensori inseriti dal configuratore
public class GestoreComprensorio {

    private static final String ERRORE_CODICE_DEL_COMPRENSORIO_SBAGLIATO = "Codice del comprensorio sbagliato!";
    private static final String DOMANDA_AGGIUNGERE_UN_ALTRO_COMUNE = "Vuoi aggiungere un altro comune?";
    private static final String INSERISCI_IL_NOME_DEL_COMUNE = "Inserisci il nome del comune:";
    private static final String INSERISCI_IL_CODICE_DEL_COMPRENSORIO = "Inserisci il codice del comprensorio al quale si vuole aggiungere il comune:";
    private static final String ERRORE_NEL_LEGGERE_IL_FILE = "Errore nel leggere il file";
    private static final String COMPRENSORIO_AGGIUNTO = "Comprensorio aggiunto.";
    private static final String ERRORE_COMPRENSORIO_GIÀ_ESISTENTE = "Comprensorio già esistente.";
    private static final String TITOLO_MENU_MODIFICA = "AZIONI SUI COMPRENSORI";
    private static final String[] VOCI_MENU_MODIFICA = {"Aggiungi comune al comprensorio: ",  "Aggiungi Comprensorio: ","Rimuovi Comprensorio: ","Salva Cambiamenti: "};
    private static final int MIN_NUMERO_COMUNI_COMPRENSORIO = 3;

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
      
    /**
     * Menu per la scelta delle operazioni da eseguire sui Comprensori 
     * - Aggiungi comune al comprensorio
     * - Aggiungi comprensorio
     * - Rimuovi comprensorio
     * - Salva Cambiamenti
     * @throws IOException
     */
    public void modificaComprensori() throws IOException {
        MenuUtil menuComprensorio = new MenuUtil(TITOLO_MENU_MODIFICA, VOCI_MENU_MODIFICA);
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

    /**
     *  Aggiunge un comune ad un comprensorio già esistente
     * @throws IOException
     */
    private void aggiungiComuneAlComprensorio() throws IOException{
        visualizzaComprensori();
        boolean risposta=true;
        int codiceComprensorio = InputDati.leggiIntero(INSERISCI_IL_CODICE_DEL_COMPRENSORIO);					
        do{
            if(controllaEsistenzaComprensorio(codiceComprensorio)){
                ComprensorioGeografico comprensorio = getComprensorio(codiceComprensorio);
                if (comprensorio != null) {
                    comprensorio.aggiungiComuneNuovo(InputDati.leggiStringa(INSERISCI_IL_NOME_DEL_COMUNE));
                    salvaMappaComprensoriSuFile();
                }
                risposta = InputDati.yesOrNo(DOMANDA_AGGIUNGERE_UN_ALTRO_COMUNE);
            }else{
                System.out.println(ERRORE_CODICE_DEL_COMPRENSORIO_SBAGLIATO);
                risposta=false;
            }
        }while (risposta);	
    }

    /**
     * Aggiunge un comprensorio alla mappa
     */
    public void aggiungiComprensorio( ){
        ComprensorioGeografico comprensorioNuovo = creaComprensorioGeografico();
        if(controlloComprensorioDuplicato(comprensorioNuovo) != null){
            System.out.println( ERRORE_COMPRENSORIO_GIÀ_ESISTENTE);
        }
        else{
            mappaComprensori.put(comprensorioNuovo.getCodice(), comprensorioNuovo);
            salvaMappaComprensoriSuFile();
            System.out.println(COMPRENSORIO_AGGIUNTO);
        }
    }

    /**
     * Salva la mappa sul file elencoComprensori
     */
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
    /**
     * controlla se il comprensorio sia già presente oppure no
     * @param comprensorioNuovo
     * @return
     */
    private ComprensorioGeografico controlloComprensorioDuplicato (ComprensorioGeografico comprensorioNuovo) {
        for (ComprensorioGeografico comprensorio : mappaComprensori.values()) {
            if (comprensorio.equals(comprensorioNuovo)) {
                return comprensorio;
            }
        }
        return null;
    }

    /**
     * Cerca comprensorio per codice
     * @param codice
     * @return comprensorio con codice corrispondente
     */
    public ComprensorioGeografico getComprensorio (int codice) {
        return mappaComprensori.get(codice);
    }
    
    /**
     * Legge la mappa dei comprensori da file
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
            System.out.println(ERRORE_NEL_LEGGERE_IL_FILE);
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
}


    

    
    


    



