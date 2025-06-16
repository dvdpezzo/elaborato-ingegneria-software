package it.ingbs.ingegneria_software.gestione_file;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

public class GestoreFileTest {

    private GestoreFile gestoreFile;
    private GestoreDati gestoreDati;

    @Before
    public void setup() {
        gestoreFile = new GestoreFile();
        gestoreDati = gestoreFile.getGestoreDati();
    }

    @Test
    public void testCaricaSalvataggio() throws IOException {
        gestoreFile.caricaSalvataggio();
        
        // Verifica che tutti i dati siano stati caricati correttamente
        assertNotNull("I comuni non dovrebbero essere null", gestoreDati.getComuni());
        assertNotNull("I comprensori non dovrebbero essere null", gestoreDati.getComprensori());
        assertNotNull("Le gerarchie non dovrebbero essere null", gestoreDati.getGerarchie());
        assertNotNull("Le credenziali dei configuratori non dovrebbero essere null", 
            gestoreDati.getCredenzialiConfiguratori());
        assertNotNull("Le credenziali dei fruitori non dovrebbero essere null", 
            gestoreDati.getCredenzialiFruitori());
        assertNotNull("I dati dei fruitori non dovrebbero essere null", 
            gestoreDati.getDatiFruitori());
        assertNotNull("I fattori non dovrebbero essere null", 
            gestoreDati.getFattori());
        assertNotNull("Le richieste non dovrebbero essere null", 
            gestoreDati.getRichieste());
    }

    @Test
    public void testCreaSalvataggio() {
        try {
            gestoreFile.creaSalvataggio();
            assertTrue("Il salvataggio dovrebbe essere completato con successo", true);
        } catch (Exception e) {
            fail("Non dovrebbe essere lanciata alcuna eccezione durante il salvataggio: " + e.getMessage());
        }
    }

    @Test
    public void testCaricaDatiFruitori() {
        HashMap<String, ?> datiFruitori = gestoreFile.caricaDatiFruitori();
        assertNotNull("I dati dei fruitori non dovrebbero essere null", datiFruitori);
    }

    @Test
    public void testSalvataggiParziali() {
        try {
            gestoreFile.salvaComuni();
            gestoreFile.salvaComprensori();
            gestoreFile.salvaGerarchie();
            gestoreFile.salvaCredenzialiConfiguratori();
            gestoreFile.salvaCredenzialiFruitori();
            gestoreFile.salvaFattori();
            gestoreFile.salvaRichieste();
            gestoreFile.salvaDatiFruitori();
            assertTrue("Tutti i salvataggi parziali dovrebbero essere completati con successo", true);
        } catch (Exception e) {
            fail("Non dovrebbe essere lanciata alcuna eccezione durante i salvataggi parziali: " + e.getMessage());
        }
    }

    @After
    public void cleanup() {
        // Pulizia delle risorse se necessario
        gestoreFile = null;
        gestoreDati = null;
    }
}
