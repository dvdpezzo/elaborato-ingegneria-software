package it.ingbs.ingegneria_software.gestione_file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.ingbs.ingegneria_software.model.comprensori.ComprensorioGeografico;
import it.ingbs.ingegneria_software.model.gerarchie.Gerarchia;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;

public class GestoreDatiTest {

    private GestoreDati gestoreDati;

    @BeforeEach
    public void setup() {
        gestoreDati = new GestoreDati();
    }

    @Test
    public void testSetAndGetComuni() {
        HashMap<Integer, String> comuni = new HashMap<>();
        comuni.put(1, "Comune1");
        gestoreDati.setComuni(comuni);
        assertEquals(comuni, gestoreDati.getComuni());
    }

    @Test
    public void testSetAndGetComprensori() {
        HashMap<Integer, ComprensorioGeografico> comprensori = new HashMap<>();
        ComprensorioGeografico comprensorio = new ComprensorioGeografico(new ArrayList<>());
        comprensori.put(1, comprensorio);
        gestoreDati.setComprensori(comprensori);
        assertEquals(comprensori, gestoreDati.getComprensori());
    }

    @Test
    public void testSetAndGetGerarchie() {
        HashMap<String, Gerarchia> gerarchie = new HashMap<>();
        Gerarchia gerarchia = new Gerarchia("Radice", "Descrizione");
        gerarchie.put("Gerarchia1", gerarchia);
        gestoreDati.setGerarchie(gerarchie);
        assertEquals(gerarchie, gestoreDati.getGerarchie());
    }

    @Test
    public void testSetAndGetCredenzialiConfiguratori() {
        HashMap<String, String> credenziali = new HashMap<>();
        credenziali.put("configuratore1", "password1");
        gestoreDati.setCredenzialiConfiguratori(credenziali);
        assertEquals(credenziali, gestoreDati.getCredenzialiConfiguratori());
    }

    @Test
    public void testSetAndGetCredenzialiFruitori() {
        HashMap<String, String> credenziali = new HashMap<>();
        credenziali.put("fruitore1", "password1");
        gestoreDati.setCredenzialiFruitori(credenziali);
        assertEquals(credenziali, gestoreDati.getCredenzialiFruitori());
    }

    @Test
    public void testSetAndGetDatiFruitori() {
        HashMap<String, Fruitore> datiFruitori = new HashMap<>();
        Fruitore fruitore = new Fruitore("fruitore1", "password1", 123, "email@example.com");
        datiFruitori.put("fruitore1", fruitore);
        gestoreDati.setDatiFruitori(datiFruitori);
        assertEquals(datiFruitori, gestoreDati.getDatiFruitori());
    }

    @Test
    public void testSetAndGetFattori() {
        HashMap<String, Double> fattori = new HashMap<>();
        fattori.put("Categoria1->Categoria2", 1.0);
        gestoreDati.setFattori(fattori);
        assertNotNull(gestoreDati.getFattori().get("Categoria1->Categoria2"));
    }

    @Test
    public void testSetAndGetRichieste() {
        HashMap<String, List<String>> richieste = new HashMap<>();
        List<String> listaRichieste = new ArrayList<>();
        listaRichieste.add("Richiesta: [Categoria1, 2]");
        Fruitore fruitore = new Fruitore("fruitore1", "password1", 123, "email@example.com");
        richieste.put(fruitore.getNomeUtente(), listaRichieste);
        gestoreDati.setRichieste(richieste);
        assertNotNull(gestoreDati.getRichieste().get(fruitore.getNomeUtente()));
        assertEquals(listaRichieste, gestoreDati.getRichieste().get(fruitore.getNomeUtente())); // nuova verifica
    }

    @Test
    public void testGetUtenti() {
        HashMap<String, String> configuratori = new HashMap<>();
        configuratori.put("configuratore1", "password1");
        HashMap<String, String> fruitori = new HashMap<>();
        fruitori.put("fruitore1", "password1");
        gestoreDati.setUtenti(configuratori, fruitori);
        List<String> utenti = gestoreDati.getUtenti();
        assertTrue(utenti.contains("configuratore1"));
        assertTrue(utenti.contains("fruitore1"));
    }
}