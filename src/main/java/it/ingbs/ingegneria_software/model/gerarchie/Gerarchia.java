package it.ingbs.ingegneria_software.model.gerarchie;

import java.util.HashMap;

import it.ingbs.ingegneria_software.Eccezioni.CategoriaNotFoundException;
import it.ingbs.ingegneria_software.Eccezioni.CategoriaOmonimaException;
import it.ingbs.ingegneria_software.Eccezioni.IllegalCampoException;
import it.ingbs.ingegneria_software.Eccezioni.PadreNotFoundException;


public class Gerarchia {
    
    private final HashMap<String, Categoria> sottoCategorie;
    private final Categoria categoriaRadice;

    /**
     * costruttore della Gerarchia
     *
     * @param nome nome della categoria radice
     * @param desc descrizione della categoria radice
     */
    public Gerarchia(String nome, String desc) {
        Categoria radice = new Categoria(nome.toUpperCase(), desc);
        try {
            radice.addCampoNativo("Stato di Conservazione", true);
            radice.addCampoNativo("Descrizione Libera", false);

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

    /**
     * Rimuove la categoria specificata dalla gerarchia
     *
     * @param nome il nome della categoria da rimuovere;
     * @throws CategoriaNotFoundException se non viene trovata una categoria col nome specificato
     */
    public void rimuoviCategoria(String nome) throws CategoriaNotFoundException {
        Categoria daRimuovere;
        if (sottoCategorie.containsKey(nome.toUpperCase())) {
            daRimuovere = sottoCategorie.get(nome.toUpperCase());
            daRimuovere.getPadre().removeFiglio(daRimuovere);
            this.sottoCategorie.remove(nome.toUpperCase());
        } else
            throw new CategoriaNotFoundException();
    }

    /**
     * Recupera una determinata categoria presente nella gerarchia
     * specificata tramite il nome
     *
     * @param nomeCategoria nome della categoria da cercare
     * @return categoria cercata
     * @throws CategoriaNotFoundException se non viene trovata una categoria col nome specificato
     */
    public Categoria getCategoria(String nomeCategoria) throws CategoriaNotFoundException {            //può lanciare un'eccezione che al momento non viene gestita
        if (sottoCategorie.containsKey(nomeCategoria.toUpperCase()))
            return sottoCategorie.get(nomeCategoria.toUpperCase());
        else
            throw new CategoriaNotFoundException();
    }

    public Categoria getCategoriaRadice() {
        return this.categoriaRadice;
    }

    /**
     * Sposta una categoria da un punto a un altro dell'albero
     * Cambiando il riferimento alla categoria padre della categoria selezionata
     *
     * @param cat nome della categoria da spostare
     * @param padreNuovo nome della nuova categoria padre
     * @throws CategoriaNotFoundException se non viene trovata una categoria col nome specificato
     */

    public void spostaCategoria(String cat, String padreNuovo) throws CategoriaNotFoundException {
        Categoria daSpost, padre, padreN;
        daSpost = this.getCategoria(cat);
        padre = daSpost.getPadre();
        padreN = getCategoria(padreNuovo);

        padreN.addFiglio(daSpost);
        padre.removeFiglio(daSpost);
        daSpost.setPadre(padreN);
    }

    /**
     * ToString ricorsivo che partendo dalla radice visualizza l'albero della gerarchia, chiamando ricorsivamente il
     * metodo per ogni SottoCategoria figlia
     *
     * @return l'albero della gerarchia
     */
    public String toString() {
        return toString(categoriaRadice.getNome());
    }

    public String toString(String nomeCategoria) {
        Categoria c = sottoCategorie.get(nomeCategoria.toUpperCase());

        return c.toString(0);
    }

   
}
