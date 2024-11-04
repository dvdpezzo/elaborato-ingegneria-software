package it.ingbs.ingegneria_software.model;

import java.util.HashMap;

public class GestoreFruitore {

    private HashMap<String,String> mappaFruitori = new HashMap();

    GestoreComprensorio gc = new GestoreComprensorio();


     /*
     * Sceglie un comprensorio geografico dalla lista dei comprensori e lo ritorna.
     * OSS: mettere un controllo sul codice inserito per verificare che sia corretto
     */
    public ComprensorioGeografico sceltaComprensorio(int codiceComprensorio){
        return gc.scegliComprensorio(codiceComprensorio);

    }


    public void visualizzaComprensori(){
        
    }

    

    
    
    
}
