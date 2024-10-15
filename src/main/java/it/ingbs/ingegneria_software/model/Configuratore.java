package it.ingbs.ingegneria_software.model;

import java.util.LinkedList;
import java.util.List;

import it.ingbs.ingegneria_software.utilitaGenerale.InputDati;

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
        boolean val = true;
         do{
           int n = InputDati.leggiIntero("Inserisci il numero del primo comune che vuoi aggiungere al comprensorio:");
           String comune_1 = gc.scegliComune(n);
           listaComuni.add(comune_1);
           int p = InputDati.leggiIntero("Inserisci il numero del secondo comune:");
           String comune_2 = gc.scegliComune(p);
           if(comune_1.equals(comune_2)){
            System.out.println("QUESTO COMUNE E GIA STATO INSERITO!");
            val=false;
           }
           else
           {
              listaComuni.add(comune_2);
              int q = InputDati.leggiIntero("Inserisci il numero del terzo comune:");
              String comune_3=gc.scegliComune(q);
                 if(comune_3.equals(comune_1) || comune_3.equals(comune_2))
                 {
                    System.out.println("QUESTO COMUNE E GIA STATO INSERITO!");
                   val=false;
                 }
                 else listaComuni.add(comune_3);
           }
         }while(val);
        return new ComprensorioGeografico(listaComuni);
    }   

    public void aggiungiComune(String nomeComune, int codiceComprensorio) {
        ComprensorioGeografico comprensorio = gestoreComprensorio.getComprensorio(codiceComprensorio);
        if (comprensorio == null) {
            System.out.println("Errore: Il comprensorio geografico con codice " + codiceComprensorio + " non esiste.");
            return;
        }

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

}
