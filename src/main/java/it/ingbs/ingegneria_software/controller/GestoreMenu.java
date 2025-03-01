package it.ingbs.ingegneria_software.controller;

import java.io.File;
import java.util.HashMap;

import it.ingbs.ingegneria_software.gestione_file.GestoreFileGerarchie;
import it.ingbs.ingegneria_software.model.Configuratore;
import it.ingbs.ingegneria_software.model.GestoreComprensorio;
import it.ingbs.ingegneria_software.model.gerarchie.Gerarchia;
import it.ingbs.ingegneria_software.model.gerarchie.MenuGerarchie;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

/*
 * NUOVA CLASSE PER LA SUDDIVISIONE DEI COMPITI DEL CONFIGURATORE (Semplificazione della classe Sistema)
 */
public class GestoreMenu {
    
    
    private final String[] vociMenuBackEnd = new String[]{"GESTIONE COMPRENSORI","GESTIONE GERARCHIE","VISUALIZZA COMPRENSORI",
"GESTIONE FATTORI CONVERSIONE"};
      
    private final MenuUtil menuBackEnd = new MenuUtil("MENU BACK-END:", vociMenuBackEnd);

    private final GestoreComprensorio gestoreComprensorio = new GestoreComprensorio(); 
    // private GestoreFattoriConversione gestoreFattori = new GestoreFattoriConversione();

    private final File nomefile = new File("src\\Data_File\\elencoGerarchie.txt");


    /*
     * nuovo menu back end dove il configuratore sceglie su quale oggetto lavorare 
     */
    public void backEnd (Configuratore configuratore) throws Exception {
        int scelta;
        do {
            scelta = menuBackEnd.scegli();
            switch (scelta) { 
                case 1:
                gestoreComprensorio.modificaComprensori();
                break;

                case 2:
                HashMap<String, Gerarchia> radici = GestoreFileGerarchie.recuperaAlbero(nomefile);
                MenuGerarchie menuGerarchie = new MenuGerarchie(radici, nomefile);
                menuGerarchie.run();
                break;

                case 3:
                gestoreComprensorio.visualizzaComprensori();
                break;

                case 4:
               // gestoreFattori.modificaFattori();
                break;
            }
        }while(scelta!=0);
    }
	
    


}
