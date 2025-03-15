package it.ingbs.ingegneria_software.model;

import java.io.IOException;

import it.ingbs.ingegneria_software.controller.GestoreMenu;
import it.ingbs.ingegneria_software.gestione_accesso.GestoreAccesso;
import it.ingbs.ingegneria_software.gestione_accesso.MenuAccesso;
import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.gestione_file.GestoreFile;
import it.ingbs.ingegneria_software.model.comprensori.GestoreComprensorio;
import it.ingbs.ingegneria_software.model.fattori.GestoreFattori;
import it.ingbs.ingegneria_software.model.gerarchie.GestoreGerarchie;
import it.ingbs.ingegneria_software.model.utenti.Configuratore;
import it.ingbs.ingegneria_software.model.utenti.Utente;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class Main {

    public static void main(String[] args) {

        final String[] VOCI = {"ACCESSO CONFIGURATORE", "ACCESSO FRUITORE"};
        MenuUtil menu = new MenuUtil("Seleziona la tua identità", VOCI);

        try {
            //creazione dati run-time da file
            GestoreFile gestoreFile = new GestoreFile();
            gestoreFile.caricaSalvataggio();
            GestoreDati gestoreDati = gestoreFile.getGestoreDati();

            //creazione oggetti principali
            GestoreGerarchie gestoreGerarchie = new GestoreGerarchie(gestoreDati.getGerarchie(), gestoreFile);
            GestoreFattori gestoreFattori = new GestoreFattori(gestoreDati.getFattori(), gestoreGerarchie, gestoreFile);
            GestoreComprensorio gestoreComprensorio = new GestoreComprensorio(gestoreDati.getComprensori(), gestoreFile);
            GestoreRichieste gestoreRichieste = new GestoreRichieste(gestoreFile, gestoreDati.getRichieste());
            GestoreMenu sistemaGenerale = new GestoreMenu(gestoreGerarchie, gestoreFattori, gestoreComprensorio, gestoreRichieste);
            GestoreAccesso sistemaAccesso = new GestoreAccesso(gestoreDati, gestoreFile, gestoreComprensorio);
            sistemaAccesso.caricaDatiFruitori();

            //Creazione MenuAccesso
            MenuAccesso menuAccesso = new MenuAccesso(sistemaAccesso);
            int opzione;
            do {
                opzione = menu.scegli();
                switch (opzione) {
                    case 1:
                        Utente configuratore = menuAccesso.loginConfiguratore();
                        if (configuratore instanceof Configuratore) {
                            sistemaGenerale.backEnd((Configuratore) configuratore);
                        }
                        break;

                    case 2:
                        menuAccesso.menuFruitore(sistemaGenerale);
                        break;
                }
            } while (opzione != 0);
            gestoreFile.creaSalvataggio();

        } catch (IOException ex) {
            System.out.println("Errore nel caricamento dei dati");
        } catch (Exception ex) {
            System.out.println("Errore generico");
        }

    }
}


