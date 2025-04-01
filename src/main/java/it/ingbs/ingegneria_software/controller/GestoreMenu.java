package it.ingbs.ingegneria_software.controller;


import it.ingbs.ingegneria_software.model.GestoreRichieste;
import it.ingbs.ingegneria_software.model.comprensori.GestoreComprensorio;
import it.ingbs.ingegneria_software.model.fattori.GestoreFattori;
import it.ingbs.ingegneria_software.model.gerarchie.GestoreGerarchie;
import it.ingbs.ingegneria_software.model.gerarchie.MenuGerarchie;
import it.ingbs.ingegneria_software.model.utenti.Configuratore;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

/*
 * NUOVA CLASSE PER LA SUDDIVISIONE DEI COMPITI DEL CONFIGURATORE (Semplificazione della classe Sistema)
 */
public class GestoreMenu {
    
    
    private final String[] vociMenuBackEnd = new String[]{"GESTIONE GERARCHIE","GESTIONE FATTORI CONVERSIONE","GESTIONE COMPRENSORI"};
    private final String[] vociMenuFrontEnd = new String[]{"Visualizza Gerarchie","Effettua una richiesta","Visualizza richieste"};      
    private final MenuUtil menuBackEnd = new MenuUtil("MENU BACK-END:", vociMenuBackEnd);
    private final MenuUtil menuFrontEnd = new MenuUtil("MENU FRONT END",vociMenuFrontEnd);
    private final GestoreGerarchie gestoreGerarchie ;
    private final GestoreFattori gestoreFattori;
    private final GestoreComprensorio gestoreComprensorio;
    private final GestoreRichieste gestoreRichieste ;   

    public GestoreMenu(GestoreGerarchie gestoreGerarchia, GestoreFattori gestoreFattori, GestoreComprensorio gestoreComprensorio, GestoreRichieste gestoreRichieste) {
        this.gestoreGerarchie = gestoreGerarchia;
        this.gestoreFattori = gestoreFattori;
        this.gestoreComprensorio = gestoreComprensorio;
        this.gestoreRichieste = gestoreRichieste;
        
    }
   
    public void backEnd (Configuratore configuratore) throws Exception {
        int scelta;
        do {
            scelta = menuBackEnd.scegli();
            switch (scelta) {            

                case 1:
                MenuGerarchie menuGerarchie = new MenuGerarchie(this.gestoreGerarchie);
                menuGerarchie.run();
                break;

                case 2:
                gestoreFattori.modificaFattori();
                break;

                case 3:
                gestoreComprensorio.modificaComprensori();
                break;
            }
        }while(scelta!=0);
    }
	

    /**
     * menu front end con le varie operazioni che il fruitore può eseguire 
     * @param fruitore
     * @throws Exception
     */

    public void frontEnd(Fruitore fruitore) throws Exception {
        int scelta;
        do {
            scelta = menuFrontEnd.scegli();
            switch (scelta) {
                case 1:
                    gestoreGerarchie.stampaGerarchie();
                    break;

                case 2:
                    gestoreRichieste.nuovaRichiesta(fruitore);
                    break;
                
                case 3:
                    gestoreRichieste.visualizzaRichieste(fruitore);
                    break;
            }
        } while (scelta != 0);
    }

}
