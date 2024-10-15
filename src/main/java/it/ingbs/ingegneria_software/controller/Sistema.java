package it.ingbs.ingegneria_software.controller;

import java.util.List;

import it.ingbs.ingegneria_software.model.*;
import it.ingbs.ingegneria_software.utilitaGenerale.*;

public class Sistema {

    public void menuBackEnd(Configuratore configuratore, boolean isLoggedIn) {       

    	String[] vociMenuBackEnd = new String[]{"AGGIUNGI COMUNE","AGGIUNGI COMPRENSORIO", 
												"AGGIUNGI NUOVA GERARCHIA",
												"AGGIUNGI CATEGORIA A GERARCHIA ESISTENTE",
												"VISUALIZZA",
												"SALVA",
												"LOGOUT"};
      
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
					ComprensorioGeografico comprensorio = configuratore.creaComprensorioGeografico();
					System.out.println("Comprensorio aggiunto.");
				break;
				case 3:
					List<Categoria> gerarchia = configuratore.creaGerarchia();
					System.out.println("Nuova gerarchia creata.");
				break;
				case 4:
					Categoria categoria = configuratore.creaCategoria();
					System.out.println("Categoria aggiunta a gerarchia esistente.");
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
					System.out.println("Cambiamenti salvati.");
				break;
				case 0: 
					isLoggedIn = false;
					System.out.println("Logout effettuato.");
				break;
			}		
		} while (isLoggedIn);
	}
    public void creaConversione(){}
    public void controlloConversione(){}
    public void primoAccesso(){}
    public void controlloAccesso(){}

    public void menuUtente() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
