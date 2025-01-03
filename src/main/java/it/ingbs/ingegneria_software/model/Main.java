package it.ingbs.ingegneria_software.model;

import java.io.IOException;
import it.ingbs.ingegneria_software.controller.GestoreMenu;
import it.ingbs.ingegneria_software.gestione_accesso.MenuAccesso;

public class Main {
	
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
