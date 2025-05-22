package it.ingbs.ingegneria_software.model.gerarchie;

import java.util.HashMap;

import it.ingbs.ingegneria_software.Eccezioni.CategoriaNotFoundException;
import it.ingbs.ingegneria_software.Eccezioni.CategoriaOmonimaException;
import it.ingbs.ingegneria_software.Eccezioni.IllegalCampoException;
import it.ingbs.ingegneria_software.Eccezioni.PadreNotFoundException;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;

 public class Gerarchia {
    
    private static final String CATEGORIA_S_AGGIUNTA = "Categoria %s aggiunta!";
    private static final String NOME_CATEGORIA = "Nome della categoria: ";
    private static final String DESCRIZIONE_CATEGORIA = "Descrizione della categoria: ";
    private static final String CHI_E_IL_PADRE = "Chi è il padre della categoria? ";
    private static final String VUOI_AGGIUNGERE_ALTRE_CATEGORIE = "Vuoi aggiungere altre categorie?";
    private static final String VUOI_AGGIUNGERE_UNA_DESCRIZIONE = "Vuoi aggiungere una descrizione?";
    private static final String CATEGORIA_DA_ELIMINARE = "Nome della categoria da eliminare: ";
    private static final String CATEGORIA_S_RIMOSSA = "Categoria %s rimossa!";
    private static final String ERRORE_MODIFICA_CATEGORIA_INESISTENTE = "Categoria inesistente!";

    private static final String CAMPO_RADICE = "Radice";
    private final HashMap<String, Categoria> sottoCategorie;
    private final Categoria categoriaRadice;

    /**
     * costruttore della Gerarchia
     * crea una categoria radice e aggiunge due campi nativi predefiniti
     * @param nome nome della categoria radice
     * @param desc descrizione della categoria radice
     */
    public Gerarchia(String nome, String desc) {
        Categoria radice = new Categoria(nome.toUpperCase(), desc);
        try {
            radice.addCampoNativo(CAMPO_RADICE);
            radice.addCampoNativo(desc);

        } catch (IllegalCampoException e) {
            e.printStackTrace();
        }

        sottoCategorie = new HashMap<>();
        sottoCategorie.put(radice.getNome(), radice);

        categoriaRadice = radice;
    }

    /**
     * Aggiunge una sottocategoria
     *
     * @param nome      nome della categoria
     * @param desc      descrizione della categoria
     * @param nomePadre nome della categoria padre (deve essere presente nella gerarchia)
     * @throws PadreNotFoundException quando non trova nella gerarchia la categoria padre specificata
     * @throws CategoriaOmonimaException quando la categoria che si tenta di aggiungere è già presente
     */
    public void addSottocategoria(String nome, String desc, String nomePadre) throws PadreNotFoundException, CategoriaOmonimaException {
        if (sottoCategorie.containsKey(nomePadre.toUpperCase())) {
            if (sottoCategorie.containsKey(nome.toUpperCase()))
                throw new CategoriaOmonimaException();
            else {
                Categoria nuova = new Categoria(nome, desc);
                Categoria padre = sottoCategorie.get(nomePadre.toUpperCase());
                padre.addFiglio(nuova);
                nuova.setPadre(padre);
                sottoCategorie.put(nuova.getNome().toUpperCase(), nuova);
            }
        } else
            throw new PadreNotFoundException();
    }
    
    public void aggiungiCategoria() {
        do {
            System.out.println(toString());
            String nomeCategoria, descrizioneCategoria;
            nomeCategoria = InputDati.leggiStringaNonVuota(NOME_CATEGORIA);
            boolean aggiungiDescrizione = InputDati.yesOrNo(VUOI_AGGIUNGERE_UNA_DESCRIZIONE);
            if (aggiungiDescrizione) {
                descrizioneCategoria = InputDati.leggiStringaNonVuota(DESCRIZIONE_CATEGORIA);
            } else {
                descrizioneCategoria = " ";
            }
            String nomePadre = InputDati.leggiStringaNonVuota(CHI_E_IL_PADRE);
            try {
                addSottocategoria(nomeCategoria.toUpperCase(), descrizioneCategoria, nomePadre);
                getCategoria(nomeCategoria.toUpperCase()).addCampoNativo(" "); // Aggiunge un campo nativo vuoto
                System.out.printf((CATEGORIA_S_AGGIUNTA) + "%n", nomeCategoria);
            } catch (PadreNotFoundException | CategoriaOmonimaException | IllegalCampoException | CategoriaNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } while (InputDati.yesOrNo(VUOI_AGGIUNGERE_ALTRE_CATEGORIE));
    }

    /**
     * Aggiunge una sotto-categoria per la creazione dell'albero della gerarchia durante la fase di caricamento del
     * programma.
     *
     * @param nuova categoria da aggiungere
     * @param nomePadre nome del padre
     * @throws PadreNotFoundException quando non trova nella gerarchia la categoria padre specificata
     * @throws CategoriaOmonimaException quando la categoria che si tenta di aggiungere è già presente
     */

    public void addSottocategoria(Categoria nuova, String nomePadre) throws PadreNotFoundException, CategoriaOmonimaException {
        if (sottoCategorie.containsKey(nomePadre.toUpperCase())) {
            if (sottoCategorie.containsKey(nuova.getNome().toUpperCase()))
                throw new CategoriaOmonimaException();
            else {
                Categoria padre = sottoCategorie.get(nomePadre.toUpperCase());
                padre.addFiglio(nuova);
                nuova.setPadre(padre);
                sottoCategorie.put(nuova.getNome().toUpperCase(), nuova);
            }
        } else
            throw new PadreNotFoundException();
    }

    public void rimuoviCategoria() {
        System.out.println(toString());
        String nomeCategoria = InputDati.leggiStringaNonVuota(CATEGORIA_DA_ELIMINARE);
        try {
            rimuoviCategoria(nomeCategoria);
            System.out.printf((CATEGORIA_S_RIMOSSA) + "%n", nomeCategoria);
        } catch (CategoriaNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Rimuove la categoria specificata dalla gerarchia
     *
     * @param nome il nome della categoria da rimuovere;
     * @throws CategoriaNotFoundException se non viene trovata una categoria col nome specificato
     */
    private void rimuoviCategoria(String nome) throws CategoriaNotFoundException {
        Categoria daRimuovere;
        if (sottoCategorie.containsKey(nome.toUpperCase())) {
            daRimuovere = sottoCategorie.get(nome.toUpperCase());
            daRimuovere.getPadre().removeFiglio(daRimuovere);
            this.sottoCategorie.remove(nome.toUpperCase());
        } else
            throw new CategoriaNotFoundException();
    }

    /**
     * Per le modifiche relative ai campi della categoria
     */
    public void modificaCampiCategoria() {
        try {
            toString();
            //selezione della categoria da modificare all'interno della gerarchia
            String nomeCategoria = InputDati.leggiStringaNonVuota("Nome categoria da modificare: ");
            Categoria categoria = getCategoria(nomeCategoria);
            if (categoria != null) {
                categoria.modificaCampi();
            } else {
                System.out.println(ERRORE_MODIFICA_CATEGORIA_INESISTENTE);
            }
        } catch (CategoriaNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Recupera una determinata categoria presente nella gerarchia
     * specificata tramite il nome
     *
     * @param nomeCategoria nome della categoria da cercare
     * @return categoria cercata
     * 
     */
    public Categoria getCategoria(String nomeCategoria) throws CategoriaNotFoundException {            
        return sottoCategorie.get(nomeCategoria.toUpperCase());
        
    }

    public Categoria getCategoriaRadice() {
        return this.categoriaRadice;
    }

    /**
     * ToString ricorsivo che partendo dalla radice visualizza l'albero della gerarchia
     *
     * @return l'albero della gerarchia
     */
    @Override
    public String toString() {
        return categoriaRadice.toString(0);
    }
    
    public HashMap<String, Categoria> getSottoCategorie() {
        return sottoCategorie;
    }
   
}