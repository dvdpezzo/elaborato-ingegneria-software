package it.ingbs.ingegneria_software.model;

import java.util.*;

import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;

public class GestoreRichieste {
    
    HashMap<Fruitore,RichiestaScambio> mappaRichieste = new HashMap<Fruitore,RichiestaScambio>();


    /**
     * aggiunge alla mappa una richiesta 
     * @param r richiesta che viene aggiunta alla mappa
     */
    public void aggiungiRichiesta(RichiestaScambio r){
        mappaRichieste.put(r.getFr(),r);
    }



    public RichiestaScambio creaRichiesta(){
        return null;
    }


    public int calcolaOre(Categoria cat1, Categoria cat2){
      return 0;
    }

    public boolean compatibile(){
        
        return true; 
    }


    
}
