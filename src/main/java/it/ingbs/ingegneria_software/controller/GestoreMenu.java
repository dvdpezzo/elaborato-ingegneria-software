package it.ingbs.ingegneria_software.controller;

import it.ingbs.ingegneria_software.model.Configuratore;
import it.ingbs.ingegneria_software.model.GestoreComprensorio;
import it.ingbs.ingegneria_software.model.GestoreFattori;
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
    private final GestoreFattori gestoreFattori = new GestoreFattori();




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
                MenuGerarchie menuGerarchie = new MenuGerarchie();
                menuGerarchie.run();
                break;

                case 3:
                gestoreComprensorio.visualizzaComprensori();
                break;

                case 4:
                gestoreFattori.modificaFattori();
                break;
            }
        }while(scelta!=0);
    }
	
    


}
