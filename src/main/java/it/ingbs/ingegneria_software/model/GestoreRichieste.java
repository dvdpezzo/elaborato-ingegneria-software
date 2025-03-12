package it.ingbs.ingegneria_software.model;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import it.ingbs.ingegneria_software.model.gerarchie.Categoria;



public class GestoreRichieste {
    
    private HashMap<Fruitore,ArrayList<RichiestaScambio>> mappaRichieste = new HashMap<Fruitore,ArrayList<RichiestaScambio>>();
   private final File fileRichieste = new File("src\\Data_File\\elencoRichieste.txt");


    /**
     * aggiunge alla mappa una richiesta 
     * @param r richiesta che viene aggiunta alla mappa
     */
    public void aggiungiRichiesta(RichiestaScambio r){
        mappaRichieste.put(r.getFr(),);
    }



    public void rimuoviRichiesta(RichiestaScambio r){
        mappaRichieste.remove(r.getFr(),r);
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


    public void salvaSuFile() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileRichieste, true))) {
        for (Entry<Fruitore, RichiestaScambio> entry : mappaRichieste.entrySet()) {
            Fruitore fruitore = entry.getKey();
            RichiestaScambio richiesta = entry.getValue();
            writer.write("Fruitore: " + fruitore.getNomeUtente() + "\n");
            writer.write(richiesta.toString() + "\n");
            writer.write("\n");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

   public HashMap<Fruitore,RichiestaScambio> getMappa(){
     return mappaRichieste;
   }

    public void setMappa(HashMap<Fruitore,RichiestaScambio> mappa){
         mappaRichieste = mappa;
    }








  /*   VERSIONE 4 
     public boolean compatibile(){
        return true; 
    }

    */


    
}