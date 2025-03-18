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
            Double fattoreConversione, Stato stato) {

        RichiestaScambio richiesta = new RichiestaScambio(catRichiesta, numOre, catOfferta, fruitore, fattoreConversione,stato);
        addRichiesta(fruitore, richiesta);
        return richiesta;
    }

    /**
     * Metodo che crea una nuova richiesta di scambio
     * @param fruitore soggetto che crea una richiesta di scambio di prestazione
     * @throws CategoriaNotFoundException
     * @return la richiesta creata
     */
    public RichiestaScambio nuovaRichiesta(Fruitore fruitore) throws CategoriaNotFoundException {
        Categoria catRichiesta = cercaCatFoglia();
        Categoria catOfferta = cercaCatFoglia();
        int numOre = InputDati.leggiInteroConMinimo("Di quante ore necessiti?", 0);
        
        // Controllo se esiste un fattore di conversione tra le due categorie
        String chiaveConversione = catRichiesta.getNome().toUpperCase() + "->" + catOfferta.getNome().toUpperCase();
        if (!gestoreFile.getGestoreDati().getFattori().containsKey(chiaveConversione)) {
            System.out.println("Non esiste un fattore di conversione tra le categorie selezionate.");
        }
        
        Double fattoreConversione = gestoreFile.getGestoreDati().getFattori().get(chiaveConversione).getValoreConversione();
        Stato stato = Stato.Aperto;
        RichiestaScambio richiestaNuova = creaRichiesta(catRichiesta, catOfferta, numOre, fruitore, fattoreConversione,stato);
        System.out.println(richiestaNuova.toString());
        boolean salva = InputDati.yesOrNo("Vuoi salvare la richiesta?");
        if (!salva) {
            rimuoviRichiesta(richiestaNuova);
            return null;
        } else {
            gestoreFile.salvaRichieste();
            return richiestaNuova;
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


    /**
     * Metodo che visualizza le richieste effettuate da un fruitore
     * @param fruitore fruitore che effettua la richiesta
     */
    public void visualizzaRichieste(Fruitore fruitore) {
        if (mappaRichieste.containsKey(fruitore)) {
            for (RichiestaScambio richiesta : mappaRichieste.get(fruitore)) {
                System.out.println(richiesta.toString());
            }
        } else {
            System.out.println("Non hai effettuato nessuna richiesta.");
        }
    }

    /**
     * Metodo che permette di ritirare una richiesta di scambio
     * @param fruitore fruitore che esgue la richiesta
     * @param richiesta richiesta da ritirare
     */
    public void ritiraRichiesta(Fruitore fruitore, RichiestaScambio richiesta) {
        if (mappaRichieste.containsKey(fruitore)) {
             for(RichiestaScambio richiestaScambio : mappaRichieste.get(fruitore)){
                 if(richiestaScambio.equals(richiesta)){
                       richiestaScambio.setStato(Stato.Ritirato);
                    }
             }
        }
        System.out.println("Richiesta ritirata con successo.");
        gestoreFile.salvaRichieste();
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
        int scelta = InputDati.leggiInteroConMinimo("Quale richiesta vuoi ritirare?", 1);
        return richieste.get(scelta - 1);
       
    }


    /**
     * Metodo che permette di valutare una richiesta di scambio
     * @param fruitore1 fruitore che effettua la richiesta
     * @param richiesta richiesta da valutare
     */
    public void valutazioneRichiesta(Fruitore fruitore1, RichiestaScambio richiesta) {
        for (Fruitore fruitore2 : mappaRichieste.keySet()) {
            if(fruitore2.getComprensorio()==(fruitore1.getComprensorio())){
                for(RichiestaScambio richiestaDaTrovare : mappaRichieste.get(fruitore2)){
                    if(richiestaDaTrovare.trovaRichiestaScambio(richiesta)){
                        richiesta.setStato(Stato.Chiuso);
                        richiestaDaTrovare.setStato(Stato.Chiuso);
                        gestoreFile.salvaRichieste();
                    }
                }
            
            }  
         
        
        }

    }

}