package it.ingbs.ingegneria_software.gestione_file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import it.ingbs.ingegneria_software.model.fattori.FattoriConversione;

public class GestoreFileFattori {

    private final File fileFattori;

    public GestoreFileFattori(String nomeFile) {
        this.fileFattori = new File(nomeFile);
    }

    /**
     * Legge i fattori di conversione da un file e li restituisce come una mappa.
     *
     * @return una mappa contenente i fattori di conversione
     */
    public HashMap<String, Double> leggiFile() {
        HashMap<String, Double> mappaFattori = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileFattori))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Processa ogni riga del file
                String[] parti = linea.split(" -> |: ");  // Divide la riga in base ai separatori
                if (parti.length == 3) {
                    String nomeCategoria1 = parti[0].trim();
                    String nomeCategoria2 = parti[1].trim();
                    double valore = Double.parseDouble(parti[2].trim());

                    // Aggiunge il fattore di conversione alla mappa
                    mappaFattori.put(nomeCategoria1 + "->" + nomeCategoria2, valore);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mappaFattori;
    }

    
    /**
     * Scrive su file i vari fattori di conversione
     * @throws IOException
     */
    public void salvaSuFile(HashMap<String, FattoriConversione> mappaFattori) throws IOException {
        try (FileWriter writer = new FileWriter(fileFattori)) {
            // Itera sulla mappa dei fattori di conversione e scrive ogni coppia su una riga
            for (FattoriConversione fattore : mappaFattori.values()) {
                // Scrive la riga con le categorie e il valore del fattore
                writer.append(fattore.getCategoria1().getNome() + " -> " +
                        fattore.getCategoria2().getNome() + ": " +
                        fattore.getValoreConversione() + "\n");
            }
        } finally {
            System.out.println("Fattori di conversione salvati correttamente");
        }
    }

}
