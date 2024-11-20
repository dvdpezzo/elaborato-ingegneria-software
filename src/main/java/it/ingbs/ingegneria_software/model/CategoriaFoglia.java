package it.ingbs.ingegneria_software.model;

import java.util.HashMap;

public class CategoriaFoglia extends Categoria {


    private String nome;  // Nome della categoria
    private String descrizione; //descrizione generale della categoria
    private HashMap<String,CategoriaFoglia> mappaSottocatFoglia;


    /*
     * costruttore
     */
    public CategoriaFoglia(String nome, String descrizione) {
        super(nome, descrizione);
        this.mappaSottocatFoglia = new HashMap<>();
    }

}

/*
 * Visto che è un estensione di categoria possiede gli stessi metodi.
 * OSS: Quando verrà chiesto di inserire una categoria dovremmo chiedere se è foglia oppure no e mandare il costruttore opportuno (modifica da eseguire)
 */





    


  


    


    

    

