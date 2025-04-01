package it.ingbs.ingegneria_software.gestione_file;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class GestoreFileTest {

    private GestoreFile gestoreFile;

    @Before
    public void setup() {
        gestoreFile = new GestoreFile();
    }

    @Test
    public void testCaricaSalvataggio() {
        try {
            gestoreFile.creaSalvataggio(); // Assicura che i file siano creati prima di caricarli
            gestoreFile.caricaSalvataggio();
            assertNotNull(gestoreFile.getGestoreDati().getComuni());
            assertNotNull(gestoreFile.getGestoreDati().getComprensori());
            assertNotNull(gestoreFile.getGestoreDati().getGerarchie());
            assertNotNull(gestoreFile.getGestoreDati().getCredenzialiConfiguratori());
            assertNotNull(gestoreFile.getGestoreDati().getCredenzialiFruitori());
            assertNotNull(gestoreFile.getGestoreDati().getDatiFruitori());
            assertNotNull(gestoreFile.getGestoreDati().getFattori());
            assertNotNull(gestoreFile.getGestoreDati().getRichieste());
        } catch (IOException e) {
            fail("IOException thrown during test: " + e.getMessage());
        }
    }

}
