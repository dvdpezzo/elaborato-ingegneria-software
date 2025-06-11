package it.ingbs.ingegneria_software.gestione_file.Convertitori;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.model.richieste.RichiestaScambio;
import it.ingbs.ingegneria_software.model.richieste.Stato;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;

public class ConvertiRichieste {

    public static HashMap<Fruitore, List<RichiestaScambio>> convertRichieste(
            HashMap<String, List<String>> richieste,
            HashMap<String, Fruitore> datiFruitori,
            HashMap<String, Categoria> categorie) {

        HashMap<Fruitore, List<RichiestaScambio>> mappaRichieste = new HashMap<>();

        for (String nomeFruitore : richieste.keySet()) {
            Fruitore fruitore = datiFruitori.get(nomeFruitore);
            List<RichiestaScambio> listaRichieste = new ArrayList<>();
            List<String> richiesteFruitore = richieste.get(nomeFruitore);

            for (int i = 0; i < richiesteFruitore.size(); i += 3) {
                String richiesta = richiesteFruitore.get(i).replace("Richiesta: [[", "").replace("]", "").trim();
                String offerta = richiesteFruitore.get(i + 1).replace("Offerta: [[", "").replace("]", "").trim();
                String[] richiestaParts = richiesta.split(",");
                String[] offertaParts = offerta.split(",");

                Categoria catRichiesta = categorie.get(richiestaParts[0].trim());
                int oreRichieste = Integer.parseInt(richiestaParts[1].trim());
                Categoria catOfferta = categorie.get(offertaParts[0].trim());
                int oreOfferte = Integer.parseInt(offertaParts[1].trim());
                Double fattoreConv = (double) oreOfferte / oreRichieste;
                Stato stato = Stato.valueOf(richiesteFruitore.get(i + 2).replace("Stato: ", "").trim());

                RichiestaScambio richiestaScambio = new RichiestaScambio(catRichiesta, oreRichieste, catOfferta, fruitore, fattoreConv, stato);
                listaRichieste.add(richiestaScambio);
            }

            mappaRichieste.put(fruitore, listaRichieste);
        }

        return mappaRichieste;
    }
}