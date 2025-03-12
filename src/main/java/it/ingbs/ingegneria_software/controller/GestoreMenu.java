package it.ingbs.ingegneria_software.controller;

import java.io.File;
import java.util.HashMap;

import it.ingbs.ingegneria_software.gestione_file.GestoreFileGerarchie;
import it.ingbs.ingegneria_software.model.Configuratore;
import it.ingbs.ingegneria_software.model.Fruitore;
import it.ingbs.ingegneria_software.model.GestoreComprensorio;
import it.ingbs.ingegneria_software.model.gerarchie.Gerarchia;
 import it.ingbs.ingegneria_software.model.gerarchie.GestoreGerarchie;
import it.ingbs.ingegneria_software.model.gerarchie.MenuGerarchie;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

/*
 * NUOVA CLASSE PER LA SUDDIVISIONE DEI COMPITI DEL CONFIGURATORE (Semplificazione della classe Sistema)
 */
public class GestoreMenu {
    
    
    private final String[] vociMenuBackEnd = new String[]{"GESTIONE COMPRENSORI","GESTIONE GERARCHIE","GESTIONE FATTORI CONVERSIONE"};

    private final String[] vociMenuFrontEnd = new String[]{"Visualizza Gerarchie"};
      
    private final MenuUtil menuBackEnd = new MenuUtil("MENU BACK-END:", vociMenuBackEnd);

    private final MenuUtil menuFrontEnd = new MenuUtil("MENU FRONT END",vociMenuFrontEnd);

    private GestoreComprensorio gestoreComprensorio = new GestoreComprensorio();
    private GestoreGerarchie gestoreGerarchia = new GestoreGerarchie();
    private HashMap<String, Gerarchia> radici = new HashMap<>();
    // private GestoreFattoriConversione gestoreFattori = new GestoreFattoriConversione();

    private File nomefile = new File("elaborato-ingegneria-software\\src\\Data_File\\elencoGerarchie.txt");


    /*
     * nuovo menu back end dove il configuratore sceglie su quale oggetto lavorare 
     */
    public void backEnd (Configuratore configuratore) throws Exception {
        int scelta=0;
        do {
            scelta = menuBackEnd.scegli();
            switch (scelta) { 
                case 1:
                gestoreComprensorio.modificaComprensori();
                break;

                case 2:
                radici = GestoreFileGerarchie.recuperaAlbero(nomefile);
                MenuGerarchie menuGerarchie = new MenuGerarchie(radici, nomefile);
                menuGerarchie.run();
                break;

                case 3:
                  //gestoreFattori.modificaFattori();
                break;
            }
        }while(scelta!=0);
    }
	

    /**
     * menu front end con le varie operazioni che il fruitore può eseguire 
     * @param fruitore
     * @throws Exception
     */

    public void frontEnd(Fruitore fruitore) throws Exception{
        int scelta=0;

        do{
            scelta=menuFrontEnd.scegli();
            switch(scelta){
                case 1: 
                //visualizzaGerarchie()
                break;

        }
     }while(scelta!=0);
  }
    


}
