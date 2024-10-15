package it.ingbs.ingegneria_software.model;
import java.util.List;

public class Categoria {

    public Categoria(String nome) {
    }

    private String nome;
    private String descrizione;
    private CampoCaratteristico aCampoCaratteristico;
    private List<Categoria> listaSottoCategorie;

    private FattoreConversione f;

    public Categoria(String nome, CampoCaratteristico campoCaratteristico, FattoreConversione f) {
        this.nome = nome;
        this.aCampoCaratteristico = campoCaratteristico;
        this.f = f;
    }



    public void aggiungiSottoCategoria(Categoria categoria) {
        listaSottoCategorie.add(categoria);
    }
    
}
