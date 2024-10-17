package it.ingbs.ingegneria_software.gestioneAccesso;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import it.ingbs.ingegneria_software.gestione_accesso.GestoreAccesso;
import it.ingbs.ingegneria_software.model.Configuratore;

class GestoreAccessoTest {
        
    @Test
    void testAccessoUtenteWithDefaultCredentials() {
        GestoreAccesso gestoreAccesso = new GestoreAccesso();
        Configuratore result = gestoreAccesso.accessoConfiguratore("admin", "admin");
        assertNotNull(result);
        assertTrue(result instanceof Configuratore);
    }
}
    