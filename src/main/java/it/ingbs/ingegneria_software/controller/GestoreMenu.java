package it.ingbs.ingegneria_software.controller;

import java.io.IOException;
import it.ingbs.ingegneria_software.model.Configuratore;
import it.ingbs.ingegneria_software.model.GestoreComprensorio;
import it.ingbs.ingegneria_software.model.GestoreGerarchia;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

/*
 * NUOVA CLASSE PER LA SUDDIVISIONE DEI COMPITI DEL CONFIGURATORE (Semplificazione della classe Sistema)
 */
public class GestoreMenu {
    
    
	private final String[] vociMenuBackEnd = new String[]{"GESTIONE COMPRENSORI","GESTIONE GERARCHIE","VISUALIZZA COMPRENSORI",
"VISUALIZZA GERARCHIE"};
      
	private final MenuUtil menuBackEnd = new MenuUtil("MENU BACK-END:", vociMenuBackEnd);

    private GestoreComprensorio gestoreComprensorio = new GestoreComprensorio();
    private GestoreGerarchia gestoreGerarchia = new GestoreGerarchia();


    /*
     * nuovo menu back end dove il configuratore sceglie su quale oggetto lavorare 
     */
    public void backEnd (Configuratore configuratore) throws IOException {
        int scelta=0;
		do {
		    scelta = menuBackEnd.scegli();
            switch (scelta) { 
                case 1:
                gestoreComprensorio.modificaComprensori();
                break;

                case 2:
                gestoreGerarchia.modificaGerarchie();
                break;

                case 3:
                gestoreComprensorio.visualizzaComprensori();
                break;

                case 4:
               // gestoreGerarchia.visualizzaGerarchie();
            }
        }while(scelta!=0);
    }
	
    


}
