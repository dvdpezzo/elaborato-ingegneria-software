package it.ingbs.ingegneria_software.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.ingbs.ingegneria_software.Eccezioni.CategoriaNotFoundException;
import it.ingbs.ingegneria_software.gestione_file.GestoreFile;
import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.model.gerarchie.Gerarchia;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;

public class GestoreRichieste {
    private final HashMap<Fruitore, List<RichiestaScambio>> mappaRichieste;
    private final GestoreFile gestoreFile;

    public GestoreRichieste(GestoreFile gestoreFileRichieste, HashMap<Fruitore, List<RichiestaScambio>> mappaRichieste) {
        this.gestoreFile = gestoreFileRichieste;
        this.mappaRichieste = mappaRichieste;
    }

    /**
     * Metodo che aggiunge una richiesta di scambio alla mappa delle richieste
     * @param fruitore fruitore che effettua la richiesta
     * @param richiestaScambio richiesta effettuata
     */
    public void addRichiesta(Fruitore fruitore, RichiestaScambio richiestaScambio){
        if(mappaRichieste.containsKey(fruitore)){
            mappaRichieste.get(fruitore).add(richiestaScambio);
        }else{
            List<RichiestaScambio> lista = new ArrayList<>();
            lista.add(richiestaScambio);
            mappaRichieste.put(fruitore, lista);
        }
        gestoreFile.salvaRichieste();
    }
    
    /**
     * Metodo che rimuove una richiesta di scambio dalla mappa delle richieste
     * @param richiestaNuova richiesta da rimuovere
     */
    public void rimuoviRichiesta(RichiestaScambio richiestaNuova) {
        for (Map.Entry<Fruitore, List<RichiestaScambio>> entry : mappaRichieste.entrySet()) {
            if (entry.getValue().contains(richiestaNuova)) {
                entry.getValue().remove(richiestaNuova);
                gestoreFile.salvaRichieste();
                break;
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

    /**
     * Metodo che crea una nuova richiesta di scambio
     * @param fruitore soggetto che crea una richiesta di scambio di prestazione
     * @throws CategoriaNotFoundException
     */
    public void nuovaRichiesta(Fruitore fruitore) throws CategoriaNotFoundException {
        Categoria catRichiesta = cercaCatFoglia();
        Categoria catOfferta = cercaCatFoglia();
        int numOre = InputDati.leggiInteroConMinimo("Di quante ore necessiti?", 0);
        
        // Controllo se esiste un fattore di conversione tra le due categorie
        String chiaveConversione = catRichiesta.getNome().toUpperCase() + "->" + catOfferta.getNome().toUpperCase();
        if (!gestoreFile.getGestoreDati().getFattori().containsKey(chiaveConversione)) {
            System.out.println("Non esiste un fattore di conversione tra le categorie selezionate.");
            return;
        }
        
        Double fattoreConversione = gestoreFile.getGestoreDati().getFattori().get(chiaveConversione).getValoreConversione();
        RichiestaScambio richiestaNuova = creaRichiesta(catRichiesta, catOfferta, numOre, fruitore, fattoreConversione);
        System.out.println(richiestaNuova.toString());
        boolean salva = InputDati.yesOrNo("Vuoi salvare la richiesta?");
        if (!salva) {
            rimuoviRichiesta(richiestaNuova);
        } else {
            gestoreFile.salvaRichieste();
        }
    }

    /**
     * Metodo che cerca una categoria foglia
     * @return la categoria cercata
     * @throws CategoriaNotFoundException
     */
    private Categoria cercaCatFoglia() throws CategoriaNotFoundException {
        Categoria catCercata = null;
        String nomeRichiesta = InputDati.leggiStringaNonVuota("Inserisci il nome della categoria di cui hai bisogno").toUpperCase();
        for (Map.Entry<String, Gerarchia> gerarchia : gestoreFile.getGestoreDati().getGerarchie().entrySet()) {
            catCercata = gerarchia.getValue().getCategoria(nomeRichiesta);
            if (catCercata != null) {
                break;
            }
        }
        if (catCercata == null) {
            throw new CategoriaNotFoundException();
        }
        return catCercata;
    }

    /*
     * Metodo che restituisce la mappa delle richieste
     */
    public HashMap<Fruitore, List<RichiestaScambio>> getMappaRichieste(){
        return mappaRichieste;
    }
    
}