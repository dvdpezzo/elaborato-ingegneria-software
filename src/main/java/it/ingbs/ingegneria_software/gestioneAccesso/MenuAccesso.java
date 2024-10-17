package it.ingbs.ingegneria_software.gestioneAccesso;

import it.ingbs.ingegneria_software.model.Configuratore;
import it.ingbs.ingegneria_software.model.Utente;
import it.ingbs.ingegneria_software.utilitaGenerale.InputDati;

public class MenuAccesso {

    /**
     * chiede all'utente di inserire le credenziali per accedere al sistema
     * @return Configuratore valido se le credenziali sono corrette
     */
    public static Configuratore loginConfiguratore (){
        GestoreAccesso gestoreAccesso = new GestoreAccesso();
        Configuratore result;
        do {
            String nomeUtente= InputDati.leggiStringaNonVuota("Inserire nome utente: ");
            String passUtente= InputDati.leggiStringaNonVuota("Inserisci password: ");
            result = gestoreAccesso.accessoConfiguratore(nomeUtente, passUtente);
        } while (result == null);
        return result;
    }


}
