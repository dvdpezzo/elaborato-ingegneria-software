package it.ingbs.ingegneria_software.model;

import java.util.HashMap;

import it.ingbs.ingegneria_software.utilita_generale.InputDati;

public class GestoreCategorie {

    HashMap<String,CategoriaFoglia> mappaCategerorieFoglia = new HashMap();

         /* 
         * cerca una catFoglia nella mappa in base al nome  (Ho provato ad unsare un try-catch ma non penso funzioni)
         */
        public CategoriaFoglia cercaCatFoglia(String nomeCat){
         try {
            for (String nomiCategoria : mappaCategerorieFoglia.keySet()) {
                if(nomiCategoria.equals(nomeCat))
                    return mappaCategerorieFoglia.get(nomeCat);
            }
        }
         catch (Exception catNotFound) {
            System.out.println("La categorie inserita non è stata trovata!");
         }
            return null;
        }


         /*
         * visualizzo tuttoe le categorie foglia presenti e le loro descrizioni
         */
        public void visualizzaCatFoglia(){
            for (String nomiCategoria : mappaCategerorieFoglia.keySet()) 
            {
               System.out.println("Categoria:"+mappaCategerorieFoglia.get(nomiCategoria)+"\n"+"Descrizione:"
                       +mappaCategerorieFoglia.get(nomiCategoria).getDescrizione());
            }
        }


        /*
         * aggiungo una nuova categoriaFoglia alla mappa e la creo. 
         */
        public void aggiungiCategoriaFoglia(){
            String nome;
            do{
               nome =InputDati.leggiStringaNonVuota("Inserisci il nome della categoria foglia:");
            }while(!controlloFoglia(nome));
            String descrizione = InputDati.leggiStringa("Inserisci una descrizione (facolatativa)");
            mappaCategerorieFoglia.put(nome,new CategoriaFoglia(nome, descrizione));
        }


        /*
         * controlla se la categoria è gia presente, se è presente mostra un errore
         */
        public boolean controlloFoglia(String nomeDaControllare){
            for (String prova : mappaCategerorieFoglia.keySet()) {
                if(prova.equals(nomeDaControllare))
                { 
                    System.out.println("Categoria già presente!");
                    return true;
                }
            }
            return false;
        }


    
    /*
     * OSS: NON MI RICORDO SE DEVO GESTIRE SOLO LE CATEGORIA FOGLIA O TUTTE LE CATEGORIE. 
     */




    


    


}
