package it.ingbs.ingegneria_software.controller;

import it.ingbs.ingegneria_software.model.GestoreFattori;
import it.ingbs.ingegneria_software.model.comprensori.GestoreComprensorio;
import it.ingbs.ingegneria_software.model.gerarchie.MenuGerarchie;
import it.ingbs.ingegneria_software.model.utenti.Configuratore;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

/*
 * Classe per la gestione delle funzionalità backend (implementate) e le funzionalità frontend (da implementare nella versione 2)
 */
public class GestoreMenu {
    
    
    private final String[] vociMenuBackEnd = new String[]{"GESTIONE COMPRENSORI","GESTIONE GERARCHIE","GESTIONE FATTORI CONVERSIONE"};      
    private final MenuUtil menuBackEnd = new MenuUtil("MENU BACK-END:", vociMenuBackEnd);

    private final GestoreComprensorio gestoreComprensorio = new GestoreComprensorio(); 
    private final GestoreFattori gestoreFattori = new GestoreFattori();

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
                gestoreFattori.modificaFattori();
                break;
            }
        }while(scelta!=0);
    }
	
    


}
