package it.ingbs.ingegneria_software.model;

import it.ingbs.ingegneria_software.controller.GestoreMenu;
import it.ingbs.ingegneria_software.gestione_accesso.MenuAccesso;

public class Main {
	
	public static void main(String[] args) throws Exception {


		//creazione istanza del sistema generale:
		GestoreMenu sistemaGenerale = new GestoreMenu();

		
		Utente configuratore = MenuAccesso.loginConfiguratore();

		Utente fruitore = MenuAccesso.loginFruitore();

		//da rivedere il modo in cui differenzia utente da configuratore, nella versione 2

		if (configuratore instanceof Configuratore){
			//se utente è di tipo Configuratore, richiama il menu back-end:
            sistemaGenerale.backEnd((Configuratore) configuratore);
		} else {
			
			sistemaGenerale.frontEnd((Fruitore) fruitore);
		}
		

	}

}
