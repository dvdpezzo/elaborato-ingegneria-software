package it.ingbs.ingegneria_software.gestione_file;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public interface GestoreFile {

    void salvaSuFile(File nomeFile) throws IOException;

    HashMap leggiFile(File nomeFile) throws IOException;

}
