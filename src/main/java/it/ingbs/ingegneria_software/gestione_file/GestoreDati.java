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
    }
    
    public void setCategorie(HashMap<String, Categoria> categorie) {
        this.categorie = categorie;
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
            Categoria categoria1 = this.categorie.get(categorieLette[0].trim());
            Categoria categoria2 = this.categorie.get(categorieLette[1].trim());
            FattoriConversione fattoreConversione = new FattoriConversione(valore, categoria1, categoria2);
            mappaFattori.put(chiave, fattoreConversione);
        }
        this.fattori = mappaFattori;
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
            for (String richiesta : richieste.get(nomeFruitore)) {
                String[] parts = richiesta.replace("Richiesta: [", "").replace("Offerta: [", "").replace("]", "").split(", ");
                String nomeCategoriaRichiesta = parts[0];
                int numOre = Integer.parseInt(parts[1]);
                Categoria categoria = categorie.get(nomeCategoriaRichiesta);
                RichiestaScambio richiestaScambio = new RichiestaScambio(categoria, numOre, categoria, fruitore, 1.0); // Assumendo un fattore di conversione di 1.0
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

    private boolean controlloEsistenzaConfiguratore(String nomeUtente, String pass) {
        return credenzialiConfiguratori.containsKey(nomeUtente) && credenzialiConfiguratori.get(nomeUtente).equals(pass);
    }

    private boolean controlloEsistenzaFruitore(String nomeUtente, String pass) {
        return credenzialiFruitori.containsKey(nomeUtente) && credenzialiFruitori.get(nomeUtente).equals(pass);
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