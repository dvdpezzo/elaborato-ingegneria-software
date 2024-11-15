package it.ingbs.ingegneria_software.model;
import java.util.*;

public class Categoria {

  
    private String nome;  // Nome della categoria
    private String descrizione; //descrizione generale della categoria
    private List<Categoria> sottoCategorie ;  // Lista di sottocategorie
    private Map<Categoria, Double> mappaFattoriConversione;


    // Costruttore per una categoria NON foglia
    public Categoria(String nome,String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
    }
    
    public void aggiungiFiglio(){};
    
    // Getter per il nome
    public String getNome() {
        return nome;
    }
    
    //-------------------------------------------------------------------------------------------------//
    

    public double calcolaOreEquivalenti(Categoria categoriaDestinazione, double ore) {
        if (this.sottoCategorie.isEmpty()) {
            // Caso base: categoria Foglia
            if (this.equals(categoriaDestinazione)) {
                return ore;
            } else {
                //prendo il fattore di conversione della categoria Destinazione
                Double fattore = this.mappaFattoriConversione.get(categoriaDestinazione);
                if (fattore != null) {
                    return ore * fattore;
                } else {
                    System.out.println("Fattore di conversione non trovato.");
                    return 0;
                }
            }
        } else {
            // Caso recursivo: nel caso non sia una categoria foglia
            double oreEquivalenti = 0;
            for (Categoria sottoCategoria : this.sottoCategorie) {
                oreEquivalenti += sottoCategoria.calcolaOreEquivalenti(categoriaDestinazione, ore);
            }
            return oreEquivalenti;
        }
    }

    public void aggiungiFattoreConversione(Categoria categoria, Double fattore) {
        if (fattore < 0.5 || fattore > 2.0) {
            System.out.println("Fattore di conversione non valido. Deve essere compreso tra 0.5 e 2.0.");
            return;
        }
        if (this.mappaFattoriConversione == null) {
            this.mappaFattoriConversione = new HashMap<>();
        }

        this.mappaFattoriConversione.put(categoria, fattore);
        // Add the inverse factor for the reverse pair
        categoria.aggiungiFattoreConversione(this, 1 / fattore);
    }
    
}
