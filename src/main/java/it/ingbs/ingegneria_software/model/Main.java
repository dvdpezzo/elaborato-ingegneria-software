package it.ingbs.ingegneria_software.model;


public class Main {

    public static void main(String[] args) {

        // Creazione del sistema
        Sistema sistema = Sistema.getInstance(); // Corretto l'uso del singleton
        
        // Carico salvataggi
        sistema.caricaSalvataggi();

        // Schermata login di accesso in cui si seleziona se configuratore o fruitore
        String tipoUtente = sistema.login();

        // Mostro menu a seconda della scelta o backend o frontend
        if (tipoUtente != null) {
            sistema.mostraMenu(tipoUtente);
        }

        //terminazione del programma

        //salvataggio dei dati
        sistema.salvaDati();

    }

   
}


