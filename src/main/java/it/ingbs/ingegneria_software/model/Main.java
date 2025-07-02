package it.ingbs.ingegneria_software.model;

import it.ingbs.ingegneria_software.Eccezioni.LoadException;

public class Main {

    public static void main(String[] args) throws LoadException {

        // Creazione del sistema
        SistemaController sistema = SistemaController.getInstance();
        
        // Carica salvataggi
        sistema.caricaSalvataggi();

        // Schermata login di accesso in cui si seleziona se configuratore o fruitore
        String tipoUtente = sistema.login();

        // Mostro menu a seconda della scelta o backend o frontend
        if (tipoUtente != null) {
            sistema.mostraMenu(tipoUtente);
        }
        //salvataggio dei dati
        sistema.salvaDati();

    }
   
}


