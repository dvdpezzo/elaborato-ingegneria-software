package it.ingbs.ingegneria_software.gestione_file;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class GestoreFileComprensori implements GestoreFile {

    private Map<String, String> mappaComprensori; 

    public GestoreFileComprensori(Map<String, String> mappaComprensori) {
        this.mappaComprensori = mappaComprensori;
    }
    @Override
    public void salvaSuFile(File nomeFile) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'salvaSuFile'");
    }

    @Override
    public void leggiFile(File nomeFile) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'leggiFile'");
    }

}
