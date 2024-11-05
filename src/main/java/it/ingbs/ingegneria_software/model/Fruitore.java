package it.ingbs.ingegneria_software.model;

import java.util.logging.Logger;

public class Fruitore extends Utente{


   private final GestoreComprensorio gestoreComprensorio;
   private static final Logger LOGGER = Logger.getLogger(Fruitore.class.getName());
   private ComprensorioGeografico comprensorio;
   private String email;




   /*
    * Creo un nuovo oggetto Fruitore
    */
    public Fruitore(String nomeUtente, String passUtente, ComprensorioGeografico comprensorio,String email){
        super(nomeUtente, passUtente);
        this.comprensorio=comprensorio;
        this.gestoreComprensorio = new GestoreComprensorio();
        this.email=email;
        
    }

    
   
    






  
    
}
