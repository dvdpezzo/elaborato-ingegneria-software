package it.ingbs.ingegneria_software.controller;

import it.ingbs.ingegneria_software.model.richieste.GestoreRichieste;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class MenuGestoreRichieste implements Runnable {
    private final GestoreRichieste gestoreRichieste;
    private static final String TITOLO_MENU = "Gestione Richieste";
    private static final String[] VOCI_MENU = {
        "Visualizza richieste Chiuse",
        "Visualizza richieste Categoria"
    };

    public MenuGestoreRichieste(GestoreRichieste gestoreRichieste) {
        this.gestoreRichieste = gestoreRichieste;
    }

    @Override
    /**
     * Meenu delle richieste 
     */
    public void run(){
        int scelta;
        MenuUtil menuRichieste = new MenuUtil(TITOLO_MENU, VOCI_MENU);
        do{
            
            scelta = menuRichieste.scegli();
            switch(scelta){
                case 1:
                      gestoreRichieste.visualizzaRichiesteChiuse();
                    break;
                case 2:
                      gestoreRichieste.visualizzaRichiesteCategoria();
                    break;
            }
        }while(scelta!=0);
    }

}
