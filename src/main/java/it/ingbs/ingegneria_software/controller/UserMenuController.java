package it.ingbs.ingegneria_software.controller;


import it.ingbs.ingegneria_software.model.comprensori.GestoreComprensorio;
import it.ingbs.ingegneria_software.model.comprensori.GestoreComuni;
import it.ingbs.ingegneria_software.model.fattori.GestoreFattori;
import it.ingbs.ingegneria_software.model.gerarchie.GestoreGerarchie;
import it.ingbs.ingegneria_software.model.richieste.GestoreRichieste;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;


public class UserMenuController {
    
    
    private final String[] vociMenuBackEnd = new String[]{"GESTIONE GERARCHIE","GESTIONE FATTORI CONVERSIONE","GESTIONE COMPRENSORI","GESTIONE RICHIESTE","GESTIONE COMUNI"};
    private final String[] vociMenuFrontEnd = new String[]{"Visualizza Gerarchie","Effettua una richiesta","Visualizza richieste","Ritira una richiesta"};      
    private final MenuUtil menuBackEnd = new MenuUtil("MENU BACK-END:", vociMenuBackEnd);
    private final MenuUtil menuFrontEnd = new MenuUtil("MENU FRONT END",vociMenuFrontEnd);
    private final GestoreGerarchie gestoreGerarchie ;
    private final GestoreFattori gestoreFattori;
    private final GestoreComprensorio gestoreComprensorio;
    private final GestoreRichieste gestoreRichieste ;   
    private final GestoreComuni gestoreComuni;

    public UserMenuController(ServiceProvider serviceFactory) {
        this.gestoreGerarchie = serviceFactory.getGestoreGerarchie();
        this.gestoreFattori = serviceFactory.getGestoreFattori();
        this.gestoreComprensorio = serviceFactory.getGestoreComprensorio();
        this.gestoreRichieste = serviceFactory.getGestoreRichieste();
        this.gestoreComuni = serviceFactory.getGestoreComuni();
        
        
    }
   
    public void backEnd () {
        int scelta;
        do {
            scelta = menuBackEnd.scegli();
            switch (scelta) {            

                case 1:
                MenuGerarchie menuGerarchie = new MenuGerarchie(this.gestoreGerarchie);
                menuGerarchie.run();
                break;

                case 2:
                MenuFattori menuFattori = new MenuFattori(this.gestoreFattori);
                menuFattori.run();
                break;

                case 3:
                MenuComprensorio menuComprensorio = new MenuComprensorio(this.gestoreComprensorio);
                menuComprensorio.run();
                break;

                case 4:
                MenuRichieste menuRichieste = new MenuRichieste(this.gestoreRichieste);
                menuRichieste.run();
                break;

                case 5:
                MenuComuni menuComuni = new MenuComuni(this.gestoreComuni);
                menuComuni.run();
                break;
            }
        }while(scelta!=0);
    }
	

    /**
     * menu front end con le varie operazioni che il fruitore può eseguire 
     * @param fruitore
     */

    public void frontEnd(Fruitore fruitore) {
        gestoreRichieste.setFruitore(fruitore); // Imposta il fruitore corrente
        int scelta;
        do {
            scelta = menuFrontEnd.scegli();
            switch (scelta) {
                case 1:
                    gestoreGerarchie.view();
                    break;

                case 2:                    
                    gestoreRichieste.aggiungi();
                    break;
                
                case 3:
                    gestoreRichieste.view();
                    break;
                
                case 4: 
                    gestoreRichieste.rimuovi();
                    break;
            }
        } while (scelta != 0);
    }

}