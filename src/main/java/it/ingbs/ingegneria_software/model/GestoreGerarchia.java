package it.ingbs.ingegneria_software.model;

import java.util.HashMap;

import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class GestoreGerarchia {

    private HashMap<String,Gerarchia> mappaGerarchie = new HashMap();

    public void modificaGerarchie() {
          String [] voci = {"Aggiungi Gerarchia: ",  "Aggiungi Categoria: ","Rimuovi Categoria: ","Salva Cambiamenti: "};
        MenuUtil menuComprensorio = new MenuUtil("AZIONI SUI COMPRENSORI",voci);
        int scelta=0;
        do{
            scelta = menuComprensorio.scegli();
            switch(scelta){
                case 1: aggiungiGerarchia();
                break;
            
                case 2: cercaGerarchia().aggiungiSottocategoria();
                break;

                case 3: cercaGerarchia().eliminaSottocategoria();
                break;

                case 4: salvaMappaGerarchie();
                break;
            }

        }while(scelta!=0);
    
    }

    /*
     * metodo che crea una gerarchia e la aggiunge alla mappa delle gerarchie 
     */
    public void aggiungiGerarchia(){
        String nome;
        do{
         nome = InputDati.leggiStringaNonVuota("Inserisci il nome della gerarchia:");

        }while(controlloNomeGerarchia(nome));

        String descrizione = InputDati.leggiStringa("Inserisci una descrizione per la gerarchia(facoltativa)");
        mappaGerarchie.put((nome.toUpperCase()), new Gerarchia(nome.toUpperCase(), descrizione));
    }


    /*
     * controllo se una gerarchia è già presente, controllo effettuato sul nome 
     */
    private boolean controlloNomeGerarchia(String nome){
        for (String nomiGerarchia : mappaGerarchie.keySet()) {
            return nomiGerarchia.equals(nome);
            }
        return false;
     }


    /*
     * Aggiugo una nuova categoria alla gerarchia già esistente
     */
    public Gerarchia cercaGerarchia(){
        visualizzaGerarchie();
        String nome;
        do{
           nome = InputDati.leggiStringaNonVuota("Inserisci il nome della gerarchia:");
        }while(controlloNomeGerarchia(nome));
        return mappaGerarchie.get(nome);
    }

    /*
     * visualizza i nomi di tutte le gerarchie
     */
    public void visualizzaGerarchie(){
        for (String  nomiGerarchia : mappaGerarchie.keySet()) {
            System.out.println(nomiGerarchia);
        }
    }

    public void salvaMappaGerarchie(){}

}
