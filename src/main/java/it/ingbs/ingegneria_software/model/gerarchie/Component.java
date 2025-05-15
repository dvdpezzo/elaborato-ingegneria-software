package it.ingbs.ingegneria_software.model.gerarchie;

public interface Component {
    String getNome();
    String getDescrizione();
    String toString(int livello);
    void setPadre(Categoria padre);
}
