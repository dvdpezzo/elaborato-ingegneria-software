package it.ingbs.ingegneria_software.model;

import java.util.*;

import it.ingbs.ingegneria_software.Eccezioni.CategoriaNotFoundException;
import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;



public class GestoreRichieste {
    
    private HashMap<Fruitore,List<RichiestaScambio>> mappaRichieste = new HashMap<Fruitore,List<RichiestaScambio>>();
    GestoreFileRichieste gestoreFileRichieste;


    public GestoreRichieste(GestoreFileRichieste gestoreFileRichieste) throws CategoriaNotFoundException{
        gestoreFileRichieste.leggiDaFile();
    }

    /**
     * Metodo che aggiunge una richiesta di scambio alla mappa delle richieste s
     * @param fr fruitore che effettua la richiesta
     * @param rs richiesta effeeuttata
     */
    public void addRichiesta(Fruitore fr, RichiestaScambio rs){
        if(mappaRichieste.containsKey(fr)){
            mappaRichieste.get(fr).add(rs);
        }else{
            List<RichiestaScambio> lista = new ArrayList<RichiestaScambio>();
            lista.add(rs);
            mappaRichieste.put(fr, lista);
            gestoreFileRichieste.salvaSuFile();
        }
    }
    
    /**
     * Metodo che rimuove una richiesta di scambio dalla mappa delle richieste
     * @param richiestaNuova richiesta da rimuovere
     */
    public void rimuoviRichiesta(RichiestaScambio richiestaNuova) {
        for (Map.Entry<Fruitore, List<RichiestaScambio>> entry : mappaRichieste.entrySet()) {
            if (entry.getValue().contains(richiestaNuova)) {
                entry.getValue().remove(richiestaNuova);
                gestoreFileRichieste.salvaSuFile();
            }
        }
    }
    /**
     * Metodo che crea una richiesta di scambio
     * @param catRichiesta  categoria richiesta dal fruitore
     * @param catOfferta    categorie offerta dal fruitore
     * @param numOre        numero di ore che vengono richieste
     * @param fruitore     fruitore che effettua la richiesta
     * @param fattoreConversione fattore di conversione tra le due categorie
     * @return
     */
    public RichiestaScambio creaRichiesta(Categoria catRichiesta, Categoria catOfferta, int numOre, Fruitore fruitore,
            Double fattoreConversione) {

        RichiestaScambio richiesta = new RichiestaScambio(catRichiesta, numOre, catOfferta, fruitore, fattoreConversione);
        addRichiesta(fruitore, richiesta);
        return richiesta;
    }

    /*
     * Metodo che restituisce la mappa delle richieste
     */
    public HashMap<Fruitore, List<RichiestaScambio>> getMappaRichieste(){
        return mappaRichieste;
    }
    
}