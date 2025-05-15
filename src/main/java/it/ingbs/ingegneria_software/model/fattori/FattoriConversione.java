package it.ingbs.ingegneria_software.model.fattori;

import it.ingbs.ingegneria_software.model.gerarchie.CategoriaFoglia;

public class FattoriConversione {
    
    private final CategoriaFoglia catOfferta ;
    private final CategoriaFoglia catRichiesta ;
    private final double valoreConversione;

    /**
     * Costruttore della classe FattoriConversione.
     * 
     * @param valoreConversione il valore del fattore di conversione
     * @param catOfferta la prima categoria foglia
     * @param categoriaFoglia2 la seconda categoria foglia
     */
    public FattoriConversione(double valoreConversione, CategoriaFoglia catOfferta, CategoriaFoglia catRichiesta) {
        this.valoreConversione = valoreConversione;
        this.catOfferta = catOfferta;
        this.catRichiesta = catRichiesta;
    }

    /**
     * Restituisce la prima categoria.
     * 
     * @return la prima categoria
     */
    public CategoriaFoglia getCategoriaOfferta() {
        return catOfferta;
    }

    /**
     * Restituisce la seconda categoria.
     * 
     * @return la seconda categoria
     */
    public CategoriaFoglia getCategoriaRichiesta() {
        return catRichiesta;
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