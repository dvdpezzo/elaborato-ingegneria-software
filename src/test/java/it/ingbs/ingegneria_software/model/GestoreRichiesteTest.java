package it.ingbs.ingegneria_software.model;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.ingbs.ingegneria_software.gestione_file.GestoreFile;
import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;

public class GestoreRichiesteTest {

    private GestoreRichieste gestoreRichieste;
    private GestoreFile gestoreFile;
    private HashMap<Fruitore, List<RichiestaScambio>> mappaRichieste;

    @BeforeEach
    public void setUp() {
        gestoreFile = new GestoreFile();
        mappaRichieste = new HashMap<>();
        gestoreRichieste = new GestoreRichieste(gestoreFile, mappaRichieste);
    }

    @Test
    public void testValutazioneRichiestaDueRichieste() {
        Categoria cat1 = new Categoria("MATE", null);
        Categoria cat2 = new Categoria("FISICA", null);
        Fruitore fruitore1 = new Fruitore("Fruitore1", null, 10, null);
        Fruitore fruitore2 = new Fruitore("Fruitore2", null, 10, null);

        RichiestaScambio richiesta1 = new RichiestaScambio(cat1, 5, cat2, fruitore1, 1.0, Stato.Aperto);
        RichiestaScambio richiesta2 = new RichiestaScambio(cat2, 5, cat1, fruitore2, 1.0, Stato.Aperto);

        gestoreRichieste.addRichiesta(fruitore1, richiesta1);
        gestoreRichieste.addRichiesta(fruitore2, richiesta2);

        gestoreRichieste.valutazioneRichiesta(fruitore1, richiesta1);

        assertEquals(Stato.Chiuso, richiesta1.getStato());
        assertEquals(Stato.Chiuso, richiesta2.getStato());
    }

    @Test
    public void testValutazioneRichiestaCiclica() {
        Categoria cat1 = new Categoria("MATE", null);
        Categoria cat2 = new Categoria("FISICA", null);
        Categoria cat3 = new Categoria("ARTE", null);
        Fruitore fruitore1 = new Fruitore("Fruitore1", "Comprensorio1", 20, null);
        Fruitore fruitore2 = new Fruitore("Fruitore2", "Comprensorio1", 20, null);
        Fruitore fruitore3 = new Fruitore("Fruitore3", "Comprensorio1", 20, null);

        RichiestaScambio richiesta1 = new RichiestaScambio(cat1, 5, cat2, fruitore1, 1.0, Stato.Aperto);
        RichiestaScambio richiesta2 = new RichiestaScambio(cat2, 5, cat3, fruitore2, 1.0, Stato.Aperto);
        RichiestaScambio richiesta3 = new RichiestaScambio(cat3, 5, cat1, fruitore3, 1.0, Stato.Aperto);

        gestoreRichieste.addRichiesta(fruitore1, richiesta1);
        gestoreRichieste.addRichiesta(fruitore2, richiesta2);
        gestoreRichieste.addRichiesta(fruitore3, richiesta3);

        boolean risultato = gestoreRichieste.valutazioneRichiesta(fruitore1, richiesta1);

        assertEquals(true, risultato);
        assertEquals(Stato.Chiuso, richiesta1.getStato());
        assertEquals(Stato.Chiuso, richiesta2.getStato());
        assertEquals(Stato.Chiuso, richiesta3.getStato());
    }

    @Test
    public void testValutazioneRichiestaNonCiclica() {
        Categoria cat1 = new Categoria("MATE", null);
        Categoria cat2 = new Categoria("FISICA", null);
        Categoria cat3 = new Categoria("ARTE", null);
        Fruitore fruitore1 = new Fruitore("Fruitore1", "Comprensorio1", 20, null);
        Fruitore fruitore2 = new Fruitore("Fruitore2", "Comprensorio1", 20, null);
        Fruitore fruitore3 = new Fruitore("Fruitore3", "Comprensorio1", 20, null);

        RichiestaScambio richiesta1 = new RichiestaScambio(cat1, 5, cat2, fruitore1, 1.0, Stato.Aperto);
        RichiestaScambio richiesta2 = new RichiestaScambio(cat2, 5, cat3, fruitore2, 1.0, Stato.Aperto);
        RichiestaScambio richiesta3 = new RichiestaScambio(cat3, 5, cat2, fruitore3, 1.0, Stato.Aperto);

        gestoreRichieste.addRichiesta(fruitore1, richiesta1);
        gestoreRichieste.addRichiesta(fruitore2, richiesta2);
        gestoreRichieste.addRichiesta(fruitore3, richiesta3);

        boolean risultato = gestoreRichieste.valutazioneRichiesta(fruitore1, richiesta1);

        assertEquals(false, risultato);
        assertEquals(Stato.Aperto, richiesta1.getStato());
        assertEquals(Stato.Chiuso, richiesta2.getStato());
        assertEquals(Stato.Chiuso, richiesta3.getStato());
    }

}
