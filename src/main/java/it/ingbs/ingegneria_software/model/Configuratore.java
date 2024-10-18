package it.ingbs.ingegneria_software.model;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.ingbs.ingegneria_software.utilita_generale.InputDati;

public class Configuratore extends Utente  {
    private Logger logConfiguratore = Logger.getLogger(getClass().getName());
    private static final String DEFAULT = "admin";
    private final GestoreComprensorio gestoreComprensorio;    

    public Configuratore(String nomeUtente, String passwordUtente) {
        super(nomeUtente, passwordUtente);
        this.gestoreComprensorio = new GestoreComprensorio();
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

        //un comprensorio deve avere minimo n=3 comuni limitrofi
        inserimentoComuni(listaComuni, gc, 3);

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
                    logConfiguratore.info("Questo comune è già stato inserito!");
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
     */
    public void aggiungiComune(String nomeComune, int codiceComprensorio) {
        if(controllaEsistenzaComprensorio(codiceComprensorio)){
            gestoreComprensorio.aggiungiComuneAlComprensorio(codiceComprensorio, nomeComune);
        }else{
            logConfiguratore.log(Level.SEVERE, "Comprensorio non trovato.");
        }
                
    }
    /**
     * controlla che determinato comprensorio esista, dato il codice
     * @param codiceComprensorio
     * @return
     */
    private boolean controllaEsistenzaComprensorio(int codiceComprensorio) {
        return gestoreComprensorio.getComprensorio(codiceComprensorio)!= null;
    }
    /**
     * Viusalizza a video i Comprensori
     */
    public void visualizzaComprensori(){
        for (ComprensorioGeografico comprensorio : gestoreComprensorio.getMappaComprensori().values()) {
            logConfiguratore.info(comprensorio.toString());
        }
    }

    public void salvaCambiamenti(){}
    public void visualizzaGerarchia(){}
    public void visualizzaFattoriConversione(){}

    public List<Categoria> creaGerarchia() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Categoria creaCategoria() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
