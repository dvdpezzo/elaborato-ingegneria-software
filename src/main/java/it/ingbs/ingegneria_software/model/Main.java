package it.ingbs.ingegneria_software.model;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import it.ingbs.ingegneria_software.controller.Sistema;
import it.ingbs.ingegneria_software.gestione_accesso.MenuAccesso;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

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

    //creazione del menu di accesso 
    private final static String [] VOCI = {"ACCESSO CONFIGURATORE","ACCESSO FRUITORE"};
    static MenuUtil menuAccesso = new MenuUtil("MENU DI ACCESSO UTENTE:",VOCI);
    

	public static void main(String[] args) throws IOException {

        //oggetti di classi che gestiscono dati e informazioni
        GestoreFruitore gf = new GestoreFruitore();
	    GestoreComprensorio gc = new GestoreComprensorio();


        
		//creazione istanza del sistema generale:
		Sistema sistemaGenerale = new Sistema();

    
        boolean accesso=true;

       do{
           int opzione =menuAccesso.scegli();
           switch(opzione){
            
            case 1:
                Utente utente = MenuAccesso.loginConfiguratore();
                  if (utente instanceof Configuratore){
                    //se utente è di tipo Configuratore, richiama il menu back-end:
                    sistemaGenerale.backEnd((Configuratore) utente, true);
                  }
                  accesso=false;
                break;

            case 2:
            MenuAccesso.menuFruitore();
            accesso=false;
            break;
           }
        }while(accesso);
    }

}
