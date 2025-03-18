package it.ingbs.ingegneria_software.gestione_file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.ingbs.ingegneria_software.model.RichiestaScambio;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;

public class GestoreFileRichieste {

    private final File fileRichieste;

    public GestoreFileRichieste(String nomeFile) {
        this.fileRichieste = new File(nomeFile);
    }

    /**
     * Metodo che salva la mappa delle richieste su file
     */
    public void salvaSuFile(HashMap<Fruitore, List<RichiestaScambio>> mappaRichieste) {
        if (mappaRichieste.isEmpty()) {
            System.out.println("Non sono presenti richieste.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileRichieste))) {
            for (Map.Entry<Fruitore, List<RichiestaScambio>> entry : mappaRichieste.entrySet()) {
                Fruitore fruitore = entry.getKey();
                writer.write("Fruitore: " + fruitore.getNomeUtente() + "\n");
                for (RichiestaScambio richiesta : entry.getValue()) {
                    writer.write(richiesta.toString() + "\n");
                }
            }
            System.out.println("Richieste salvate correttamente");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo che legge le richieste di scambio da file
     *
     * @return una mappa contenente il nome del fruitore e una lista di stringhe con i dati delle richieste
     */
    public HashMap<String, List<String>> leggiFile() {
        HashMap<String, List<String>> mappaRichieste = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileRichieste))) {
            String line;
            String currentFruitore = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Fruitore: ")) {
                    currentFruitore = line.substring(10).trim();
                } else if (line.startsWith("Richiesta: [")) {
                    String richiesta = line.substring(11, line.length() - 1).trim();
                    mappaRichieste.computeIfAbsent(currentFruitore, k -> new ArrayList<>()).add("Richiesta: [" + richiesta + "]");
                } else if (line.startsWith("Offerta: [")) {
                    String offerta = line.substring(9, line.length() - 1).trim();
                    mappaRichieste.computeIfAbsent(currentFruitore, k -> new ArrayList<>()).add("Offerta: [" + offerta + "]");
                } else if (line.startsWith("Stato: ")) {
                    String stato = line.substring(7).trim();
                    mappaRichieste.computeIfAbsent(currentFruitore, k -> new ArrayList<>()).add("Stato: " + stato);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mappaRichieste;
    }
}