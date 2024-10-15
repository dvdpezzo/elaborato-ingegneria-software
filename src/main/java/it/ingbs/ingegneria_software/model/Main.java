package it.ingbs.ingegneria_software.model;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import it.ingbs.ingegneria_software.controller.Sistema;
import it.ingbs.ingegneria_software.gestioneAccesso.MenuAccesso;

public class Main {
	public static void main(String[] args) {

		//creazione istanza del sistema generale:
		Sistema sistemaGenerale = new Sistema();

		//chiedi credenziali d'accesso:
		Utente utente = MenuAccesso.avvioLogin();

		if (utente instanceof Configuratore){
			//se utente è di tipo Configuratore, richiama il menu back-end:
            sistemaGenerale.menuBackEnd((Configuratore) utente, true);
		} else {
			//altrimenti richiama il menu utente:
			sistemaGenerale.menuUtente();
		}
		

	}

}
