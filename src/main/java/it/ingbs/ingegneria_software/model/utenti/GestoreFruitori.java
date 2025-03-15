package it.ingbs.ingegneria_software.model.utenti;

import java.util.HashMap;

import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.model.comprensori.ComprensorioGeografico;
import it.ingbs.ingegneria_software.model.comprensori.GestoreComprensorio;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;


public class GestoreFruitori {

    private static final String MSG_PASS = "Inserisci la tua password:";
    private static final String MSG_NOME_UTENTE = "Inserisci il tuo nome utente:";
    private static final String MSG_EMAIL = "Inserisci la tua email:";
    private static final String ERRORE_COMPRENSORIO = "IL CODICE DEL COMPRENSORIO INSERITO E ERRATO!";
    private static final String MSG_COD_COMPRENSORIO = "Inserisci il codice del tuo comprensorio:";
    private final HashMap<String,Fruitore> mappaDatiFruitori;
    private final HashMap<String,String> mappaCredenziali;
    private GestoreComprensorio gestoreComprensorio;
    private final GestoreUtente gestoreUtente;
    private final GestoreDati gestoreDati;
    
            
    /**
     * Non posso prendre una mappa ma i dati del fruitore
     * @param gestoreCredenziali
     */
    public GestoreFruitori(GestoreUtente gestoreUtente, GestoreDati gestoreDati, GestoreComprensorio gestoreComprensorio) {
        this.gestoreDati = gestoreDati;
        this.gestoreComprensorio = gestoreComprensorio;
        this.mappaCredenziali = gestoreDati.getCredenzialiFruitori();
        this.mappaDatiFruitori = gestoreDati.getDatiFruitori();
        this.gestoreUtente = gestoreUtente;

    }

          
        
    /*
     * aggiunge i dati relativi ad un fruitore alla Mappa dei dati (MappaFruitori)
     */
    public void aggiungiDati(String nomeUtente, String password){
        mappaCredenziali.put(nomeUtente, password);
    }

    /*
     * visualizza elenco fruitori con informazioni annesse 
     */
    public void visualizzaFruitori(){
        for(Fruitore f : mappaDatiFruitori.values()){
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
        gestoreComprensorio.visualizzaComprensori();
        ComprensorioGeografico comprensorio;
        int code;
        do {
            code = InputDati.leggiIntero(MSG_COD_COMPRENSORIO);
            comprensorio = gestoreComprensorio.getComprensorio(code);
            if (comprensorio == null) {
                System.out.println(ERRORE_COMPRENSORIO);
            }
        } while (comprensorio == null);

        String email;
        do {
            email = InputDati.leggiStringaNonVuota(MSG_EMAIL);
            if (emailEsistente(email)) {
                System.out.println("Email già utilizzata, si prega di inserire un'altra email.");
            }
        } while (emailEsistente(email));

        String nomeUtente;
        do {
            nomeUtente = InputDati.leggiStringaNonVuota(MSG_NOME_UTENTE);
        } while (gestoreUtente.controlloUtente(nomeUtente));

        String pass = InputDati.leggiStringa(MSG_PASS);
        Fruitore newFruitore = new Fruitore(nomeUtente, pass, code, email);
        mappaDatiFruitori.put(newFruitore.getNomeUtente(), newFruitore);
        gestoreDati.setDatiFruitori(mappaDatiFruitori);
        return newFruitore;
    }

    private boolean emailEsistente(String email) {
        for (Fruitore fruitore : mappaDatiFruitori.values()) {
            if (fruitore.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Trova un fruitore e lo restituisce 
     * @param nomeUtente parametro di ricerca del fruitore
     * @return il fruitore oppure un errore. 
     */
    public Fruitore trovaFruitore(String nomeUtente) {
        Fruitore utente = mappaDatiFruitori.get(nomeUtente);
        if (utente == null) {
            throw new IllegalArgumentException("Utente non trovato: " + nomeUtente);
        }
        return utente;
    }
    

}
