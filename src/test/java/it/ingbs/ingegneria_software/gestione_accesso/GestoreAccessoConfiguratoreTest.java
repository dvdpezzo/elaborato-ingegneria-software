package it.ingbs.ingegneria_software.gestione_accesso;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.ingbs.ingegneria_software.controller.ServiceProvider;
import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.model.utenti.Configuratore;
import it.ingbs.ingegneria_software.model.utenti.GestoreConfiguratori;

// Java




class GestoreAccessoConfiguratoreTest {

    private GestoreDati gestoreDati;
    private ServiceProvider serviceProvider;
    private GestoreConfiguratori gestoreConfiguratori;
    private GestoreAccessoConfiguratore gestoreAccessoConfiguratore;

    @BeforeEach
    void setUp() {
        gestoreDati = GestoreDati.getInstance();
        // Pulizia credenziali per evitare side effects tra test
        gestoreDati.setCredenzialiConfiguratori(new HashMap<>());
        serviceProvider = new ServiceProvider(gestoreDati);
        gestoreConfiguratori = serviceProvider.getGestoreConfiguratori();
        gestoreAccessoConfiguratore = new GestoreAccessoConfiguratore(serviceProvider);
    }

    @Test
    void testAccessoConfiguratoreClassico_Success() {
        // Arrange
        String username = "utente1";
        String password = "password1";
        HashMap<String, String> credenziali = new HashMap<>();
        credenziali.put(username, password);
        gestoreDati.setCredenzialiConfiguratori(credenziali);

        // Act
        Configuratore configuratore = gestoreAccessoConfiguratore.accesso(username, password);

        // Assert
        assertNotNull(configuratore);
        assertEquals(username, configuratore.getNomeUtente());
        assertEquals(password, configuratore.getPassword());
    }

    @Test
    void testAccessoConfiguratoreClassico_Fail() {
        // Arrange
        String username = "utente2";
        String password = "password2";
        HashMap<String, String> credenziali = new HashMap<>();
        credenziali.put(username, password);
        gestoreDati.setCredenzialiConfiguratori(credenziali);

        // Act & Assert
        assertNull(gestoreAccessoConfiguratore.accesso(username, "wrongpassword"));
        assertNull(gestoreAccessoConfiguratore.accesso("wronguser", password));
    }

    @Test
    void testPrimoAccesso_AdminAdmin() throws Exception {
        // Arrange: Simula input utente per la registrazione
        String nuovoUtente = "nuovoConfig";
        String nuovaPassword = "nuovaPass";
        String inputSimulato = nuovoUtente + "\n" + nuovaPassword + "\n";
        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(inputSimulato.getBytes()));

            // Act
            Configuratore configuratore = gestoreAccessoConfiguratore.accesso("admin", "admin");

            // Assert
            assertNotNull(configuratore);
            assertEquals(nuovoUtente, configuratore.getNomeUtente());
            assertEquals(nuovaPassword, configuratore.getPassword());
            // Verifica che sia stato aggiunto alla mappa
            assertTrue(gestoreConfiguratori.getMappaConfiguratori().containsKey(nuovoUtente));
        } finally {
            System.setIn(originalIn);
        }
    }
}