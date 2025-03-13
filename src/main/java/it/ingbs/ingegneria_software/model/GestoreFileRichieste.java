package it.ingbs.ingegneria_software.model;

import java.io.*;
import java.util.*;

import it.ingbs.ingegneria_software.Eccezioni.CategoriaNotFoundException;
import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.model.gerarchie.GestoreGerarchie;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;
import it.ingbs.ingegneria_software.model.utenti.GestoreFruitori;

public class GestoreFileRichieste {
    

    private final File fileRichieste = new File("src\\Data_File\\elencoRichieste.txt");
    private GestoreRichieste gestoreRichieste;
    private GestoreFruitori gestoreFruitori;  
    private GestoreGerarchie gestoreGerarchie;
        
          /**
         * Metodo che salva la mappa delle richieste su file
         */
        public void salvaSuFile() {
            HashMap<Fruitore, List<RichiestaScambio>> mappaRichieste = gestoreRichieste.getMappaRichieste();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileRichieste))) {
                for (Map.Entry<Fruitore, List<RichiestaScambio>> entry : mappaRichieste.entrySet()) {
                    Fruitore fruitore = entry.getKey();
                    for (RichiestaScambio richiesta : entry.getValue()) {
                        writer.write(fruitore.getNomeUtente()+ "\n");
                        writer.write(richiesta.toString() + "\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        /**
         * Metodo che legge le richieste di scambio da file
         * @param gestoreFruitori gestore dei fruitori
         * @throws CategoriaNotFoundException 
         */
        public void leggiDaFile() throws CategoriaNotFoundException {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileRichieste))) {
                String line;
                Fruitore currentFruitore = null;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Fruitore: ")) {
                        String nickname = line.substring(10);
                        currentFruitore = gestoreFruitori.trovaFruitore(nickname);
                } else if (line.startsWith("Richiesta: [")) {
                    String[] richiestaParts = line.substring(11, line.length() - 1).split(",");
                    Categoria catRichiesta = gestoreGerarchie.getCategoriaRichiesta(line);
                    int oreRichieste = Integer.parseInt(richiestaParts[1]);
                    line = reader.readLine(); // Read the next line for Offerta
                    String[] offertaParts = line.substring(9, line.length() - 1).split(",");
                    Categoria catOfferta = gestoreGerarchie.getCategoriaRichiesta(line);
                    int oreOfferte = Integer.parseInt(offertaParts[1]);
                    RichiestaScambio richiesta = new RichiestaScambio(catRichiesta, oreRichieste, catOfferta, currentFruitore, 1.0);
                    gestoreRichieste.addRichiesta(currentFruitore, richiesta);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
}

