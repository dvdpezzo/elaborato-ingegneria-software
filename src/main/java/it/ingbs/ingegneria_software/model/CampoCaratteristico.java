package it.ingbs.ingegneria_software.model;
import java.util.List;

public class CampoCaratteristico {

    private String nomeCampo;
    private List<Valore> dominioValori;
    
    public CampoCaratteristico(String nomeCampo, List<Valore> dominioValori) {
        this.nomeCampo = nomeCampo;
        this.dominioValori = dominioValori;
    }

    
}
