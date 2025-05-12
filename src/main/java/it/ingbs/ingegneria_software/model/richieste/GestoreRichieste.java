package it.ingbs.ingegneria_software.model.richieste;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import it.ingbs.ingegneria_software.Eccezioni.CategoriaNotFoundException;
import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.model.gerarchie.Gerarchia;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.UtilityHandler;

public class GestoreRichieste implements UtilityHandler{
    private final HashMap<Fruitore, List<RichiestaScambio>> mappaRichieste;
    private final HashMap<Integer, List<RichiestaScambio>> richiesteChiuse = new HashMap<>();
    private final GestoreDati gestoreDati;
    private final Random random = new Random();
    private Fruitore fruitore; // Campo per il fruitore corrente

    public GestoreRichieste(GestoreDati gestoreDati) {
        this.gestoreDati = gestoreDati;
        this.mappaRichieste = gestoreDati.getRichieste();
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
    public void addRichiesta(Fruitore fruitore, RichiestaScambio richiestaScambio){
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
     * Metodo che rimuove una richiesta di scambio dalla mappa delle richieste
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
    public RichiestaScambio nuovaRichiesta(Fruitore fruitore) {
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
        } while (catRichiesta == null || catOfferta == null);
        
        int numOre = InputDati.leggiInteroConMinimo("Di quante ore necessiti?", 0);
        
        // Controllo se esiste un fattore di conversione tra le due categorie
        String chiaveConversione = catRichiesta.getNome().toUpperCase() + "->" + catOfferta.getNome().toUpperCase();
        if (!gestoreDati.getFattori().containsKey(chiaveConversione)) {
            System.out.println("Non esiste un fattore di conversione tra le categorie selezionate.");
            return null; 
        }
        
        Double fattoreConversione = gestoreDati.getFattori().get(chiaveConversione).getValoreConversione();
        Stato stato = Stato.Aperto;
        RichiestaScambio richiestaNuova = creaRichiesta(catRichiesta, catOfferta, numOre, fruitore, fattoreConversione,stato);
        System.out.println(richiestaNuova.toString());
        boolean salva = InputDati.yesOrNo("Vuoi salvare la richiesta?");
        if (!salva) {
            rimuoviRichiesta(richiestaNuova);
            return null;
        } else {
            salva();
            return richiestaNuova;
        }
    }

    /**
     * Metodo che cerca una categoria foglia
     * @return la categoria cercata se la trova null altrimenti
     * 
     */
    private Categoria cercaCatFoglia() {
        Categoria catCercata = null;
        String nomeRichiesta = InputDati.leggiStringaNonVuota("Inserisci il nome della categoria di cui hai bisogno").toUpperCase();
        for (Map.Entry<String, Gerarchia> gerarchia :gestoreDati.getGerarchie().entrySet()) {
            try {
                catCercata = gerarchia.getValue().getCategoria(nomeRichiesta);
                if (catCercata != null) {
                    break;
                }
            } catch (CategoriaNotFoundException ex) {
                ex.printStackTrace();
            }
        }        
        return catCercata;
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

    /*
     * Metodo che valuta tutte le richieste di scambio presenti nella mappaRichieste
     */
    public void valutazioneRichieste(){
        for(Map.Entry<Fruitore, List<RichiestaScambio>> entry : mappaRichieste.entrySet()){
            for(RichiestaScambio richiesta : entry.getValue()){
                if(richiesta.getStato() == Stato.Aperto){
                    if(valutazioneRichiesta(entry.getKey(), richiesta)){
                        richiesta.setStato(Stato.Chiuso);
                    }
                }
            }
        }
        salva();
    }

    /*
     * Metodo che valuta una richiesta di scambio
     */
    public boolean valutazioneRichiesta(Fruitore proprietarioRichiesta, RichiestaScambio richiesta) {
        // prendo la mappa di tutte le richieste che hanno fruitori con lo stesso comprensorio di proprietario richiesta
        HashMap<Fruitore, List<RichiestaScambio>> mappaRichiesteComprensorio = new HashMap<>();
        for (Map.Entry<Fruitore, List<RichiestaScambio>> entry : mappaRichieste.entrySet()) {
            if (entry.getKey().getComprensorio() == (proprietarioRichiesta.getComprensorio())) {
                mappaRichiesteComprensorio.put(entry.getKey(), entry.getValue());
            }
        }

        // Verifica se esiste un ciclo di richieste soddisfatte
        boolean richiestaPrincipaleSoddisfatta = false;
        for (Map.Entry<Fruitore, List<RichiestaScambio>> entry : mappaRichiesteComprensorio.entrySet()) {
            for (RichiestaScambio r : entry.getValue()) {
                Set<RichiestaScambio> visited = new HashSet<>();
                if (isCyclic(r, visited, mappaRichiesteComprensorio)) {
                    // Chiude tutte le richieste coinvolte nel ciclo
                    for (RichiestaScambio richiestaCiclo : visited) {
                        richiestaCiclo.setStato(Stato.Chiuso);
                    }
                    // Aggiunge il set di richieste chiuse alla mappa richiesteChiuse
                    aggiungiRichiesteChiuse(visited);
                    if (visited.contains(richiesta)) {
                        richiestaPrincipaleSoddisfatta = true;
                    }
                }
            }
        }
        // filtraRichieste();
        salva();

        return richiestaPrincipaleSoddisfatta;
    }

    /*
     * Metodo che filtra le richieste
     */
    private boolean isCyclic(RichiestaScambio richiesta, Set<RichiestaScambio> visited, HashMap<Fruitore, List<RichiestaScambio>> mappaRichiesteComprensorio) {
        if (visited.contains(richiesta)) {
            return true;
        }

        visited.add(richiesta);

        for (Map.Entry<Fruitore, List<RichiestaScambio>> entry : mappaRichiesteComprensorio.entrySet()) {
            for (RichiestaScambio r : entry.getValue()) {
                if (richiesta.soddisfaRichiesta(r)) {
                    if (isCyclic(r, visited, mappaRichiesteComprensorio)) {
                        return true;
                    }
                }
            }
        }

        visited.remove(richiesta);
        return false;
    }


    /**
     * Metodo che genera un codice casuale
     * @return
     */   
    private int generaCodiceRichiesta(){        
        return random.nextInt(9999);
    }

    /**
     * Metodo che aggiunge un set di richieste chiuse alla mappa richiesteChiuse.
     * Genera un codice univoco per identificare il gruppo di richieste.
     * Evita di aggiungere lo stesso set più volte.
     *
     * @param richiesteSet il set di richieste che si completano a vicenda
     */
    private void aggiungiRichiesteChiuse(Set<RichiestaScambio> richiesteSet) {
        // Converte il set in una lista per confronti
        List<RichiestaScambio> nuovaListaRichieste = new ArrayList<>(richiesteSet);

        // Controlla se il set è già presente nella mappa
        for (List<RichiestaScambio> listaEsistente : richiesteChiuse.values()) {
            if (listaEsistente.containsAll(nuovaListaRichieste) && nuovaListaRichieste.containsAll(listaEsistente)) {
                // Il set è già presente, non aggiungere duplicati
                return;
            }
        }

        // Genera un codice univoco e aggiunge il set alla mappa
        int codiceUnivoco = generaCodiceRichiesta();
        richiesteChiuse.put(codiceUnivoco, nuovaListaRichieste);
    }

    /**
     * Metodo che visualizza le richieste chiuse
     */
    public void visualizzaRichiesteChiuse(){
        for(Map.Entry<Integer, List<RichiestaScambio>> entry : richiesteChiuse.entrySet()){
            System.out.println("Codice richiesta: " + entry.getKey());
            for(RichiestaScambio richiesta : entry.getValue()){

                System.out.println(richiesta.getFr().getNomeUtente()+" "+richiesta.getFr().getEmail());
                System.out.println(richiesta.toString()+"\n");
            }
        }
    }

    /**
     * Metodo che visualizza le richieste di una categoria
     */
    public void visualizzaRichiesteCategoria(){
        
        Categoria catCercata = cercaCatFoglia();
        if(catCercata == null){
            System.out.println("Categoria non trovata.");
            return;
        }
        for(Map.Entry<Fruitore, List<RichiestaScambio>> entry : mappaRichieste.entrySet()){
            for(RichiestaScambio richiesta : entry.getValue()){
                if(richiesta.getCatRichiesta().equals(catCercata)){
                    System.out.println(richiesta.getFr().getNomeUtente());
                    System.out.println(richiesta.toString());
                }
                else if(richiesta.getCatOfferta().equals(catCercata)){
                    System.out.println(richiesta.getFr().getNomeUtente());
                    System.out.println(richiesta.toString());
                    }
            }
        }
        
    }

    @Override
    public void view() {
        if (mappaRichieste.containsKey(fruitore)) {
            for (RichiestaScambio richiesta : mappaRichieste.get(fruitore)) {
                System.out.println(richiesta.toString());
                
            }
        } else {
            System.out.println("Non hai effettuato nessuna richiesta.");
        }
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


    
}






