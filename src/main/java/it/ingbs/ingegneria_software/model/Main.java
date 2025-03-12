package it.ingbs.ingegneria_software.model;

import it.ingbs.ingegneria_software.controller.GestoreMenu;
import it.ingbs.ingegneria_software.gestione_accesso.MenuAccesso;
import it.ingbs.ingegneria_software.model.utenti.Configuratore;
import it.ingbs.ingegneria_software.model.utenti.Utente;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class Main {
	
	public static void main(String[] args) throws Exception {

        
		//creazione istanza del sistema generale:
		GestoreMenu sistemaGenerale = new GestoreMenu();

	/**
     * menu per l'accesso del fruitore o configuratore
     */

	 final String [] VOCI ={"ACCESSO CONFIGURATORE","ACCESSO FRUITORE"};
	 MenuUtil menu = new MenuUtil("Seleziona la tua identità", VOCI);
	 boolean valore = true; 
	 do{
		 int opzione = menu.scegli();
		 switch(opzione){
			 case 1: 
			 Utente configuratore = (Utente) MenuAccesso.loginConfiguratore();
			 if(configuratore instanceof Configuratore){
				sistemaGenerale.backEnd((Configuratore) configuratore);
			 }
			 break; 

			 case 2: 
			 MenuAccesso.menuFruitore();
			 break; 
		 }
	 }while(valore);
		


  
    } 
}


