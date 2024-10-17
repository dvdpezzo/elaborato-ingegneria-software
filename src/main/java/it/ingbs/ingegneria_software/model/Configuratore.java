package it.ingbs.ingegneria_software.model;

import java.util.*;
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

/* crea un compensorioGeofratico partento dalla lista dei comuni, l'utente decide quali comuni inserire in base al numero inserito
   esegue anche un controllo sul comune scelto per evitare che si ripeta lo stesso. 
 */
    public void creaComprensorioGeografico(){      

        List<String> listaComuni = new LinkedList<>();
        GestoreComuni gc = new GestoreComuni();

        gc.stampaComuni();

        //un comprensorio deve avere minimo tre comuni limitrofi
        for (int i = 0; i < 3; i++) {
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

        // Sort the list of comuni
        Collections.sort(listaComuni);
        ComprensorioGeografico comprensorioNuovo = new ComprensorioGeografico(listaComuni);
        gestoreComprensorio.aggiungiComprensorio(comprensorioNuovo);
    }


    public void aggiungiComune(String nomeComune, int codiceComprensorio) {
        gestoreComprensorio.aggiungiComuneAlComprensorio(codiceComprensorio, nomeComune);        
    }


    public void salvaCambiamenti(){}

    public void visualizzaComprensori(){
        for (ComprensorioGeografico comprensorio : gestoreComprensorio.getMappaComprensori().values()) {
            logConfiguratore.info(comprensorio.toString());
        }
    }
    public void visualizzaGerarchia(){}
    public void visualizzaFattoriConversione(){}

    public List<Categoria> creaGerarchia() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Categoria creaCategoria() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean controllaEsistenzaComprensorio(int codiceComprensorio) {
        return gestoreComprensorio.getComprensorio(codiceComprensorio)!= null;
    }

}
