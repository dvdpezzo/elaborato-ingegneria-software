package it.ingbs.ingegneria_software.model.gerarchie;

import java.util.Collection;
import java.util.Vector;

import it.ingbs.ingegneria_software.Eccezioni.CampoNotFoundException;
import it.ingbs.ingegneria_software.Eccezioni.IllegalCampoException;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class Categoria {

    private static final String CAMPO_S_CORRETTAMENTE_AGGIUNTO = "Campo %s correttamente aggiunto!";
    private static final String CAMPO_NATIVO_ELIMINATO = "Campo nativo eliminato!";
    private static final String CAMPI_NATIVI_ELIMINATI = "Campi nativi eliminati!";
    private static final String VUOI_AGGIUNGERE_ALTRI_CAMPI = "Vuoi aggiungere altri campi?";
    private static final String VUOI_ELIMINARE_ALTRI_CAMPI = "Vuoi eliminare altri campi?";
    private static final String NOME_DEL_CAMPO = "Nome del campo: ";
    private static final String SCELTA_ELIMINAZ_CAMPI = "Vuoi eliminare tutti i campi nativi? (1) o solo alcuni? (2)";

    private final String descrizione;
    private final Vector<Campo> campiNativi;
    private final Vector<Categoria> figli;
    private String nome;
    private Categoria padre;
    //private GestoreFattori gf;
    /**
     * Costruttore della categoria
     *
     * @param nome nome della categoria
     * @param descrizione descrizione della categoria
     */
    public Categoria(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.padre = null;
        this.campiNativi = new Vector<>();
        this.figli = new Vector<>();
    }

    /**
     * Restituisce il padre della categoria.
     *
     * @return il padre della categoria
     */
    public Categoria getPadre() {
        return this.padre;
    }

    /**
     * Imposta il padre della categoria.
     *
     * @param padre il padre della categoria
     */
    public void setPadre(Categoria padre) {
        this.padre = padre;
    }

    /**
     * Aggiunge un figlio alla categoria.
     *
     * @param nuovaCategoria la nuova categoria da aggiungere come figlio
     */
    public void addFiglio(Categoria nuovaCategoria) {
        this.figli.add(nuovaCategoria);
        nuovaCategoria.setPadre(this);
    }

    /**
     * Rimuove un figlio dalla categoria.
     *
     * @param categoriaDaRimuovere la categoria da rimuovere
     */
    public void removeFiglio(Categoria categoriaDaRimuovere) {
        this.figli.remove(categoriaDaRimuovere);
        categoriaDaRimuovere.setPadre(null);
    }

     public void modificaCampi() {
        MenuUtil menuLavoro = new MenuUtil("Cosa desideri fare?", new String[]{"aggiungi campo", "rimuovi campo"});
        int scelta;
        do {
            scelta = menuLavoro.scegli();
            switch (scelta) {
                case 1:
                    aggiungiCampi();
                    break;
                case 2:
                    rimuoviCampi();
                    break;
            }
        } while (scelta != 0);
    }

     private void aggiungiCampi() {
        String nomeCampo;
        do {
            nomeCampo = InputDati.leggiStringaNonVuota(NOME_DEL_CAMPO);
            try {
                this.addCampoNativo(nomeCampo);
                System.out.printf((CAMPO_S_CORRETTAMENTE_AGGIUNTO) + "%n", nomeCampo);
            } catch (IllegalCampoException e) {
                System.out.println(e.getMessage());
            }
        } while (InputDati.yesOrNo(VUOI_AGGIUNGERE_ALTRI_CAMPI));
    }

    private void rimuoviCampi() {
        String nomeCampo;
        int scelta = InputDati.leggiIntero(SCELTA_ELIMINAZ_CAMPI, 1, 2);
        try {
            if (scelta == 1) {
                this.eliminaCampiNativi();
                this.addCampoNativo(" "); // Aggiunge un campo vuoto
                System.out.println(CAMPI_NATIVI_ELIMINATI);
            } else {
                System.out.println(this.stampaCampiNativi());
                do {
                    nomeCampo = InputDati.leggiStringaNonVuota(NOME_DEL_CAMPO);
                    eliminaCampoNativo(nomeCampo);
                    System.out.println(CAMPO_NATIVO_ELIMINATO);
                } while (InputDati.yesOrNo(VUOI_ELIMINARE_ALTRI_CAMPI));
                if (getCampiNativi().isEmpty()) {
                    addCampoNativo(" "); // Aggiunge un campo vuoto se non ci sono più campi
                }
            }
        } catch (IllegalCampoException | CampoNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Elimina tutti i campi nativi della categoria.
     */
    public void eliminaCampiNativi() {
        this.campiNativi.clear();
    }

    /**
     * Elimina un campo nativo specifico dalla categoria.
     *
     * @param nomeCampo il nome del campo da eliminare
     * @throws CampoNotFoundException se il campo non viene trovato
     */
    public void eliminaCampoNativo(String nomeCampo) throws CampoNotFoundException {
        Campo campoDaEliminare = this.getCampoNativo(nomeCampo);
        this.campiNativi.remove(campoDaEliminare);
    }

    /**
     * Restituisce un campo nativo specifico della categoria.
     *
     * @param nome il nome del campo
     * @return il campo nativo
     * @throws CampoNotFoundException se il campo non viene trovato
     */
    public Campo getCampoNativo(String nome) throws CampoNotFoundException {
        for (Campo campo : campiNativi) {
            if (campo.nome.equalsIgnoreCase(nome))
                return campo;
        }
        throw new CampoNotFoundException();
    }

    /**
     * Stampa una rappresentazione grafica dell'albero generato.
     * Un contatore tiene traccia del livello del nodo e, in base a questo valore, il metodo imposta quante indentazioni
     * iniziali presenta il suddetto nodo.
     * Ogni nodo presenta una descrizione della categoria, in particolare il nome e i suoi campi nativi tra parentesi.
     *
     * @param livello contatore del livello del nodo dell'albero
     * @return stringa con una rappresentazione grafica dell'albero
     */
    public String toString(int livello) {
        StringBuilder sb = new StringBuilder();
        sb.append(nome);
        sb.append(this.stampaCampiNativi());
        sb.append("\n");
        livello++;
        for (Categoria figlio : figli) {
            String indentazione = new String(new char[livello]).replace("\0", "\t");
            sb.append(indentazione);
            sb.append(figlio.toString(livello));
        }
        return sb.toString();
    }

    /**
     * Controlla se una categoria possiede dei figli.
     *
     * @param categoria la categoria da controllare
     * @return true se possiede figli, false se non li possiede
     */
    public boolean hasFiglio(Categoria categoria) {
        return !categoria.figli.isEmpty();
    }

    /**
     * Aggiunge un campo nativo alla categoria.
     *
     * @param nome il nome del campo
     * @throws IllegalCampoException se il campo è duplicato o non valido
     */
    public void addCampoNativo(String nome) throws IllegalCampoException {
        for (Campo campo : this.getCampi()) {
            if (campo.nome.equalsIgnoreCase(nome)) {
                throw new IllegalCampoException();
            } else if (campo.nome.equals(" ") || campo.nome.equals("")) {
                campiNativi.clear();
            }
        }
        campiNativi.add(new Campo(nome));
    }

    /**
     * Restituisce tutti i campi ereditati di una categoria.
     * Se la categoria selezionata è una radice, allora restituisce i suoi campi nativi.
     *
     * @return un Vector con i campi ereditati
     */
    public Vector<Campo> getCampi() {
        Vector<Campo> campiEreditati;
        if (padre != null) {
            campiEreditati = padre.getCampi();
        } else {
            return new Vector<>(campiNativi);
        }

        campiEreditati.addAll(campiNativi);

        return campiEreditati;
    }

    /**
     * Stampa i campi nativi della categoria.
     *
     * @return una stringa con i campi nativi della categoria
     */
    public String stampaCampiNativi() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (Campo campo : campiNativi) {
            sb.append(campo.nome).append(", ");
        }
        if (sb.length() > 3)
            sb.deleteCharAt(sb.length() - 2);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Restituisce i campi nativi della categoria.
     *
     * @return una collezione di campi nativi
     */
    public Collection<Campo> getCampiNativi() {
        return campiNativi;
    }

    /**
     * Restituisce il nome della categoria.
     *
     * @return il nome della categoria
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome della categoria.
     *
     * @param nuovoNome il nuovo nome della categoria
     */
    public void setNome(String nuovoNome) {
        this.nome = nuovoNome;
    }

    /**
     * Restituisce la descrizione della categoria.
     *
     * @return la descrizione della categoria
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Restituisce i figli della categoria.
     *
     * @return un Vector con i figli della categoria
     */
    public Vector<Categoria> getFigli() {
        return figli;
    }
}

class Campo {
    String nome;

    /**
     * Costruttore del campo
     *
     * @param nome nome del campo
     */
    public Campo(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return String.format("#%s", nome);
    }
}