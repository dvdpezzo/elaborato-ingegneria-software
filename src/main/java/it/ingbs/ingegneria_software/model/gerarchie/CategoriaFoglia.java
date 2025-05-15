package it.ingbs.ingegneria_software.model.gerarchie;

public class CategoriaFoglia implements Component {

    private final String nome;
    private final String descrizione;
    private Categoria padre;

    // Costruttore
    public CategoriaFoglia(String nome, String descrizione) {
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

    // Metodi di utilità
    @Override
    public String toString(int livello) {
        String indentazione = new String(new char[livello]).replace("\0", "\t");
        return indentazione + nome + " [" + descrizione + "]\n";
    }

}
