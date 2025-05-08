package it.ingbs.ingegneria_software.model.fattori;

import java.util.HashMap;

import it.ingbs.ingegneria_software.Eccezioni.CategoriaNotFoundException;
import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.model.gerarchie.Gerarchia;
import it.ingbs.ingegneria_software.model.gerarchie.GestoreGerarchie;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;

public class GestoreFattori {

    private static final String INSERISCI_IL_NOME_DELLA_GERARCHIA = "Inserisci il nome della Gerarchia:";
    private static final String ERRORE_CATEGORIA = "Le categorie che hai inserito non sono categorie foglia!";
    private static final String CATEGORIA_RICERCATA = "Inserisci il nome della categoria ricercata:";
    private static final String INSERISCI_VALORE_CONVERSIONE = "Inserisci il valore di conversione:";
    private static final String FATTORI_CONVERSIONE_CREATI = "Fattori di conversione creati";
    private static final String FATTORI_CONVERSIONE_SALVATI = "Fattori di conversione salvati";
    private static final String FATTORI_CONVERSIONE_DERIVATO_CREATI = "Fattore di conversione derivato creato";
    private static final String RIMUOVI_FATTORE_DA = "Da quale categoria vuoi rimuovere il fattore? ";
    private static final String RIMUOVI_FATTORE_VERSO = "Verso quale categoria vuoi rimuovere il fattore? ";

    private final HashMap<String, FattoriConversione> mappaFattori;
    private final GestoreGerarchie gestoreGerarchie;
    private final GestoreDati gestoreDati;

    public GestoreFattori(HashMap<String, FattoriConversione> mappaFattori, GestoreGerarchie gestoreGerarchie, GestoreDati gestoreDati) {
        this.mappaFattori = mappaFattori;
        this.gestoreGerarchie = gestoreGerarchie;
        this.gestoreDati = gestoreDati;
        
    }

    /**
     * Crea un fattore di conversione e lo inserisce nella mappa dei fattori 
     * e in automatico crea quello opposto
     * 
     * @param categoria1 prima categoria inserita 
     * @param categoria2 seconda categoria inserita 
     * @param valoreConversione indica il valore di conversione tra la prima e la seconda categoria 
     */
    private void assegnaFattoreConversione(Categoria categoria1, Categoria categoria2, double valoreConversione) {
        if (!(categoria1.hasFiglio(categoria1) && categoria2.hasFiglio(categoria2))) {

            String chiave = categoria1.getNome() + "->" + categoria2.getNome();
            for(String chiaveEsistente : mappaFattori.keySet()) {
                if (chiave.equalsIgnoreCase(chiaveEsistente)) {
                    System.out.println("Il fattore di conversione esiste già!");
                    return;
                }
            }
            FattoriConversione fattore = new FattoriConversione(valoreConversione, categoria1, categoria2);
            mappaFattori.put(chiave, fattore);
            System.out.println(FATTORI_CONVERSIONE_CREATI);



            //creo in automatico il fattore opposto 
            fattoreOpposto(categoria1, categoria2, valoreConversione);

        } else {
            System.out.println(ERRORE_CATEGORIA);
        }
    }

    /**
     * Crea il fattore di conversione opposto rispetto a quello inserito nel metodo assegnaFattoreConversione()
     * 
     * @param categoria1 
     * @param categoria2
     * @param valoreConversione
     */
    private void fattoreOpposto(Categoria categoria1, Categoria categoria2, double valoreConversione) {

        String chiaveOpposta = categoria2.getNome() + "->" + categoria1.getNome();
        FattoriConversione fattoreOpposto = new FattoriConversione(1 / valoreConversione, categoria2, categoria1);
        mappaFattori.put(chiaveOpposta, fattoreOpposto);
    }

    /**
     * Crea un fattore di conversione derivandolo da due fattori già esistenti
     * @param categoria1 categoria dalla quale prendo il valore
     * @param categoria2 categoria dalla quale prendo il valore 
     * @param categoria3 categoria delle quale voglio creare un valore 
     */
    public void fattoreDerivato(Categoria categoria1, Categoria categoria2, Categoria categoria3) {
        String chiave12 = categoria1.getNome() + "->" + categoria2.getNome();
        String chiave23 = categoria2.getNome() + "->" + categoria3.getNome();
        if (mappaFattori.containsKey(chiave12) && mappaFattori.containsKey(chiave23)) {
            double valoreConversione1 = mappaFattori.get(chiave12).getValoreConversione();
            double valoreConversione2 = mappaFattori.get(chiave23).getValoreConversione();
            double valoreDerivato = valoreConversione1 * valoreConversione2;

            assegnaFattoreConversione(categoria1, categoria3, valoreDerivato);
        }
    }

    /**
     * Chiede al configuratore quale fattore di conversione vuole creare 
     */
    public void nuovoFattore() {

        Categoria categoria1, categoria2;
        do {
            try {
                categoria1 = trovaCategoria();
                categoria2 = trovaCategoria();
            } catch (Exception ex) {
                System.out.println("Errore categoria inesistente");
                return;
            }
        } while (categoria1.hasFiglio(categoria1) || categoria2.hasFiglio(categoria2));
        double valoreConversione = InputDati.leggiDoubleLimitato(INSERISCI_VALORE_CONVERSIONE, 0.5, 2);
        assegnaFattoreConversione(categoria1, categoria2, valoreConversione);
    }

    /**
     * Chiede al configuratore quale fattore di conversione derivato vuole calcolare 
     */
    public void nuovoFattoreDerivato() {
        visualizzaFattori();
        Categoria categoria1, categoria2, categoria3;
        do {
            try {
                categoria1 = trovaCategoria();
                categoria2 = trovaCategoria();
                categoria3 = trovaCategoria();
            } catch (Exception ex) {
                System.out.println("Errore categoria inesistente");
                return;
            }
        } while (categoria1.hasFiglio(categoria1) || categoria2.hasFiglio(categoria2) || categoria3.hasFiglio(categoria3));
        fattoreDerivato(categoria1, categoria2, categoria3);
        System.out.println(FATTORI_CONVERSIONE_DERIVATO_CREATI);
    }


    /**
     * Visualizza tutti i fattori di conversione
     */
    public void visualizzaFattori() {
        for (String chiave : mappaFattori.keySet()) {
            System.out.println(chiave + " : " + mappaFattori.get(chiave).getValoreConversione());
        }
    }

    /**
     * Stampa le gerarchie utilizzando GestoreGerarchie.
     */
    public void stampaGerarchie() {
        gestoreGerarchie.stampaGerarchie();
    }

    /**
     * Salva i fattori di conversione su file.
     */
    public void salvaFattori() {
        gestoreDati.setFattori(mappaFattori);
        System.out.println(FATTORI_CONVERSIONE_SALVATI);
    }

    /**
     * Trova la categoria inserita dall'utente
     * @return la categoria inserita dall'utente     * 
     */
    private Categoria trovaCategoria() throws Exception{

        String nomeGerarchia = InputDati.leggiStringaNonVuota(INSERISCI_IL_NOME_DELLA_GERARCHIA);
        Gerarchia gerarchiaRicercata = gestoreGerarchie.getGerarchia(nomeGerarchia);
        String nomeCategoria = InputDati.leggiStringaNonVuota(CATEGORIA_RICERCATA);
        return gerarchiaRicercata.getCategoria(nomeCategoria);
    }

    /**
     * Restituisce la categoria con il nome specificato
     * @param nomeCategoria il nome della categoria da cercare
     * @return la categoria con il nome specificato
     * 
     */
    public Categoria getCategoria(String nomeCategoria) {
        for (Gerarchia gerarchia : gestoreGerarchie.getRadici().values()) {
            try {
                Categoria categoria = gerarchia.getCategoria(nomeCategoria);
                if (categoria != null) {
                    return categoria;
                }
            } catch (CategoriaNotFoundException ex) {
                ex.printStackTrace();


                
            }
        }
       return null;
    }

    /**
     * Rimuove un fattore di conversione dalla mappa dei fattori
     */
    public void rimuoviFattore() {
        visualizzaFattori();
        String nomeCategoria1 = InputDati.leggiStringaNonVuota(RIMUOVI_FATTORE_DA);
        String nomeCategoria2 = InputDati.leggiStringaNonVuota(RIMUOVI_FATTORE_VERSO);
        mappaFattori.remove(nomeCategoria1.toUpperCase() + "->" + nomeCategoria2.toUpperCase());
        mappaFattori.remove(nomeCategoria2.toUpperCase() + "->" + nomeCategoria1.toUpperCase());
        salvaFattori();
    }

    /*
     * ritorna il valore del fattore di conversione data la sua stringa 
     */
    public Double getFattore(String nomeFattore){
         return  mappaFattori.get(nomeFattore).getValoreConversione();
    }
}