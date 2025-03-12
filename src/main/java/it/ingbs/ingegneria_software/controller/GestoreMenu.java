package it.ingbs.ingegneria_software.controller;

import java.io.File;
import java.util.HashMap;

import it.ingbs.ingegneria_software.Eccezioni.CategoriaNotFoundException;
import it.ingbs.ingegneria_software.model.FattoriConversione;
import it.ingbs.ingegneria_software.model.Fruitore;
import it.ingbs.ingegneria_software.model.GestoreFattori;
import it.ingbs.ingegneria_software.model.GestoreRichieste;
import it.ingbs.ingegneria_software.model.RichiestaScambio;
import it.ingbs.ingegneria_software.model.comprensori.GestoreComprensorio;
import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.model.gerarchie.Gerarchia;
import it.ingbs.ingegneria_software.model.gerarchie.GestoreGerarchie;
 import it.ingbs.ingegneria_software.model.gerarchie.MenuGerarchie;
import it.ingbs.ingegneria_software.model.utenti.Configuratore;
import it.ingbs.ingegneria_software.model.utenti.GestoreFruitori;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

/*
 * NUOVA CLASSE PER LA SUDDIVISIONE DEI COMPITI DEL CONFIGURATORE (Semplificazione della classe Sistema)
 */
public class GestoreMenu {
    
    
    private final String[] vociMenuBackEnd = new String[]{"GESTIONE COMPRENSORI","GESTIONE GERARCHIE","GESTIONE FATTORI CONVERSIONE"};

    private final String[] vociMenuFrontEnd = new String[]{"Visualizza Gerarchie","Effettua una richiesta"};
      
    private final MenuUtil menuBackEnd = new MenuUtil("MENU BACK-END:", vociMenuBackEnd);

    private final MenuUtil menuFrontEnd = new MenuUtil("MENU FRONT END",vociMenuFrontEnd);

    private GestoreComprensorio gestoreComprensorio = new GestoreComprensorio();
    private GestoreGerarchie gestoreGerarchia = new GestoreGerarchie();
    private HashMap<String, Gerarchia> radici = new HashMap<>();
    private File nomefile = new File("elaborato-ingegneria-software\\src\\Data_File\\elencoGerarchie.txt");
    private final GestoreFattori gestoreFattori = new GestoreFattori();
    private final GestoreRichieste gr = new GestoreRichieste();

    public void backEnd (Configuratore configuratore) throws Exception {
        int scelta;
        do {
            scelta = menuBackEnd.scegli();
            switch (scelta) { 
                case 1:
                gestoreComprensorio.modificaComprensori();
                break;

                case 2:
                MenuGerarchie menuGerarchie = new MenuGerarchie();
                menuGerarchie.run();
                break;

                case 3:
                gestoreFattori.modificaFattori();
                break;
            }
        }while(scelta!=0);
    }
	

    /**
     * menu front end con le varie operazioni che il fruitore può eseguire 
     * @param fruitore
     * @throws Exception
     */

    public void frontEnd(Fruitore fruitore) throws Exception{
        int scelta;

        do{
            scelta=menuFrontEnd.scegli();
            switch(scelta){
                case 1: 
                gestoreGerarchia.stampaGerarchie();
                break;

                case 2:
                    nuovaRichiesta(fruitore);
                break;

        }
     }while(scelta!=0);
  }


  /**
   * 
   * @param fruitore soggetto che crea una richiesta di scambio di prestazione
   * @throws CategoriaNotFoundException
   */
    private void nuovaRichiesta(Fruitore fruitore) throws CategoriaNotFoundException {
        Categoria catRichiesta = cercaCatFoglia();
        Categoria catOfferta = cercaCatFoglia();
        int numOre = InputDati.leggiInteroConMinimo("Di quante ore necessiti?", 0);
        Double fattoreConversione = gestoreFattori.getFattore(catRichiesta.getNome().toUpperCase()+"->"+catOfferta.getNome().toUpperCase());
        RichiestaScambio richiestaNuova = gr.creaRichiesta(catRichiesta,catOfferta, numOre, fruitore, fattoreConversione);
        System.out.println(richiestaNuova.toString());
        boolean salva = InputDati.yesOrNo("Vuoi salvare la richiesta?");
        if(!salva){
           gr.rimuoviRichiesta(richiestaNuova);
          }else{
            gr.salvaSuFile();
          }
        
    }


    /**
     * 
     * @return la categoria cercata
     * @throws CategoriaNotFoundException
     */
    private Categoria cercaCatFoglia() throws CategoriaNotFoundException {
        Categoria catCercata;
        do{
        String nomeRichiesta = InputDati.leggiStringaNonVuota("Inserisci il nome della categoria di cui hai bisogno")
                                                      .toUpperCase();
        catCercata = gestoreFattori.getCategoria(nomeRichiesta);
        }while(catCercata==null);
        return catCercata;
    }
    


}
