package it.ingbs.ingegneria_software.gestioneAccesso;

import it.ingbs.ingegneria_software.utilitaGenerale.*;

/* MENU DI ACCESSO MOLTO BASILARE, BISOGNA CREARE UNA CLASSE DI GESTIONE DEL FILE DI ACCESO (UNA MAPPA) */

public class MenuAccesso {

    private static final String [] AZIONI_POSSIBILI ={"ACCESSO COORDINATORE", "ACCESSO FRUITORE"};

    public void avvio(GestoreAccesso gestoreAccesso) {
        int scelta;
        MenuUtil menuLogin = new MenuUtil("MENU DI ACCESSO:", AZIONI_POSSIBILI);
        do {
            scelta = menuLogin.scegli();
            switch(scelta){
                //accesso coordinatore
                case 1:
                    String nomeConfig= InputDati.leggiStringaNonVuota("Inserire nome utente: ");
                    String passConfig= InputDati.leggiStringaNonVuota("Inserisci password: ");
                    gestoreAccesso.accessoUtente(nomeConfig, passConfig);
                    break;
                //accesso fruitore
                case 2:
                    String nomeFruit= InputDati.leggiStringaNonVuota("Inserire nome utente: ");
                    String passFruit= InputDati.leggiStringaNonVuota("Inserisci password: ");
                    gestoreAccesso.accessoUtente(nomeFruit, passFruit);
                    break;
            }
        }while(scelta!=0);
    }
}
