package it.ingbs.ingegneria_software.model.gerarchie;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import it.ingbs.ingegneria_software.utilita_generale.InputDati;

public class CategoriaFoglia extends Categoria {


    private String nome;  // Nome della categoria
    private String descrizione; //descrizione generale della categoria   
    
    
    /*
        * costruttore
        */
    public CategoriaFoglia(String nome, String descrizione) {
        super(nome, descrizione);
    }



    public String getNome() {
        return nome;
    }



    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public boolean isFoglia() {
        return true;
    }

    @Override
    public HashMap getSottoCategorie() {
        return (HashMap) Collections.emptyMap();
    }


        
    
    }
    
 



/*
 * Visto che è un estensione di categoria possiede gli stessi metodi.
 * OSS: Quando verrà chiesto di inserire una categoria dovremmo chiedere se è foglia oppure no e mandare il costruttore opportuno (modifica da eseguire)
 */





    


  


    


    

    

