package it.ingbs.ingegneria_software.model.fattori;

import it.ingbs.ingegneria_software.model.gerarchie.Categoria;

public class FattoriConversione {
    
    private Categoria categoria1;
    private Categoria categoria2;
    private double valoreConversione;

    /**
     * Costruttore della classe FattoriConversione.
     * 
     * @param valoreConversione il valore del fattore di conversione
     * @param categoriaFoglia1 la prima categoria foglia
     * @param categoriaFoglia2 la seconda categoria foglia
     */
    public FattoriConversione(double valoreConversione, Categoria categoriaFoglia1, Categoria categoriaFoglia2) {
        this.valoreConversione = valoreConversione;
        this.categoria1 = categoriaFoglia1;
        this.categoria2 = categoriaFoglia2;
    }

    /**
     * Restituisce la prima categoria.
     * 
     * @return la prima categoria
     */
    public Categoria getCategoria1() {
        return categoria1;
    }

    /**
     * Restituisce la seconda categoria.
     * 
     * @return la seconda categoria
     */
    public Categoria getCategoria2() {
        return categoria2;
    }

    /**
     * Restituisce il valore del fattore di conversione.
     * 
     * @return il valore del fattore di conversione
     */
    public double getValoreConversione() {
        return valoreConversione;
    }
}