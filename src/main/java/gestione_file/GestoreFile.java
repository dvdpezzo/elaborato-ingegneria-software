package gestione_file;

import java.io.File;
import java.io.IOException;

public interface GestoreFile {

    void salvaSuFile(File nomeFile) throws IOException;

    void leggiFile(File nomeFile) throws IOException;

}
