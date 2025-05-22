package it.ingbs.ingegneria_software.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.model.richieste.GestoreRichieste;
import it.ingbs.ingegneria_software.model.richieste.RichiestaScambio;
import it.ingbs.ingegneria_software.model.richieste.Stato;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;

public class GestoreRichiesteTest {

    private GestoreRichieste gestoreRichieste;
    private GestoreDati gestoreDati;

    @BeforeEach
    public void setUp() {
        gestoreDati = GestoreDati.getInstance();
        gestoreRichieste = new GestoreRichieste(gestoreDati);
    }

    @Test
    public void testValutazioneRichiestaDueRichieste() {
        Categoria cat1 = new Categoria("MATE", null);
        Categoria cat2 = new Categoria("FISICA", null);
        Fruitore fruitore1 = new Fruitore("Fruitore1", null, 10, null);
        Fruitore fruitore2 = new Fruitore("Fruitore2", null, 10, null);

        RichiestaScambio richiesta1 = gestoreRichieste.creaRichiesta(cat1, cat2, 5, fruitore1, 1.0, Stato.Aperto);
        RichiestaScambio richiesta2 = gestoreRichieste.creaRichiesta(cat2, cat1, 5, fruitore2, 1.0, Stato.Aperto);

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

        RichiestaScambio richiesta1 = gestoreRichieste.creaRichiesta(cat1, cat2, 5, fruitore1, 1.0, Stato.Aperto);
        RichiestaScambio richiesta2 = gestoreRichieste.creaRichiesta(cat2, cat3, 5, fruitore2, 1.0, Stato.Aperto);
        RichiestaScambio richiesta3 = gestoreRichieste.creaRichiesta(cat3, cat1, 5, fruitore3, 1.0, Stato.Aperto);

        gestoreRichieste.valutazioneRichiesta(fruitore1, richiesta1);

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

        RichiestaScambio richiesta1 =  gestoreRichieste.creaRichiesta(cat1, cat2, 5, fruitore1, 1.0, Stato.Aperto);
        RichiestaScambio richiesta2 =  gestoreRichieste.creaRichiesta(cat2, cat3, 5, fruitore2, 1.0, Stato.Aperto);
        RichiestaScambio richiesta3 =  gestoreRichieste.creaRichiesta(cat3, cat2, 5, fruitore3, 1.0, Stato.Aperto);

        gestoreRichieste.valutazioneRichiesta(fruitore1, richiesta1);

        assertEquals(Stato.Aperto, richiesta1.getStato());
        assertEquals(Stato.Chiuso, richiesta2.getStato());
        assertEquals(Stato.Chiuso, richiesta3.getStato());
    }

    @Test
    public void testRitiraRichiesta_Success() {
        Categoria cat1 = new Categoria("MATE", null);
        Fruitore fruitore1 = new Fruitore("Fruitore1", null, 10, null);
        RichiestaScambio richiesta1 =  gestoreRichieste.creaRichiesta(cat1, cat1, 5, fruitore1, 1.0, Stato.Aperto);

        gestoreRichieste.ritiraRichiesta(fruitore1, richiesta1);

        assertEquals(Stato.Ritirato, richiesta1.getStato());
    }

    @Test
    public void testRitiraRichiesta_AlreadyChiuso() {
        Categoria cat1 = new Categoria("MATE", null);
        Fruitore fruitore1 = new Fruitore("Fruitore1", null, 10, null);
        RichiestaScambio richiesta1 =  gestoreRichieste.creaRichiesta(cat1, cat1, 5, fruitore1, 1.0, Stato.Chiuso);

        gestoreRichieste.ritiraRichiesta(fruitore1, richiesta1);

        assertEquals(Stato.Chiuso, richiesta1.getStato());
    }

}
