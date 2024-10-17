package it.ingbs.ingegneria_software.model;

import it.ingbs.ingegneria_software.controller.Sistema;
import it.ingbs.ingegneria_software.gestione_accesso.MenuAccesso;

public class Main {
	public static void main(String[] args) {

		//creazione istanza del sistema generale:
		Sistema sistemaGenerale = new Sistema();

		//chiedi credenziali d'accesso:
		Utente utente = MenuAccesso.loginConfiguratore();

		//da rivedere il modo in cui differenzia utente da configuratore, nella versione 2

		if (utente instanceof Configuratore){
			//se utente è di tipo Configuratore, richiama il menu back-end:
            sistemaGenerale.backEnd((Configuratore) utente, true);
		} else {
			//altrimenti richiama il menu utente:
			sistemaGenerale.frontEnd();
		}
		

	}

}
