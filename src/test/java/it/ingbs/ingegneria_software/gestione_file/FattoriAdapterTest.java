package it.ingbs.ingegneria_software.gestione_file;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;

import it.ingbs.ingegneria_software.gestione_file.Adapter.FattoriAdapter;
import it.ingbs.ingegneria_software.model.fattori.FattoriConversione;
import it.ingbs.ingegneria_software.model.gerarchie.Categoria;

public class FattoriAdapterTest {

    private HashMap<String, Double> fattori;
    private HashMap<String, Categoria> categorie;

    @Before
    public void setUp() {
        fattori = new HashMap<>();
        categorie = new HashMap<>();
        // Mock categorie
        Categoria catA = new Categoria("A", "");
        Categoria catB = new Categoria("B", "");
        Categoria catC = new Categoria("C", "");
        categorie.put("A", catA);
        categorie.put("B", catB);
        categorie.put("C", catC);
        // Mock fattori
        fattori.put("A->B", 0.5);
        fattori.put("B->C", 1.0);
    }


    /**
     * test che verifica che il metodo convertFattori restituisca una mappa con la dimensione corretta
     */
    @Test
    public void testConvertFattoriReturnsCorrectSize() {
        HashMap<String, FattoriConversione> result = FattoriAdapter.convertFattori(fattori, categorie);
        assertEquals(2, result.size());
    }



    /**
     * test che verifica che il metodo convertFattori restituisca una mappa con valore di conversione
     * e caterogie corrette 
     */
    @Test
    public void testConvertFattoriCorrectValues() {
        HashMap<String, FattoriConversione> result = FattoriAdapter.convertFattori(fattori, categorie);
        FattoriConversione conv = result.get("A->B");
        assertNotNull(conv);
        assertEquals(0.5, conv.getValoreConversione(), 0.0000);
        assertEquals("A", conv.getCategoria1().getNome());
        assertEquals("B", conv.getCategoria2().getNome());
    }

    /**
     * test che verifica che aggiungendo un nuovo fattore di conversione funzioni tutto correttamente
     * (Ripercorre tutti i test fatti in precedenza
     */
    @Test
    public void testConvertFattoriWithMultipleEntries() {
        fattori.put("A->C", 1.5);
        HashMap<String, FattoriConversione> result = FattoriAdapter.convertFattori(fattori, categorie);
        assertEquals(3, result.size());
        assertEquals(1.5, result.get("A->C").getValoreConversione(), 0.0000);
        assertEquals("A", result.get("A->C").getCategoria1().getNome());
        assertEquals("C", result.get("A->C").getCategoria2().getNome());
    }

}

