package it.ingbs.ingegneria_software.gestione_file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.ingbs.ingegneria_software.model.RichiestaScambio;
import it.ingbs.ingegneria_software.model.comprensori.ComprensorioGeografico;
import it.ingbs.ingegneria_software.model.fattori.FattoriConversione;
import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.model.gerarchie.Gerarchia;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;

//classe contenente tutti i dati run-time
public class GestoreDati {
    private HashMap<Integer, String> comuni;
    private HashMap<Integer, ComprensorioGeografico> comprensori;
    private HashMap<String, Gerarchia> gerarchie;
    private HashMap<String, String> credenzialiConfiguratori;
    private HashMap<String, String> credenzialiFruitori;
    private HashMap<String, Fruitore> datiFruitori;
    private HashMap<String, FattoriConversione> fattori;
    private HashMap<Fruitore, List<RichiestaScambio>> richieste;
    private HashMap<String, Categoria> categorie;

    public GestoreDati() {
        this.comuni = new HashMap<>();
        this.comprensori = new HashMap<>();
        this.gerarchie = new HashMap<>();
        this.credenzialiConfiguratori = new HashMap<>();
        this.credenzialiFruitori = new HashMap<>();
        this.datiFruitori = new HashMap<>();
        this.fattori = new HashMap<>();
        this.richieste = new HashMap<>();
        this.categorie = new HashMap<>();
    }

    public HashMap<Integer, String> getComuni() {
        return comuni;
    }

    public void setComuni(HashMap<Integer, String> comuni) {
        this.comuni = comuni;
    }

    public HashMap<Integer, ComprensorioGeografico> getComprensori() {
        return comprensori;
    }

    public void setComprensori(HashMap<Integer, ComprensorioGeografico> comprensori) {
        this.comprensori = comprensori;
    }

    public HashMap<String, Gerarchia> getGerarchie() {
        return gerarchie;
    }

    public void setGerarchie(HashMap<String, Gerarchia> gerarchie) {
        this.gerarchie = gerarchie;
        setCategorie();
    }
    
    public HashMap<String, Categoria> getCategorie() {
        return categorie;
    }
    public void setCategorie() {
        for(Gerarchia g : gerarchie.values()) {
            for(Categoria c : g.getSottoCategorie().values()) {
                this.categorie.put(c.getNome(), c);
            }
        }
    }

    public HashMap<String, String> getCredenzialiConfiguratori() {
        return credenzialiConfiguratori;
    }

    public void setCredenzialiConfiguratori(HashMap<String, String> credenzialiConfiguratori) {
        this.credenzialiConfiguratori = credenzialiConfiguratori;
    }

    public HashMap<String, String> getCredenzialiFruitori() {
        return credenzialiFruitori;
    }

    public void setCredenzialiFruitori(HashMap<String, String> credenzialiFruitori) {
        this.credenzialiFruitori = credenzialiFruitori;
    }    

    public HashMap<String, FattoriConversione> getFattori() {
        return fattori;
    }
    /**
     * Imposta i fattori di conversione a partire da una mappa di stringhe e double.
     *
     * @param fattori la mappa delle stringhe e dei double
     */
    public void setFattori(HashMap<String, Double> fattori) {
        HashMap<String, FattoriConversione> mappaFattori = new HashMap<>();
        for (String chiave : fattori.keySet()) {
            Double valore = fattori.get(chiave);
            String[] categorieLette = chiave.split("->");
            Categoria categoria1 = trovaCategoria(categorieLette[0].trim());
            Categoria categoria2 = trovaCategoria(categorieLette[1].trim());
            FattoriConversione fattoreConversione = new FattoriConversione(valore, categoria1, categoria2);
            mappaFattori.put(chiave, fattoreConversione);
        }
        this.fattori = mappaFattori;
    }

    /**
     * Trova una categoria tra tutte le gerarchie.
     *
     * @param nomeCategoria il nome della categoria da trovare
     * @return la categoria trovata, o null se non trovata
     */
    private Categoria trovaCategoria(String nomeCategoria) {
        for (Gerarchia gerarchia : gerarchie.values()) {
            Categoria categoria = gerarchia.getCategoria(nomeCategoria);
            if (categoria != null) {
                return categoria;
            }
        }
        return null;
    }

    public HashMap<Fruitore, List<RichiestaScambio>> getRichieste() {
        return richieste;
    }

    /**
     * Imposta le richieste a partire da una mappa di stringhe.
     *
     * @param richieste la mappa delle richieste in formato stringa
     */
    public void setRichieste(HashMap<String, List<String>> richieste) {
        HashMap<Fruitore, List<RichiestaScambio>> mappaRichieste = new HashMap<>();
        for (String nomeFruitore : richieste.keySet()) {
            Fruitore fruitore = datiFruitori.get(nomeFruitore);
            List<RichiestaScambio> listaRichieste = new ArrayList<>();
            List<String> richiesteFruitore = richieste.get(nomeFruitore);
            for (int i = 0; i < richiesteFruitore.size(); i += 2) {
                String richiesta = richiesteFruitore.get(i).replace("Richiesta: [[", "").replace("]", "").trim();
                String offerta = richiesteFruitore.get(i + 1).replace("Offerta: [[", "").replace("]", "").trim();
                String[] richiestaParts = richiesta.split(",");
                String[] offertaParts = offerta.split(",");
                Categoria catRichiesta = categorie.get(richiestaParts[0].trim());
                int oreRichieste = Integer.parseInt(richiestaParts[1].trim());
                Categoria catOfferta = categorie.get(offertaParts[0].trim());
                int oreOfferte = Integer.parseInt(offertaParts[1].trim());
                Double fattoreConv = (double) oreOfferte / oreRichieste;
                RichiestaScambio richiestaScambio = new RichiestaScambio(catRichiesta, oreRichieste, catOfferta, fruitore, fattoreConv);
                listaRichieste.add(richiestaScambio);
            }
            mappaRichieste.put(fruitore, listaRichieste);
        }
        this.richieste = mappaRichieste;
    }

    public HashMap<String, Fruitore> getDatiFruitori() {
        return datiFruitori;
    }

    public void setDatiFruitori(HashMap<String, Fruitore> datiFruitore) {
        this.datiFruitori = datiFruitore;
    }

    public ArrayList<String> getUtenti() {
        ArrayList<String> utenti = new ArrayList<>();
        utenti.addAll(credenzialiConfiguratori.keySet());
        utenti.addAll(credenzialiFruitori.keySet());
        return utenti;
    }

    public void setUtenti(HashMap<String, String> configuratori, HashMap<String, String> fruitori) {
        this.credenzialiConfiguratori = configuratori;
        this.credenzialiFruitori = fruitori;
    }
    
}