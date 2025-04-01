package it.ingbs.ingegneria_software.controller;

import it.ingbs.ingegneria_software.model.comprensori.GestoreComprensorio;
import it.ingbs.ingegneria_software.model.fattori.GestoreFattori;
import it.ingbs.ingegneria_software.model.gerarchie.GestoreGerarchie;
import it.ingbs.ingegneria_software.model.gerarchie.MenuGerarchie;
import it.ingbs.ingegneria_software.model.utenti.Configuratore;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class GestoreMenu {
    
    
    private final String[] vociMenuBackEnd = new String[]{"GESTIONE GERARCHIE","GESTIONE FATTORI CONVERSIONE","GESTIONE COMPRENSORI"};
    private final MenuUtil menuBackEnd = new MenuUtil("MENU BACK-END:", vociMenuBackEnd);
    private final GestoreGerarchie gestoreGerarchie ;
    private final GestoreFattori gestoreFattori;
    private final GestoreComprensorio gestoreComprensorio; 

    public GestoreMenu(GestoreGerarchie gestoreGerarchia, GestoreFattori gestoreFattori, GestoreComprensorio gestoreComprensorio) {
        this.gestoreGerarchie = gestoreGerarchia;
        this.gestoreFattori = gestoreFattori;
        this.gestoreComprensorio = gestoreComprensorio;
        
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
	
}