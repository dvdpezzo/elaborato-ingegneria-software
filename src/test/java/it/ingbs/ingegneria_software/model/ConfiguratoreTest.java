package it.ingbs.ingegneria_software.model;
   
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
    
public class ConfiguratoreTest {
    
    private static final String DEFAULT = "admin";

    @Test
    void testCreaComprensorioGeografico_withLessThan3Comuni() {
        Configuratore configuratore = new Configuratore(DEFAULT, DEFAULT);
        GestoreComuni gc = new GestoreComuni();
    
        List<String> comuni = new ArrayList<>();
        comuni.add(gc.scegliComune(1)); // Assuming there is at least one comune in the list
        comuni.add(gc.scegliComune(2)); // Assuming there are at least two comuni in the list
    
        try {
            configuratore.creaComprensorioGeografico();
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Un comprensorio deve avere almeno tre comuni limitrofi", e.getMessage());
        }
    }
    @Test
    void testCreaComprensorioGeografico_withMoreThan3Comuni() {
        Configuratore configuratore = new Configuratore(DEFAULT, DEFAULT);
        GestoreComuni gc = new GestoreComuni();
    
        List<String> comuni = new ArrayList<>();
        comuni.add(gc.scegliComune(1)); // Assuming there is at least one comune in the list
        comuni.add(gc.scegliComune(2)); // Assuming there are at least two comuni in the list
        comuni.add(gc.scegliComune(3)); // Assuming there are at least three comuni in the list
        comuni.add(gc.scegliComune(4)); // Adding an additional comune
    
        try {
            configuratore.creaComprensorioGeografico();
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Un comprensorio deve avere almeno tre comuni limitrofi", e.getMessage());
        }
    }
    @Test
    void testCreaComprensorioGeografico_withComuniAlreadyInComprensorio() {
        Configuratore configuratore = new Configuratore(DEFAULT, DEFAULT);
        GestoreComuni gc = new GestoreComuni();
    
        List<String> comuni = new ArrayList<>();
        comuni.add(gc.scegliComune(1)); // Assuming there is at least one comune in the list
        comuni.add(gc.scegliComune(2)); // Assuming there are at least two comuni in the list
        comuni.add(gc.scegliComune(3)); // Assuming there are at least three comuni in the list
    
        ComprensorioGeografico comprensorio = new ComprensorioGeografico(comuni);
        configuratore.aggiungiComune(comuni.get(0), comprensorio.getCodice());
    
        try {
            configuratore.creaComprensorioGeografico();
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Questo comune è già stato inserito!", e.getMessage());
        }
    }
    @Test
    void testCreaComprensorioGeografico_withNonExistentComuneNumber() {
        Configuratore configuratore = new Configuratore(DEFAULT, DEFAULT);
        GestoreComuni gc = new GestoreComuni();
    
        List<String> comuni = new ArrayList<>();
        comuni.add(gc.scegliComune(1)); // Assuming there is at least one comune in the list
        comuni.add(gc.scegliComune(2)); // Assuming there are at least two comuni in the list
        comuni.add(gc.scegliComune(999999)); // Non-existent comune number
    
        try {
            configuratore.creaComprensorioGeografico();
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Il numero del comune inserito non esiste", e.getMessage());
        }
    }
    @Test
    void testCreaComprensorioGeografico_withEmptyListComuni() {
        Configuratore configuratore = new Configuratore(DEFAULT, DEFAULT);
        GestoreComuni gc = new GestoreComuni();
    
        List<String> comuni = new ArrayList<>();
    
        try {
            configuratore.creaComprensorioGeografico();
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Un comprensorio deve avere almeno tre comuni limitrofi", e.getMessage());
        }
    }
}
    