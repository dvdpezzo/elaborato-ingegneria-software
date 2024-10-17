package it.ingbs.ingegneria_software.model;
   
import org.junit.jupiter.api.Test;
    
public class ConfiguratoreTest {

    private String DEFAULT = "admin";

    @Test
    public void aggiungiComune_shouldThrowException_whenCodiceComprensorioNotFound() {
        Configuratore configuratore = new Configuratore(DEFAULT, DEFAULT);
        String nomeComune = "ComuneTest";
        int codiceComprensorio = 123; // Assuming 123 is not a valid codiceComprensorio
        configuratore.aggiungiComune(nomeComune, codiceComprensorio);
    }
}
    