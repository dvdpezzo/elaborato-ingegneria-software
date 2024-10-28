package it.ingbs.ingegneria_software.model;

import java.util.logging.Logger;

public class Fruitore extends Utente{

 /*
  * a)scelta del comprensorio di appartenenza
    b)accesso con nome utente (univoco) e password
    c)se primo accesso deve inserire anche la mail.
    d)aggiungere metodi di utilità
  */

  /*
	 * Dopo essere entrati nella sezione del fruitore bisogna selezionare il comprensorio di appartenenza ed in seguito 
	 * eseguire l'accesso.
	 * Se primo accesso devo inserire anche email e nome utente deve essere univoco
	 * Se primo accesso già eseguito devo vedere il menu frontEnd. 
	 */


   private final GestoreComprensorio gestoreComprensorio;
   private static final Logger LOGGER = Logger.getLogger(Fruitore.class.getName());




   /*
    * Creo un nuovo oggetto Fruitore
    */
    public Fruitore(String nomeUtente, String passUtente){
        super(nomeUtente, passUtente);
        this.gestoreComprensorio = new GestoreComprensorio();
        
    }

    /*
     * Visualizza tutti i comprensori
     */
    public void visualizzaComprensori(){
        for (ComprensorioGeografico comprensorio : gestoreComprensorio.getMappaComprensori().values()) {
            LOGGER.info(comprensorio.toString());
        }
    }

    /*
     * Sceglie un comprensorio geografico dalla lista dei comprensori e lo ritorna.
     */
    public ComprensorioGeografico sceltaComprensorio(){

        return null;

    }
    






  
    
}
