package it.ingbs.ingegneria_software.model;

import java.util.*;

import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.model.gerarchie.GestoreGerarchie;

public class GestoreRichieste {
    
    private HashMap<Fruitore,RichiestaScambio> mappaRichieste = new HashMap<Fruitore,RichiestaScambio>();
    private GestoreGerarchie gg = new GestoreGerarchie();


    /**
     * aggiunge alla mappa una richiesta 
     * @param r richiesta che viene aggiunta alla mappa
     */
    public void aggiungiRichiesta(RichiestaScambio r){
        mappaRichieste.put(r.getFr(),r);
    }

    public int calcolaOre(Categoria cat1, Categoria cat2, int ore){
      return 0;
    }

    /**
     * 
     * @param cat1 richiesta dal fruitore
     * @param cat2 offerta del fuitore
     * @param ore  richieste dal fruitore
     * @param fr   soggetto che effettua la richiesta
     * @return     la richiesta effettuata 
     */
    public RichiestaScambio creaRichiesta(Categoria cat1, Categoria cat2, int ore, Fruitore fr, Double fattoreConv) {
        RichiestaScambio nuovaRichiesta = new RichiestaScambio(cat1, ore, cat2, fr, fattoreConv);
        aggiungiRichiesta(nuovaRichiesta);
        return nuovaRichiesta;
    }

    



  /*   VERSIONE 4 
     public boolean compatibile(){
        return true; 
    }

    */


    
}