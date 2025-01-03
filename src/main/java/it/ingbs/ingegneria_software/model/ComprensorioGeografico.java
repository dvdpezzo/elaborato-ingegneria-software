package it.ingbs.ingegneria_software.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class ComprensorioGeografico {

    private static final String COMUNI = " Comuni = [";
    private static final String CODICE = "Codice = ";
    private static final String COMUNE_GIÀ_PRESENTE_NEL_COMPRENSORIO_GEOGRAFICO = "Comune già presente nel comprensorio geografico.";
    private static final String COMUNE_AGGIUNTO_CON_SUCCESSO_AL_COMPRENSORIO = "Comune %s aggiunto con successo al comprensorio %d";
    private final int codice;
    private List<String> listaComuni = new ArrayList<>();
    private final Random random = new Random();
    private GestoreComuni gestoreComuni = new GestoreComuni();
    
    
    /**
     * Costruttore per quando creo nuovo (assegna codice random)
     */
    public ComprensorioGeografico(List<String> listaComuni){
        this.codice=generaCodice();
        this.listaComuni=listaComuni;
    }

    /**
     * Costruttore per quando prendo da file
     */
    public ComprensorioGeografico(int codice, String listaComuni) {
        this.codice = codice;
        this.listaComuni = parseComuni(listaComuni);
    }

    /**
     * Analizza una rappresentazione di stringa di un elenco di comuni in una List<String>.
     *
     * <p>La stringa di input dovrebbe essere nel formato "[comune1, comune2, ..., comuneN]".
     * La funzione rimuove le parentesi quadre e divide la stringa dove trova virgole per ottenere
     * una serie di nomi di comuni. Quindi converte l'array in un List<String> e lo restituisce.
     * 
     * @param listaComuni una rappresentazione di stringa di un elenco di comuni.
     * @return List<String> contenente i nomi dei comuni analizzati.
     */
    private List<String> parseComuni(String listaComuni) {
        String[] comuniArray = listaComuni.substring(1, listaComuni.length() - 1).split(", ");
        return new ArrayList<>(Arrays.asList(comuniArray));
    }

    public int getCodice() {
        return codice;
    }
  
    /**
     * genera codice randomico per Comprensorio
     * @return codice generato tra 0 e 9999
     */
    private int generaCodice(){        
        return random.nextInt(9999);
    }

    /**
     * aggiunge comune alla listaComuni se non già presente, altrimenti comunica messaggio d'errore
     * @throws IOException 
     */
    public void aggiungiComuneNuovo(String nomeComune) throws IOException {
        if (!listaComuni.contains(nomeComune)) {
            listaComuni.add(nomeComune);
            gestoreComuni.aggiungiComune(new Comuni(nomeComune)); //aggiungo il comune all'elenco dei comuni e lo visualizzo per verifica. 
            gestoreComuni.scriviComuni();
            gestoreComuni.visualizzaComuni();
            System.out.println(String.format(COMUNE_AGGIUNTO_CON_SUCCESSO_AL_COMPRENSORIO, nomeComune, getCodice()));
        } else {
            System.out.println(COMUNE_GIÀ_PRESENTE_NEL_COMPRENSORIO_GEOGRAFICO);
        }
    }
    
    public List<String> getListaComuni() {
        return listaComuni;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CODICE).append(getCodice()).append(COMUNI);
        for (String comune : getListaComuni()) {
            sb.append(comune).append(", ");
        }
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2); 
        }
        sb.append("]");
        return sb.toString();
    }

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ComprensorioGeografico other = (ComprensorioGeografico) obj;
        return this.listaComuni.equals(other.listaComuni);
    }

}
