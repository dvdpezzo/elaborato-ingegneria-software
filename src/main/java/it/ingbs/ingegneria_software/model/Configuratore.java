package it.ingbs.ingegneria_software.model;

import java.util.LinkedList;
import java.util.List;

import it.ingbs.ingegneria_software.utilita_generale.InputDati;

public class Configuratore extends Utente  {
    private static final String DEFAULT = "admin";
    private GestoreComprensorio gestoreComprensorio;

    

    public Configuratore(String nomeUtente, String passwordUtente) {
        super(nomeUtente, passwordUtente);
        this.gestoreComprensorio = new GestoreComprensorio();
    }

/* crea un compensorioGeofratico partento dalla lista dei comuni, l'utente decide quali comuni inserire in base al numero inserito
   esegue anche un controllo sul comune scelto per evitare che si ripeta lo stesso. 
 */
    public ComprensorioGeografico creaComprensorioGeografico(){      

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
                    System.out.println("Questo comune è già stato inserito!");
                } else {
                    listaComuni.add(comune);
                    comuneValido = true;
                }
            }
        }
        return new ComprensorioGeografico(listaComuni);
    }   

    public void aggiungiComune(String nomeComune, int codiceComprensorio) {
        ComprensorioGeografico comprensorio = gestoreComprensorio.getComprensorio(codiceComprensorio);
        comprensorio.aggiungiComune(nomeComune);
        System.out.println("Comune " + nomeComune + " aggiunto con successo al comprensorio " + codiceComprensorio);
    }


    public void salvaCambiamenti(){}

    public void visualizzaComprensori(){}
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
