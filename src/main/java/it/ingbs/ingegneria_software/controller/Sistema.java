package it.ingbs.ingegneria_software.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.ingbs.ingegneria_software.model.Configuratore;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class Sistema {

	private final Logger logSistema = Logger.getLogger(getClass().getName());
	private final String[] vociMenuBackEnd = new String[]{"AGGIUNGI COMUNE","AGGIUNGI COMPRENSORIO", 
												"AGGIUNGI NUOVA GERARCHIA",
												"AGGIUNGI CATEGORIA A GERARCHIA ESISTENTE",
												"VISUALIZZA",
												"SALVA"};
      
	private final MenuUtil menuBackEnd = new MenuUtil("MENU BACK-END:", vociMenuBackEnd);
	

	/**
	 * Menu accessibile ai soli configuratori, viene mostrato finchè isLoggedIn è true
	 * @param configuratore Configuratore che ha effettuato l'accesso
	 * @param isLoggedIn
	 */
    public void backEnd (Configuratore configuratore, boolean isLoggedIn) {
		do {
			int scelta = menuBackEnd.scegli();
            switch (scelta) { 
				case 1: //AGGIUNGI COMUNE
					boolean risposta;											
					// chiede a quale comprensorio lo voglio aggiungere
					configuratore.visualizzaComprensori();
					int codiceComprensorio = InputDati.leggiIntero("Inserisci il codice del comprensorio al quale si vuole aggiungere il comune:");					
					do{
						configuratore.aggiungiComune(InputDati.leggiStringa("Inserisci il nome del comune:"), codiceComprensorio);
						risposta = InputDati.yesOrNo("Vuoi aggiungere un altro comune?");
					}while (risposta);						
					
				break;

				case 2: //AGGIUNGI COMPRENSORIO
					configuratore.creaComprensorioGeografico();
					configuratore.visualizzaComprensori();
					
				break;
				case 3: //AGGIUNGI NUOVA GERARCHIA
					// List<Categoria> gerarchia = configuratore.creaGerarchia();
					logSistema.info( "Nuova gerarchia creata.");
				break;
				case 4: //AGGIUNGI CATEGORIA A GERARCHIA ESISTENTE
					// Categoria categoria = configuratore.creaCategoria();
					logSistema.log(Level.INFO, "Categoria aggiunta a gerarchia esistente.");
				break;
				case 5: //VISUALIZZA
					String[] vociMenuVisualizzazione = new String[]{"COMPRENSORI", "GERARCHIA", "FATTIori DI CONVERSIONE"};
					MenuUtil menuVisualizza = new MenuUtil("MENU DI VISUALIZZAZIONE:", vociMenuVisualizzazione);
				
					int sceltaVisualizza = menuVisualizza.scegli();
					switch (sceltaVisualizza) {
						case 1: configuratore.visualizzaComprensori(); break;
						case 2: configuratore.visualizzaGerarchia(); break;
						case 3: configuratore.visualizzaFattoriConversione(); break;
					}
				break;
				case 6: //SALVA 
					configuratore.salvaCambiamenti();
					logSistema.log(Level.INFO, "Cambiamenti salvati.");
				break;
				case 0: 
					isLoggedIn = false;
					logSistema.log(Level.INFO, "Logout effettuato.");

				break;
			}		
		} while (isLoggedIn);
	}

	    public void frontEnd () {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
