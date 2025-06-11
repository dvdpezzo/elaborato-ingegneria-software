package it.ingbs.ingegneria_software.gestione_file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.ingbs.ingegneria_software.gestione_file.Convertitori.ConvertiFattori;
import it.ingbs.ingegneria_software.gestione_file.Convertitori.ConvertiRichieste;
import it.ingbs.ingegneria_software.model.comprensori.ComprensorioGeografico;
import it.ingbs.ingegneria_software.model.fattori.FattoriConversione;
import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.model.gerarchie.Gerarchia;
import it.ingbs.ingegneria_software.model.richieste.RichiestaScambio;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;

//classe contenente tutti i dati run-time
public class GestoreDati {

    private static GestoreDati instance;
    private HashMap<Integer, String> comuni;
    private HashMap<Integer, ComprensorioGeografico> comprensori;
    private HashMap<String, Gerarchia> gerarchie;
    private HashMap<String, String> credenzialiConfiguratori;
    private HashMap<String, String> credenzialiFruitori;
    private HashMap<String, Fruitore> datiFruitori;
    private HashMap<String, FattoriConversione> fattori;
    private HashMap<Fruitore, List<RichiestaScambio>> richieste;
    private final HashMap<String, Categoria> categorie;

    private GestoreDati() {
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

    public static GestoreDati getInstance() {
        if (instance == null) {
            instance = new GestoreDati();
        }
        return instance;
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


    // Metodo per convertire i fattori da file in oggetti FattoriConversione
    public void setFattoriFile(HashMap<String, Double> fattori) {
        this.fattori = ConvertiFattori.convertFattori(fattori, categorie);
    }


    // Metodo per aggiornare i fattori di conversione run time dopo una modifica 
    public void setFattori(HashMap<String, FattoriConversione> fattori) {
        this.fattori = fattori;
    }

    public HashMap<Fruitore, List<RichiestaScambio>> getRichieste() {
        return richieste;
    }

    // Metodo per convertire le richieste da file in oggetti RichiestaScambio
    public void setRichiesteFile(HashMap<String, List<String>> richieste) {
        this.richieste = ConvertiRichieste.convertRichieste(richieste, datiFruitori, categorie);
    }

    // Metodo per aggiornare le richieste run time dopo una modifica
    public void setRichieste(HashMap<Fruitore, List<RichiestaScambio>> richieste) {
        this.richieste = richieste;
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