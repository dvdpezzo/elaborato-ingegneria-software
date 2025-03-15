package it.ingbs.ingegneria_software.gestione_accesso;

import it.ingbs.ingegneria_software.controller.GestoreMenu;
import it.ingbs.ingegneria_software.model.utenti.Configuratore;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class MenuAccesso {

    private static final String INSERISCI_PASSWORD = "Inserisci password: ";
    private static final String INSERIRE_NOME_UTENTE = "Inserire nome utente: ";
    private final GestoreAccesso gestoreAccesso;
    public MenuAccesso(GestoreAccesso gestoreAccesso) {
        this.gestoreAccesso = gestoreAccesso;
    }
    /**
     * chiede all'utente di inserire le credenziali per accedere al sistema
     * @return Configuratore valido se le credenziali sono corrette
     */
    public Configuratore loginConfiguratore (){
        Configuratore result;
        do {
            String nomeUtente= InputDati.leggiStringaNonVuota(INSERIRE_NOME_UTENTE);
            String passUtente= InputDati.leggiStringaNonVuota(INSERISCI_PASSWORD);
            result = gestoreAccesso.accessoConfiguratore(nomeUtente, passUtente);
        } while (result == null);
        return result;
    }


     /**
     * chiede all'utente di inserire le credenziali per accedere al sistema
     * @return Fruitore valido se le credenziali sono corrette
     */

    public Fruitore loginFruitore(GestoreAccesso gestoreAccesso){
        Fruitore result = null;
        do {
            String nomeUtente = InputDati.leggiStringaNonVuota(INSERIRE_NOME_UTENTE);
            String passUtente = InputDati.leggiStringaNonVuota(INSERISCI_PASSWORD);
            result = gestoreAccesso.accessoFruitore(nomeUtente, passUtente);
        } while (result == null);
        return result;
    }

    /**
     * menu per l'accesso del fruitore
          * @throws Exception 
          */
     
    public void menuFruitore(GestoreMenu sistemaGenerale) throws Exception {
        final String[] VOCI = {"ACCESSO UTENTE", "REGISTRAZIONE UTENTE"};
        MenuUtil menu = new MenuUtil("Seleziona la modalità di accesso:", VOCI);
        boolean valore = true;
        do {
            int opzione = menu.scegli();
            switch (opzione) {
                case 1:
                    Fruitore fruit = loginFruitore(gestoreAccesso);
                    if (fruit instanceof Fruitore) {
                        sistemaGenerale.frontEnd((Fruitore) fruit);
                    }
                    valore = false;
                    break;

                case 2:
                    Fruitore result = gestoreAccesso.registrazioneNuovoFruitore();
                    if (result instanceof Fruitore) {
                        sistemaGenerale.frontEnd((Fruitore) result);
                    }
                    valore = false;
                    break;
            }
        } while (valore);
    }
}
