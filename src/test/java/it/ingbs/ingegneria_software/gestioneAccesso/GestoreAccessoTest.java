package it.ingbs.ingegneria_software.gestioneAccesso;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import it.ingbs.ingegneria_software.model.Configuratore;
import it.ingbs.ingegneria_software.model.Utente;

class GestoreAccessoTest {
        
    @Test
    void testAccessoUtenteWithDefaultCredentials() {
        GestoreAccesso gestoreAccesso = new GestoreAccesso();
        Utente result = gestoreAccesso.accessoUtente("admin", "admin");
        assertNotNull(result);
        assertTrue(result instanceof Configuratore);
    }
}
    