package it.ingbs.ingegneria_software.model.fattori;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.model.gerarchie.CategoriaFoglia;
import it.ingbs.ingegneria_software.model.gerarchie.Gerarchia;
import it.ingbs.ingegneria_software.model.gerarchie.GestoreGerarchie;

public class GestoreFattoriTest {
    private GestoreFattori gestoreFattori;
    private GestoreDati gestoreDati;
    private GestoreGerarchie gestoreGerarchie;

    @Before
    public void setup() {
        gestoreDati = GestoreDati.getInstance();
        gestoreGerarchie = new GestoreGerarchie(gestoreDati);
        gestoreFattori = new GestoreFattori(gestoreGerarchie, gestoreDati);

        // Configura categorie foglia per i test
        CategoriaFoglia catOfferta = new CategoriaFoglia("CategoriaOfferta", "DescrizioneOfferta");
        CategoriaFoglia catRichiesta = new CategoriaFoglia("CategoriaRichiesta", "DescrizioneRichiesta");
        gestoreGerarchie.getRadici().put("TestGerarchia", new Gerarchia("Radice", "DescrizioneRadice"));
        gestoreGerarchie.getRadici().get("TestGerarchia").getCategorieFoglia().add(catOfferta);
        gestoreGerarchie.getRadici().get("TestGerarchia").getCategorieFoglia().add(catRichiesta);
    }

    @Test
    public void testCreazioneFattore() {
        CategoriaFoglia catOfferta = gestoreGerarchie.getRadici().get("TestGerarchia").getCategorieFoglia().get(0);
        CategoriaFoglia catRichiesta = gestoreGerarchie.getRadici().get("TestGerarchia").getCategorieFoglia().get(1);

        gestoreFattori.creaFattore(catOfferta, catRichiesta, 1.5); // Simula la creazione di un fattore
        
        // Simula l'aggiunta di un fattore

        String chiaveFattore = catOfferta.getNome() + "->" + catRichiesta.getNome();
        assertTrue(gestoreDati.getFattori().containsKey(chiaveFattore));
    }

    @Test
    public void testRimozioneFattore() {
        CategoriaFoglia catOfferta = gestoreGerarchie.getRadici().get("TestGerarchia").getCategorieFoglia().get(0);
        CategoriaFoglia catRichiesta = gestoreGerarchie.getRadici().get("TestGerarchia").getCategorieFoglia().get(1);

        // Crea un fattore per poi rimuoverlo
        FattoriConversione fattore = new FattoriConversione(1.5, catOfferta, catRichiesta);
        gestoreDati.getFattori().put(catOfferta.getNome() + "->" + catRichiesta.getNome(), fattore);

        gestoreFattori.rimuovi(); // Simula la rimozione del fattore

        String chiaveFattore = catOfferta.getNome() + "->" + catRichiesta.getNome();
        assertFalse(gestoreDati.getFattori().containsKey(chiaveFattore));
    }
}
