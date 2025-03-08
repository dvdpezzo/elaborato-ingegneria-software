package it.ingbs.ingegneria_software.model;

import java.io.*;
import java.util.HashMap;

import it.ingbs.ingegneria_software.gestione_file.GestoreFileCredenziali;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;


public class GestoreFruitori {

    private static final String MSG_PASS = "Inserisci la tua password:";
    private static final String MSG_NOME_UTENTE = "Inserisci il tuo nome utente:";
    private static final String MSG_EMAIL = "Inserisci la tua email:";
    private static final String ERRORE_COMPRENSORIO = "IL CODICE DEL COMPRENSORIO INSERITO E ERRATO!";
    private static final String MSG_COD_COMPRENSORIO = "Inserisci il codice del tuo comprensorio:";
    private HashMap<String,Fruitore> mappaFruitori = new HashMap<String,Fruitore>();
    private HashMap<String,String> mappaPass = new HashMap<String,String>();
    private GestoreComprensorio gc = new GestoreComprensorio();
    private GestoreUtente gu = new GestoreUtente();
    private final File datiFruitori = new File("src\\File_di_accesso\\datiFruitori.txt");



    /**
     * Non posso prendre una mappa ma i dati del fruitore
     * @param gestoreCredenziali
     */
    public GestoreFruitori(GestoreFileCredenziali gestoreCredenziali){
        this.mappaFruitori = leggiFileFruitori();
    }
          
        
    /*
     * aggiunge i dati relativi ad un fruitore alla Mappa dei dati (MappaFruitori)
     */
    public void aggiungiDati(String nomeUtente, String password){
        mappaPass.put(nomeUtente, password);
    }

    /*
     * visualizza elenco fruitori con informazioni annesse 
     */
    public void visualizzaFruitori(){
        for(Fruitore f : mappaFruitori.values()){
            System.out.println("Utente:"); 
            f.infoFruitore();
        }
        }

    /**
     * 
     * OSS: Devo aggiungere il controllo sul nome utente a livello globale  
     * creo un nuovo frutiore
     * @return il fruitore creato 
     */
    public Fruitore creaUtenteFruitore() {
        leggiFileFruitori();
        gc.visualizzaComprensori();
        ComprensorioGeografico comprensorio;
        do{
        int code = InputDati.leggiIntero(MSG_COD_COMPRENSORIO);
        comprensorio =gc.getComprensorio(code);
           if(comprensorio==null) 
            {
              System.out.println(ERRORE_COMPRENSORIO);
            }
        }while(comprensorio==null);

        String email = InputDati.leggiStringaNonVuota(MSG_EMAIL);
        String nomeUtente; 
        do{
            nomeUtente= InputDati.leggiStringaNonVuota(MSG_NOME_UTENTE);
        }while(gu.controlloUtente(nomeUtente));

        String pass = InputDati.leggiStringa(MSG_PASS);
        Fruitore newFruitore = new Fruitore(nomeUtente,pass,comprensorio,email);
        mappaFruitori.put(newFruitore.getNomeUtente(),newFruitore);
        salvaSuFileFruitori();
        return newFruitore;

    }

    /**
     * Trova un fruitore e lo restituisce 
     * @param nomeUtente parametro di ricerca del fruitore
     * @return il fruitore oppure un errore. 
     */
    public Fruitore trovaFruitore(String nomeUtente) {
        Fruitore utente = mappaFruitori.get(nomeUtente);
        if (utente == null) {
            throw new IllegalArgumentException("Utente non trovato: " + nomeUtente);
        }
        return utente;
    }
    
       

    /**
     * Salva i dati dei fruitori su file 
     */
    public void salvaSuFileFruitori(){
        try(
           BufferedWriter bw = new BufferedWriter(new FileWriter(datiFruitori))
           ){
            for(HashMap.Entry<String,Fruitore> entry : mappaFruitori.entrySet()){
                bw.write(entry.getKey() + " " +entry.getValue().getPassword() +" "+entry.getValue().getEmail()+" "+
                entry.getValue().getComprensorio().getCodice());
                bw.newLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    /*
     * legge da file i dati dei fruitori
     */
    public HashMap<String,Fruitore> leggiFileFruitori(){

        try(
            BufferedReader br = new BufferedReader(new FileReader(datiFruitori))
           ){
            String parola = br.readLine();
            do{
                String [] dati = parola.split(" ");
                String nome = dati[0];
                String pass = dati[1];
                String mail = dati[2];
                String comprensorio = dati[3];
                int codice = Integer.parseInt(comprensorio);
                ComprensorioGeografico cg = gc.getComprensorio(codice);
                mappaPass.put(nome,pass);
                mappaFruitori.put(nome,new Fruitore(nome, pass, cg, mail));
                parola = br.readLine();
            } while(parola!=null && parola.equals("\n"));

         }catch(Exception ex){
               ex.printStackTrace();
               }
            return mappaFruitori;
    }
}
