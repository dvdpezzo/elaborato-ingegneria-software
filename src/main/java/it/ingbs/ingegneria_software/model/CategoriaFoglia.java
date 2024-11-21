package it.ingbs.ingegneria_software.model;

import java.util.HashMap;

import it.ingbs.ingegneria_software.utilita_generale.InputDati;

public class CategoriaFoglia extends Categoria {


    private String nome;  // Nome della categoria
    private String descrizione; //descrizione generale della categoria
    private  HashMap<String,CategoriaFoglia> mappaSottocatFoglia;
    
    
        /*
         * costruttore
         */
        public CategoriaFoglia(String nome, String descrizione) {
            super(nome, descrizione);
            this.mappaSottocatFoglia = new HashMap<>();
        }
    
        /*
         * cerca una catFoglia nella mappa in base al nome
         */
        public CategoriaFoglia cercaCatFoglia(String nomeCat){
            for (String nomiCategoria : mappaSottocatFoglia.keySet()) {
                if(nomiCategoria.equals(nomeCat)){
                    return mappaSottocatFoglia.get(nomeCat);
                }
            }
            return null;
        }



        /*
         * visualizzo tutto le categorie foglia presenti
         */
        public void visualizzaCatFoglia(){
            for (String nomiCategoria : mappaSottocatFoglia.keySet()) 
            {
               System.out.println("Categoria:"+mappaSottocatFoglia.get(nomiCategoria));
            }
        }
        
    
    }
    
 



/*
 * Visto che è un estensione di categoria possiede gli stessi metodi.
 * OSS: Quando verrà chiesto di inserire una categoria dovremmo chiedere se è foglia oppure no e mandare il costruttore opportuno (modifica da eseguire)
 */





    


  


    


    

    

