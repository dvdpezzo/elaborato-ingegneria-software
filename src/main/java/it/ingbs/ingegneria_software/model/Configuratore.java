package it.ingbs.ingegneria_software.model;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.ingbs.ingegneria_software.utilita_generale.InputDati;

public class Configuratore extends Utente  {
    private static final Logger LOGGER = Logger.getLogger(Configuratore.class.getName());
    private static final int MIN_NUMERO_COMUNI_COMPRENSORIO = 3;
    private final GestoreComprensorio gestoreComprensorio;  
    private final GestoreCategorie gestoreCategorie;  

    public Configuratore(String nomeUtente, String passwordUtente) {
        super(nomeUtente, passwordUtente);
        this.gestoreComprensorio = new GestoreComprensorio();
        this.gestoreCategorie = new GestoreCategorie();
    }
    /**
     * Crea un compensorioGeografico partento dalla lista dei comuni, 
     * l'utente decide quali comuni inserire in base al numero inserito
     * esegue anche un controllo sul comune scelto per evitare che si ripeta lo stesso. 
     */
    public void creaComprensorioGeografico(){

        List<String> listaComuni = new LinkedList<>();
        GestoreComuni gc = new GestoreComuni();

        gc.stampaComuni();

        //un comprensorio deve avere minimo n=3 ? comuni limitrofi
        inserimentoComuni(listaComuni, gc, MIN_NUMERO_COMUNI_COMPRENSORIO);

        // Riordina in ordine alfabetico la lista dei comuni, onde evitare Comprensori geogradici duplicati
        Collections.sort(listaComuni);
        ComprensorioGeografico comprensorioNuovo = new ComprensorioGeografico(listaComuni);
        gestoreComprensorio.aggiungiComprensorio(comprensorioNuovo);
    
    }
    /**
     * Inserisce n comuni da una lista di comuni disponibili.
     * Se un comune viene scelto più volte, lo ripete fino a quando non viene scelto un altro.
     */
    private void inserimentoComuni(List<String> listaComuni, GestoreComuni gc, int n) {
        for (int i = 0; i < n; i++) {
            boolean comuneValido = false;
            while (!comuneValido) {
                int numeroComune = InputDati.leggiIntero("Inserisci il numero del " + (i + 1) + "° comune:");
                String comune = gc.scegliComune(numeroComune);
                if (listaComuni.contains(comune)) {
                    LOGGER.info("Questo comune è già stato inserito!");
                } else {
                    listaComuni.add(comune);
                    comuneValido = true;
                }
            }
        }
    }
    /**
     * aggiungi comune al comprensorio esistente con codice passato ad argomento
     * @param nomeComune
     * @param codiceComprensorio
     * @throws IOException 
     */
    public void aggiungiComune(String nomeComune, int codiceComprensorio) throws IOException {
        if(controllaEsistenzaComprensorio(codiceComprensorio)){
            gestoreComprensorio.aggiungiComuneAlComprensorio(codiceComprensorio, nomeComune);
        }else{
            LOGGER.log(Level.SEVERE, "Comprensorio non trovato.");
        }
                
    }
    /**
     * controlla che determinato comprensorio esista, dato il codice
     * @param codiceComprensorio
     * @return
     */
    public boolean controllaEsistenzaComprensorio(int codiceComprensorio) {
        return gestoreComprensorio.getComprensorio(codiceComprensorio)!= null;
    }
    /**
     * Viusalizza a video i Comprensori
     */
    public void visualizzaComprensori(){
        for (ComprensorioGeografico comprensorio : gestoreComprensorio.getMappaComprensori().values()) {
            LOGGER.info(comprensorio.toString());
        }
    }

    public void salvaCambiamenti(){}
    public void visualizzaGerarchia(){
        gestoreCategorie.visualizzaAlberoCategorie();
    }
    public void visualizzaFattoriConversione(){}

    public List<Categoria> creaGerarchia(String nomeGerarchia) {
        return gestoreCategorie.creaGerarchiaRicorsiva(nomeGerarchia); 
    }

    public Categoria creaCategoria(String nomeCategoria) {
        return null;
    }

}
