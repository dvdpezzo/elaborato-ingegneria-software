package it.ingbs.ingegneria_software.model.comprensori;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ComprensorioGeografico {

    private static final String COMUNI = " Comuni = [";
    private static final String CODICE = "Codice = ";
    private static final String COMUNE_GIA_PRESENTE_NEL_COMPRENSORIO_GEOGRAFICO = "Comune già presente nel comprensorio geografico.";
    private static final String COMUNE_AGGIUNTO_CON_SUCCESSO_AL_COMPRENSORIO = "Comune %s aggiunto con successo al comprensorio %d";
    private static final String ERRORE_COMUNE_NON_TROVATO = "Comune non trovato!";

    private final int codiceComprensorio;
    private List<String> listaComuni = new ArrayList<>();
    private final Random random = new Random();
    private final GestoreComuni gestoreComuni = new GestoreComuni();

    /**
     * Costruttore per creare un nuovo comprensorio con un codice randomico.
     *
     * @param listaComuni la lista dei comuni del comprensorio
     */
    public ComprensorioGeografico(List<String> listaComuni) {
        this.codiceComprensorio = generaCodice();
        this.listaComuni = listaComuni;
    }

    /**
     * Costruttore per creare un comprensorio da file.
     *
     * @param codice il codice del comprensorio
     * @param listaComuni la lista dei comuni del comprensorio in formato stringa
     */
    public ComprensorioGeografico(int codice, String listaComuni) {
        this.codiceComprensorio = codice;
        this.listaComuni = parseComuni(listaComuni);
    }

    /**
     * Analizza una stringa di un elenco di comuni in una List<String>.
     *
     * @param listaComuni stringa di un elenco di comuni
     * @return List<String> contenente i nomi dei comuni analizzati
     */
    private List<String> parseComuni(String listaComuni) {
        String[] comuniArray = listaComuni.substring(1, listaComuni.length() - 1).split(", ");
        return new ArrayList<>(Arrays.asList(comuniArray));
    }

    /**
     * Restituisce il codice del comprensorio.
     *
     * @return il codice del comprensorio
     */
    public int getCodice() {
        return codiceComprensorio;
    }

    /**
     * Genera un codice randomico per il comprensorio.
     *
     * @return codice generato tra 0 e 9999
     */
    private int generaCodice() {
        return random.nextInt(9999);
    }

    /**
     * Aggiunge un nuovo comune alla lista dei comuni se non già presente.
     *
     * @param nomeComuneDaAggiungere il nome del comune da aggiungere
     * @throws IOException se si verifica un errore durante la scrittura del file
     */
    public void aggiungiComuneNuovo(String nomeComuneDaAggiungere) throws IOException {
        String nomeComune = nomeComuneDaAggiungere.toUpperCase();
        if (!listaComuni.contains(nomeComune)) {
            listaComuni.add(nomeComune);
            gestoreComuni.aggiungiComune(new Comuni(nomeComune));
            gestoreComuni.scriviComuni();
            System.out.println(String.format(COMUNE_AGGIUNTO_CON_SUCCESSO_AL_COMPRENSORIO, nomeComune, getCodice()));
        } else {
            System.out.println(COMUNE_GIA_PRESENTE_NEL_COMPRENSORIO_GEOGRAFICO);
        }
    }

    /**
     * Restituisce la lista dei comuni del comprensorio.
     *
     * @return la lista dei comuni
     */
    public List<String> getListaComuni() {
        return listaComuni;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CODICE).append(getCodice()).append(COMUNI);
        for (String comune : getListaComuni()) {
            sb.append(comune.toUpperCase()).append(", ");
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
