package it.ingbs.ingegneria_software.model.fattori;

import it.ingbs.ingegneria_software.model.gerarchie.Categoria;

/**
 * Rappresenta un fattore di conversione tra due categorie.
 */
public class FattoriConversione {
    private final Categoria categoria1;
    private final Categoria categoria2;
    private final double valoreConversione;

    public FattoriConversione(double valoreConversione, Categoria categoriaFoglia1, Categoria categoriaFoglia2) {
        this.valoreConversione = valoreConversione;
        this.categoria1 = categoriaFoglia1;
        this.categoria2 = categoriaFoglia2;
    }

    public Categoria getCategoria1() { return categoria1; }
    public Categoria getCategoria2() { return categoria2; }
    public double getValoreConversione() { return valoreConversione; }
}