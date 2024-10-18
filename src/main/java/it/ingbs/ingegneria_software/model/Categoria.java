package it.ingbs.ingegneria_software.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Categoria {

  
    private String nome;  // Nome della categoria
    private CampoCaratteristico campoCaratteristico;  // Campo caratteristico
    private List<Categoria> sottoCategorie ;  // Lista di sottocategorie
    private Map<Categoria, Double> mappaFattoriConversione;

    // Costruttore per una categoria non foglia
    public Categoria(String nome, CampoCaratteristico campoCaratteristico) {
        this.nome = nome;
        this.campoCaratteristico = campoCaratteristico;
        this.sottoCategorie = new ArrayList<>();
    }
    
    // Costruttore per una categoria foglia (senza sottocategorie)
    public Categoria(String nome) {
        this(nome, null);  // Nessun campo caratteristico per le foglie
    }
    
    // Metodo per aggiungere una sottocategoria
    public void aggiungiSottocategoria(Categoria sottocategoria) {
        sottoCategorie.add(sottocategoria);
    }
    
    // Getter per il nome
    public String getNome() {
        return nome;
    }
    
    // Getter per il campo caratteristico
    public CampoCaratteristico getCampoCaratteristico() {
        return campoCaratteristico;
    }
    
    // Getter per le sottocategorie
    public List<Categoria> getSottocategorie() {
        return sottoCategorie;
    }   

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
