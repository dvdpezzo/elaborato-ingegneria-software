package it.ingbs.ingegneria_software.model.gerarchie;

import java.util.List;

import it.ingbs.ingegneria_software.model.fattori.FattoriConversione;

public class CategoriaFoglia extends Categoria {

    private final String nome;
    private final String descrizione;
    private Categoria padre;
    private List<FattoriConversione> fattoriConversione;

    // Costruttore
    public CategoriaFoglia(String nome, String descrizione) {
        super(nome, descrizione);
        this.nome = nome;
        this.descrizione = descrizione;
        this.padre = null;
    }

    // Getter
    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public String getDescrizione() {
        return this.descrizione;
    }

    @Override
    public void setPadre(Categoria padre) {
        this.padre = padre;
    }

    @Override
    public Categoria getPadre() {
        return this.padre;
    }

    // Metodi di utilità
    @Override
    public String toString(int livello) {
        String indentazione = new String(new char[livello]).replace("\0", "\t");
        return indentazione + nome + " [" + descrizione + "]\n";
    }

    @Override
    public void addFiglio(Component nuovaCategoria) {
        throw new UnsupportedOperationException("Non è possibile aggiungere figli a una categoria foglia.");
    }

    public void setFattoriConversione(FattoriConversione fattoreConversione) {
        if (!this.fattoriConversione.contains(fattoreConversione)) {
            this.fattoriConversione.add(fattoreConversione);
        }
    }

    public void setFattoriConversioneOpposto(FattoriConversione fattoreConversione) {
        if (!this.fattoriConversione.contains(fattoreConversione)) {
            this.fattoriConversione.add(fattoreConversione);
        }
    }
}
