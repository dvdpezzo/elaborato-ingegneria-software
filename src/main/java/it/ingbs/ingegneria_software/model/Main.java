package it.ingbs.ingegneria_software.model;

import java.io.IOException;
import java.util.logging.*;
import it.ingbs.ingegneria_software.controller.GestoreMenu;
import it.ingbs.ingegneria_software.gestione_accesso.MenuAccesso;

public class Main {
	//setup logger: non toccare
	static {
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.INFO);
        for (Handler handler : rootLogger.getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                handler.setFormatter(new SimpleFormatter() {
                    @Override
                    public synchronized String format(java.util.logging.LogRecord aRecord) {
                        return String.format("%s%s%n", 
                                             aRecord.getMessage(), 
                                             aRecord.getThrown() == null ? "" : aRecord.getThrown().toString());
                    }
                });
            }
        }
    }
	
	public static void main(String[] args) throws IOException {


		//creazione istanza del sistema generale:
		GestoreMenu sistemaGenerale = new GestoreMenu();

		//chiedi credenziali d'accesso:
		Utente utente = MenuAccesso.loginConfiguratore();

		//da rivedere il modo in cui differenzia utente da configuratore, nella versione 2

		if (utente instanceof Configuratore){
			//se utente è di tipo Configuratore, richiama il menu back-end:
            sistemaGenerale.backEnd((Configuratore) utente);
		} else {
			//altrimenti richiama il menu utente:
			//sistemaGenerale.frontEnd();
		}
		

	}

}
