package it.ingbs.ingegneria_software.model;

import java.io.IOException;

import it.ingbs.ingegneria_software.Eccezioni.CampoNotFoundException;
import it.ingbs.ingegneria_software.Eccezioni.CategoriaNotFoundException;
import it.ingbs.ingegneria_software.Eccezioni.CategoriaOmonimaException;
import it.ingbs.ingegneria_software.Eccezioni.IllegalCampoException;
import it.ingbs.ingegneria_software.Eccezioni.PadreNotFoundException;
import it.ingbs.ingegneria_software.controller.GestoreMenu;
import it.ingbs.ingegneria_software.gestione_accesso.GestoreAccesso;
import it.ingbs.ingegneria_software.gestione_accesso.ControlloAccesso;
import it.ingbs.ingegneria_software.gestione_accesso.GestoreAccessoConfiguratore;
import it.ingbs.ingegneria_software.gestione_accesso.GestoreAccessoFruitore;
import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.gestione_file.GestoreFile;
import it.ingbs.ingegneria_software.model.comprensori.GestoreComprensorio;
import it.ingbs.ingegneria_software.model.fattori.GestoreFattori;
import it.ingbs.ingegneria_software.model.gerarchie.GestoreGerarchie;
import it.ingbs.ingegneria_software.model.richieste.GestoreRichieste;
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
            GestoreAccessoConfiguratore sistemaAccessoConf = new GestoreAccessoConfiguratore(gestoreDati, gestoreFile, gestoreComprensorio);
            GestoreAccessoFruitore sistemaAccessoFruit = new GestoreAccessoFruitore(gestoreDati, gestoreFile, gestoreComprensorio);
            sistemaAccesso.caricaDatiFruitori();

            //Creazione MenuAccesso
            ControlloAccesso menuAccesso = new ControlloAccesso();
            int opzione;
            do {
                opzione = menu.scegli();
                switch (opzione) {
                    case 1:
                        Configuratore configuratore = menuAccesso.login(new GestoreAccessoUtente<Configuratore>() {
                            @Override
                            public Configuratore accesso(String nomeUtente, String password) {
                                return sistemaAccesso.loginConfiguratore(nomeUtente, password);
                            }

                            @Override
                            public Configuratore registrazioneNuovoUtente() {
                                return null; // Non necessario per il login
                            }
                        });
                        sistemaGenerale.backEnd();
                        break; // Aggiunto il break per evitare di eseguire il case successivo

                    case 2:
                        menuAccesso.menuFruitore(sistemaGenerale);
                        break;
                }
            } while (opzione != 0);
            gestoreFile.creaSalvataggio();

     } catch (IOException ex) {
            System.out.println("Errore nel caricamento dei dati");
        } catch (CampoNotFoundException ex) {
            System.out.println("Errore generico");
        } catch(CategoriaNotFoundException ex) {
            System.out.println("Errore nella categoria");
        } catch (CategoriaOmonimaException ex){
            System.out.println("Errore nella categoria omonima: " + ex.getMessage());
        } catch (IllegalCampoException ex) {
            System.out.println("Errore nel campo: " + ex.getMessage());
        } catch (PadreNotFoundException ex){
            System.out.println("Errore nel padre: " + ex.getMessage());
        }
        catch (Exception ex) {
            System.out.println("Errore sconosciuto: " + ex.getMessage());
        }
    }
}


