package it.ingbs.ingegneria_software.model;

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

     
   private ComprensorioGeografico comprensorio;


   /*
    * Quando creo un utente Fruitore devo indicare il comprensorio geografico ed i suoi dati utente
    */
    public Fruitore(String nomeUtente,String passUtente,ComprensorioGeografico comprensorio){
         super(nomeUtente,passUtente); 
         this.comprensorio=comprensorio;

    }





  
    
}
