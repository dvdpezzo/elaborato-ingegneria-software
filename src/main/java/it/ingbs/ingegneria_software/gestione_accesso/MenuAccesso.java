package it.ingbs.ingegneria_software.gestione_accesso;

import it.ingbs.ingegneria_software.model.Configuratore;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;

public class MenuAccesso {

    private MenuAccesso() {
        throw new IllegalStateException("Utility class");
      }
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
