package it.ingbs.ingegneria_software.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class GestoreFile {
    
    public void salvaSuFileCategoria(File nomeFile, Categoria oggetto) throws IOException{
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeFile))) {
             String salvataggio = oggetto.getNome();
                bw.write(salvataggio);
                bw.newLine();
            }
        }

    }

