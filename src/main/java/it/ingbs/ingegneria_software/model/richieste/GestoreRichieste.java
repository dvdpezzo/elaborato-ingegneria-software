package it.ingbs.ingegneria_software.model.richieste;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import it.ingbs.ingegneria_software.Eccezioni.CategoriaNotFoundException;
import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.model.gerarchie.Gerarchia;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;
import it.ingbs.ingegneria_software.utilita_generale.*;

public class GestoreRichieste implements Rimuovibile,Salvabile,Visualizzabile, Aggiungibile {
    private final HashMap<Fruitore, List<RichiestaScambio>> mappaRichieste;
    private final GestoreDati gestoreDati;
    private final GestoreCicliScambio gestoreCicli;
    private Random random = new Random();
    private Fruitore fruitore;

    public GestoreRichieste(GestoreDati gestoreDati) {
        this.gestoreDati = gestoreDati;
        this.mappaRichieste = gestoreDati.getRichieste();
        this.gestoreCicli = new GestoreCicliScambio(mappaRichieste);
        inizializza();
    }

    private final void inizializza() {
        valutazioneRichieste();
    }

    public void setFruitore(Fruitore fruitore) {
        this.fruitore = fruitore;
    }

    /**
     * Metodo che aggiunge una richiesta di scambio alla mappa delle richieste
     * @param fruitore fruitore che effettua la richiesta
     * @param richiestaScambio richiesta effettuata
     */
    private void addRichiesta(Fruitore fruitore, RichiestaScambio richiestaScambio){
        if(mappaRichieste.containsKey(fruitore)){
            mappaRichieste.get(fruitore).add(richiestaScambio);
        }else{
            List<RichiestaScambio> lista = new ArrayList<>();
            lista.add(richiestaScambio);
            mappaRichieste.put(fruitore, lista);
        }
        salva();
    }
    
    /**
     * Metodo che rimuove una richiesta di scambio dalla mappa delle richieste (quando non viene salvata)
     * @param richiestaNuova richiesta da rimuovere
     */
    private void rimuoviRichiesta(RichiestaScambio richiestaNuova) {
        for (Map.Entry<Fruitore, List<RichiestaScambio>> entry : mappaRichieste.entrySet()) {
            if (entry.getValue().contains(richiestaNuova)) {
                entry.getValue().remove(richiestaNuova);
                salva();
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
            Double fattoreConversione, Stato stato) {

        RichiestaScambio richiesta = new RichiestaScambio(catRichiesta, numOre, catOfferta, fruitore, fattoreConversione,stato);
        addRichiesta(fruitore, richiesta);
        return richiesta;
    }

    /**
     * Metodo che crea una nuova richiesta di scambio
     * @param fruitore soggetto che crea una richiesta di scambio di prestazione
     * @return la richiesta creata
     */
    private RichiestaScambio nuovaRichiesta(Fruitore fruitore) {
        Categoria catRichiesta;
        Categoria catOfferta;
        do { 
            catRichiesta = cercaCatFoglia();
            catOfferta = cercaCatFoglia();
            if (catRichiesta == null || catOfferta == null) {
                System.out.println("Categoria non trovata. Riprova.");
            } else if (catRichiesta.equals(catOfferta)) {
                System.out.println("Le categorie non possono essere uguali. Riprova.");
            }
        } while (catRichiesta == null || catOfferta == null || catRichiesta.equals(catOfferta));
        
        int numOre = InputDati.leggiInteroConMinimo("Di quante ore necessiti?", 0);
        
        // Controllo se esiste un fattore di conversione tra le due categorie
        String chiaveConversione = catRichiesta.getNome().toUpperCase() + "->" + catOfferta.getNome().toUpperCase();
        if (!gestoreDati.getFattori().containsKey(chiaveConversione)) {
            System.out.println("Non esiste un fattore di conversione tra le categorie selezionate.");
            return null; 
        }
        
        Double fattoreConversione = gestoreDati.getFattori().get(chiaveConversione).getValoreConversione();
        RichiestaScambio richiestaNuova = creaRichiesta(catRichiesta, catOfferta, numOre, fruitore, fattoreConversione, Stato.Aperto);
        
        if (!InputDati.yesOrNo("Vuoi salvare la richiesta?")) {
            rimuoviRichiesta(richiestaNuova);
            return null;
        }
        
        salva();
        return richiestaNuova;
    }

    /**
     * Metodo che cerca una categoria foglia usando il GestoreGerarchie
     * @return la categoria cercata se la trova null altrimenti
     */
    private Categoria cercaCatFoglia() {
        String nomeRichiesta = InputDati.leggiStringaNonVuota("Inserisci il nome della categoria di cui hai bisogno").toUpperCase();
        for (Map.Entry<String, Gerarchia> gerarchia : gestoreDati.getGerarchie().entrySet()) {
            try {
                Categoria cat = gerarchia.getValue().getCategoria(nomeRichiesta);
                if (cat != null && cat.getFigli().isEmpty()) {  // Verifica che sia una foglia
                    return cat;
                }
            } catch (CategoriaNotFoundException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return null;
    }

    /*
     * Metodo che restituisce la mappa delle richieste
     */
    public HashMap<Fruitore, List<RichiestaScambio>> getMappaRichieste(){
        return mappaRichieste;
    }

    /**
     * Metodo che permette di ritirare una richiesta di scambio
     * @param fruitore fruitore che esgue la richiesta
     * @param richiesta richiesta da ritirare
     */
    public void ritiraRichiesta(Fruitore fruitore, RichiestaScambio richiesta) {
        if (mappaRichieste.containsKey(fruitore)) {
             for(RichiestaScambio richiestaScambio : mappaRichieste.get(fruitore)){
                 if(richiestaScambio.equals(richiesta) && richiestaScambio.getStato()!= Stato.Chiuso){
                       richiestaScambio.setStato(Stato.Ritirato);
                       System.out.println("Richiesta ritirata con successo.");
                       salva();
                       return;
                    }
                }
                System.out.println("Richiesta non trovata o già chiusa.");
        }
    }

    /**
     * Metodo che permette di scegliere una richiesta di scambio
     * @param fruitore fruitore che effettua la richiesta
     * @return  la richiesta scelta
     */
    public RichiestaScambio scegliRichiesta(Fruitore fruitore) {
        List<RichiestaScambio> richieste = mappaRichieste.get(fruitore);
        if (richieste == null || richieste.isEmpty()) {
            System.out.println("Non hai effettuato nessuna richiesta.");
            return null;
        }
        System.out.println("Ecco le tue richieste:");
        for (int i = 0; i < richieste.size(); i++) {
            System.out.println((i + 1) + ") " + richieste.get(i).toString());
        }
        int scelta = InputDati.leggiInteroLimitato("Quale richiesta vuoi ritirare?", 1,richieste.size());
        return richieste.get(scelta - 1);
       
    }

    /**
     * Valuta tutte le richieste aperte cercando cicli di scambio validi
     */
    public void valutazioneRichieste() {
        for (Map.Entry<Fruitore, List<RichiestaScambio>> entry : mappaRichieste.entrySet()) {
            for (RichiestaScambio richiesta : entry.getValue()) {
                valutazioneRichiesta(entry.getKey(), richiesta);
                
            }
        }
        salva();
    }

    /**
     * Valuta una singola richiesta di scambio
     */
    public void valutazioneRichiesta(Fruitore proprietarioRichiesta, RichiestaScambio richiesta) {
        if (richiesta.getStato() == Stato.Aperto) {
            gestoreCicli.valutaRichieste(proprietarioRichiesta, richiesta);
            salva();
        }
    }

    @Override
    public void view() {
        visualizzaRichiesteFruitore(this.fruitore, mappaRichieste);
    }

    //rimuovi = ritira
    @Override
    public void rimuovi() {
        RichiestaScambio richiesta = scegliRichiesta(fruitore);
        if (richiesta != null) {
            ritiraRichiesta(fruitore, richiesta);
            System.out.println("Richiesta rimossa con successo.");
        }
    }

    @Override
    public void aggiungi() {
        RichiestaScambio nuovaRichiesta = nuovaRichiesta(fruitore);
        if (nuovaRichiesta != null) {
            valutazioneRichiesta(fruitore, nuovaRichiesta);
            System.out.println("Richiesta aggiunta con successo.");
        }
    }

    @Override
    public void salva() {
        gestoreDati.setRichieste(mappaRichieste);
    }
    
    private void visualizzaRichiesta(RichiestaScambio richiesta) {
        System.out.println(richiesta.getFr().getNomeUtente() + " " + richiesta.getFr().getEmail());
        System.out.println(richiesta.toString());
    }

    public void visualizzaRichiesteChiuse() {
        Map<Integer, List<RichiestaScambio>> richiesteChiuse = gestoreCicli.getRichiesteChiuse();
        for(Map.Entry<Integer, List<RichiestaScambio>> entry : richiesteChiuse.entrySet()){
            System.out.println("Ciclo #" + entry.getKey() + ":");
            for(RichiestaScambio richiesta : entry.getValue()){
                visualizzaRichiesta(richiesta);
                System.out.println();
            }
            System.out.println("-----------------");
        }
    }
    
    public void visualizzaRichiesteCategoria() {
        Categoria catCercata = cercaCatFoglia();
        if(catCercata == null){
            System.out.println("Categoria non trovata.");
            return;
        }
        for(Map.Entry<Fruitore, List<RichiestaScambio>> entry : mappaRichieste.entrySet()){
            for(RichiestaScambio richiesta : entry.getValue()){
                if(richiesta.getCatRichiesta().equals(catCercata) || 
                   richiesta.getCatOfferta().equals(catCercata)){
                    visualizzaRichiesta(richiesta);
                }
            }
        }
    }

    public void visualizzaRichiesteFruitore(Fruitore fruitore, Map<Fruitore, List<RichiestaScambio>> mappaRichieste) {
        List<RichiestaScambio> richiesteFruitore = mappaRichieste.get(fruitore);
        if (richiesteFruitore != null) {
            richiesteFruitore.forEach(this::visualizzaRichiesta);
        }
    }
}