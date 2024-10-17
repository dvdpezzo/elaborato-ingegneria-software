package it.ingbs.ingegneria_software.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.ingbs.ingegneria_software.model.Categoria;
import it.ingbs.ingegneria_software.model.Configuratore;
import it.ingbs.ingegneria_software.utilitaGenerale.InputDati;
import it.ingbs.ingegneria_software.utilitaGenerale.MenuUtil;

public class Sistema {

	public final Logger logSistema;
	
	public Sistema() {
		this.logSistema = Logger.getLogger(getClass().getName());
	}

    public void menuBackEnd(Configuratore configuratore, boolean isLoggedIn) {     
		
		String[] vociMenuBackEnd = new String[]{"AGGIUNGI COMUNE","AGGIUNGI COMPRENSORIO", 
												"AGGIUNGI NUOVA GERARCHIA",
												"AGGIUNGI CATEGORIA A GERARCHIA ESISTENTE",
												"VISUALIZZA",
												"SALVA"};
      
		MenuUtil menuBackEnd = new MenuUtil("MENU BACK-END:", vociMenuBackEnd);
		do {
			int scelta = menuBackEnd.scegli();
            switch (scelta) { 
				case 1:
					boolean aggiungiComune;
					do{
						if(InputDati.yesOrNo("Vuoi aggiungere un comune?")){
							// chiede a quale comprensorio lo voglio aggiungere, se non ne esiste ancora nessuno
							int codiceComprensorio = InputDati.leggiIntero("Inserisci il codice del comprensorio al quale si vuole aggiungere il comune:");
							// lo rimando alla creazione del comprensorio geografico
							configuratore.aggiungiComune(InputDati.leggiStringa("Inserisci il nome del comune:"), codiceComprensorio);
							aggiungiComune=true;
						}   
						else aggiungiComune=false;     
					}while(aggiungiComune);
				break;

				case 2:
					// ComprensorioGeografico comprensorio = configuratore.creaComprensorioGeografico();
					logSistema.log(Level.INFO, "Comprensorio aggiunto.");
				break;
				case 3:
					List<Categoria> gerarchia = configuratore.creaGerarchia();
					logSistema.log(Level.INFO, "Nuova gerarchia creata.");
				break;
				case 4:
					Categoria categoria = configuratore.creaCategoria();
					logSistema.log(Level.INFO, "Categoria aggiunta a gerarchia esistente.");
				break;
				case 5:
					String[] vociMenuVisualizzazione = new String[]{"COMPRENSORI", "GERARCHIA", "FATTIori DI CONVERSIONE"};
					MenuUtil menuVisualizza = new MenuUtil("MENU DI VISUALIZZAZIONE:", vociMenuVisualizzazione);
				
					int sceltaVisualizza = menuVisualizza.scegli();
					switch (sceltaVisualizza) {
						case 1: configuratore.visualizzaComprensori(); break;
						case 2: configuratore.visualizzaGerarchia(); break;
						case 3: configuratore.visualizzaFattoriConversione(); break;
					}
				break;
				case 6: 
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

    public void menuUtente() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
