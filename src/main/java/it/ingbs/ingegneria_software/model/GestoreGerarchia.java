package it.ingbs.ingegneria_software.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import it.ingbs.ingegneria_software.gestione_file.GestoreFileGerarchie;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class GestoreGerarchia {

    private HashMap<String,Gerarchia> mappaGerarchie;
    private final File elencoGerarchie = new File("src\\Data_File\\elencoGerarchie.txt");
    private GestoreFileGerarchie gestoreFileGerarchie;

    public void modificaGerarchie() throws IOException {
        riempiMappaGerarchiDaFile();
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
        
    private void riempiMappaGerarchiDaFile () {
        gestoreFileGerarchie = new GestoreFileGerarchie(mappaGerarchie);
        try {
            mappaGerarchie = gestoreFileGerarchie.leggiFile(elencoGerarchie);
            System.out.println("Caricamento completato");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
        
            /*
     * metodo che crea una gerarchia e la aggiunge alla mappa delle gerarchie 
     */
    public void aggiungiGerarchia(){
        String nome;
        do{
         nome = InputDati.leggiStringaNonVuota("Inserisci il nome della gerarchia:");

        }while(mappaGerarchie.containsKey(nome.toUpperCase()));

        mappaGerarchie.put((nome.toUpperCase()), new Gerarchia(nome.toUpperCase()));
    }

    /*
     * Aggiugo una nuova categoria alla gerarchia già esistente
     */
    public Gerarchia cercaGerarchia(){
        visualizzaGerarchie();
        String nome;
        do{
           nome = InputDati.leggiStringaNonVuota("Inserisci il nome della gerarchia:");
        }while(!mappaGerarchie.containsKey(nome.toUpperCase()));
        return mappaGerarchie.get(nome.toUpperCase());
    }

    /*
     * visualizza i nomi di tutte le gerarchie
     */
    public void visualizzaGerarchie(){
        System.out.println("Elenco Gerarchie:");
        for (String  nomiGerarchia : mappaGerarchie.keySet()) {
            System.out.println(nomiGerarchia);
        }
    }

    public void salvaMappaGerarchie() throws IOException{
   
        gestoreFileGerarchie = new GestoreFileGerarchie(mappaGerarchie);
        gestoreFileGerarchie.salvaSuFile(elencoGerarchie);
        System.out.println("Salvataggio completato con successo");

        
    }

}
