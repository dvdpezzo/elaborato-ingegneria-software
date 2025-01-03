package it.ingbs.ingegneria_software.gestione_file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import it.ingbs.ingegneria_software.model.Categoria;
import it.ingbs.ingegneria_software.model.CategoriaFoglia;
import it.ingbs.ingegneria_software.model.Gerarchia;

/*
 * - rivedere il modo di scrittura su file delle categorie: quando salvo le informazioni che permangono davvero e non vengono
 *  modificate ad un secondo avvio sono le gerarchie; le categorie "foglia" vengono sovrascritte
 * - modificare il modo di aggiunta delle categorie: selezionare più padri, al momento viene vista come "padre" solo la categoria radice
 */

public class GestoreFileGerarchie implements GestoreFile{

    private static final String CATEGORIA_FOGLIA = " - CategoriaFoglia";
    private HashMap<String, Gerarchia> mappaGerarchie; 

    public GestoreFileGerarchie(HashMap<String, Gerarchia> mappaGerarchie) {
        this.mappaGerarchie = mappaGerarchie;
    }

    @Override
public void salvaSuFile(File nomeFile) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile))) {
        for (HashMap.Entry<String, Gerarchia> entry : mappaGerarchie.entrySet()) {
            Gerarchia gerarchia = entry.getValue();
            writer.write(entry.getKey());
            writer.write("\t|\t");
            scriviCategorie(writer, gerarchia.getCategoriePrincipali(), 0);
            writer.newLine();
        }
    }
}

private void scriviCategorie(BufferedWriter writer, HashMap<String, Categoria> categorie, int livello) throws IOException {
    for (HashMap.Entry<String, Categoria> entry : categorie.entrySet()) {
        if (livello > 0) {
            writer.write("\t|\t");
        }
        Categoria categoria = entry.getValue();
        writer.write(entry.getKey() + ":" + categoria.getDescrizione());
        if (!categoria.isFoglia()) {
            scriviCategorie(writer, categoria.getSottoCategorie(), livello + 1);
        } else {
            writer.write(CATEGORIA_FOGLIA);
        }
    }
}

@Override
public HashMap<String, Gerarchia> leggiFile(File nomeFile) throws IOException {
    HashMap<String, Gerarchia> gerarchie = new HashMap<>();
    
    try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\t\\|\t");
            String nomeGerarchia = parts[0];
            Gerarchia gerarchia = new Gerarchia(nomeGerarchia);
            gerarchie.put(nomeGerarchia, gerarchia);
            
            if (parts.length > 1) {
                String[] categorie = parts[1].split("\t\\|\t");
                Stack<Categoria> stack = new Stack<>();
                Map<String, Categoria> categoriePrincipali = gerarchia.getCategoriePrincipali();
                
                for (String categoriaStr : categorie) {
                    String nomeCat;
                    String descrizioneCat = "";
                    boolean isFoglia = categoriaStr.endsWith(CATEGORIA_FOGLIA);
                    if (isFoglia) {
                        categoriaStr = categoriaStr.replace(CATEGORIA_FOGLIA, "");
                    }
                    
                    String[] categoriaParts = categoriaStr.split(":", 2);
                    nomeCat = categoriaParts[0].trim();
                    if (categoriaParts.length > 1) {
                        descrizioneCat = categoriaParts[1].trim();
                    }
                    
                    Categoria categoria = isFoglia ? new CategoriaFoglia(nomeCat, descrizioneCat) : new Categoria(nomeCat, descrizioneCat);
                    
                    if (stack.isEmpty()) {
                        categoriePrincipali.put(nomeCat, categoria);
                    } else {
                        stack.peek().getSottoCategorie().put(nomeCat, categoria);
                    }
                    
                    if (!isFoglia) {
                        stack.push(categoria);
                    } else {
                        while (!stack.isEmpty() && stack.peek().getSottoCategorie().size() == 1) {
                            stack.pop();
                        }
                    }
                }
            }
        }
    }
    
    return gerarchie;
}

}
