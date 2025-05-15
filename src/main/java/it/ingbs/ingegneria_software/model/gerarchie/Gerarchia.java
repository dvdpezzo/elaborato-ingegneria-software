package it.ingbs.ingegneria_software.model.gerarchie;

import java.util.ArrayList;
import java.util.List;

import it.ingbs.ingegneria_software.Eccezioni.CategoriaNotFoundException;
import it.ingbs.ingegneria_software.Eccezioni.IllegalCampoException;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.UtilityHandler;

public class Gerarchia implements UtilityHandler {

    private static final String NOME_CATEGORIA = "Nome Categoria: ";
    private static final String DESCRIZIONE_CATEGORIA = "Descrizione Categoria: ";
    private static final String CHI_E_IL_PADRE = "A quale categoria desideri aggiungerla? ";
    private static final String CATEGORIA_DA_ELIMINARE = "Nome Categoria da eliminare: ";
    private static final String CATEGORIA_S_AGGIUNTA = "Categoria %s aggiunta!";
    private static final String CATEGORIA_S_RIMOSSA = "Categoria %s rimossa!";
    private static final String VUOI_AGGIUNGERE_ALTRE_CATEGORIE = "Vuoi aggiungere altre categorie? ";
    private static final String VUOI_AGGIUNGERE_UNA_DESCRIZIONE = "Vuoi aggiungere una descrizione?";
    private static final String ERRORE_MODIFICA_CATEGORIA_INESISTENTE = "ERRORE: si sta tentando di modificare una categoria inesistente";

    private final Categoria categoriaRadice;
    private List<CategoriaFoglia> categorieFoglia = new ArrayList<>();
    private List<Component> categorie = new ArrayList<>();

    /**
     * Costruttore della Gerarchia
     * Crea una categoria radice e aggiunge due campi nativi predefiniti.
     *
     * @param nome nome della categoria radice
     * @param desc descrizione della categoria radice
     */
    public Gerarchia(String nome, String desc) {
        this.categoriaRadice = new Categoria(nome.toUpperCase(), desc);
        try {
            categoriaRadice.addCampoNativo("Radice");
            categoriaRadice.addCampoNativo(desc);
        } catch (IllegalCampoException ex) {
        }

    }

    public Categoria getCategoriaRadice() {
        return categoriaRadice;
    }

    @Override
    public String toString() {
        return categoriaRadice.toString(0);
    }

    /**
     * Aggiunge una categoria alla gerarchia.
     */
    @Override
    public void aggiungi() {
        do {
            System.out.println(this.toString());
            String nomeCategoria = InputDati.leggiStringaNonVuota(NOME_CATEGORIA);
            String descrizioneCategoria = InputDati.yesOrNo(VUOI_AGGIUNGERE_UNA_DESCRIZIONE)
                    ? InputDati.leggiStringaNonVuota(DESCRIZIONE_CATEGORIA)
                    : " ";
            String nomePadre = InputDati.leggiStringaNonVuota(CHI_E_IL_PADRE);
            boolean isFoglia = InputDati.yesOrNo("La categoria è una foglia?");

            try {
                Categoria padre = this.getCategoriaRadice().cercaCategoria(nomePadre);
                Categoria nuovaCategoria;

                if (isFoglia) {
                    nuovaCategoria = new CategoriaFoglia(nomeCategoria, descrizioneCategoria);
                    categorieFoglia.add((CategoriaFoglia) nuovaCategoria);
                } else {
                    nuovaCategoria = new Categoria(nomeCategoria, descrizioneCategoria);
                }

                padre.addFiglio(nuovaCategoria);
                System.out.printf((CATEGORIA_S_AGGIUNTA) + "%n", nomeCategoria);

            } catch (CategoriaNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } while (InputDati.yesOrNo(VUOI_AGGIUNGERE_ALTRE_CATEGORIE));
    }

    /**
     * Rimuove una categoria dalla gerarchia.
     *
     */
    @Override
    public void rimuovi() {
        System.out.println(this.toString());
        String nomeCategoria = InputDati.leggiStringaNonVuota(CATEGORIA_DA_ELIMINARE);
        try {
            Categoria categoriaDaRimuovere = this.getCategoriaRadice().cercaCategoria(nomeCategoria);

            if (categoriaDaRimuovere instanceof CategoriaFoglia) {
                categorieFoglia.remove((CategoriaFoglia) categoriaDaRimuovere);
                // Logica per rimuovere i fattori associati alla categoria foglia
                // ...aggiungi qui il codice per rimuovere i fattori...
            }

            this.getCategoriaRadice().rimuoviCategoria(nomeCategoria);
            System.out.printf((CATEGORIA_S_RIMOSSA) + "%n", nomeCategoria);
        } catch (CategoriaNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void view() {
        System.out.println(this.toString());
    }

    @Override
    public void salva() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Per le modifiche relative ai campi della categoria
     */
    public void modificaCampiCategoria() {
        view();
        //selezione della categoria da modificare all'interno della gerarchia
        String nomeCategoria = InputDati.leggiStringaNonVuota("Nome categoria da modificare: ");
        Categoria categoria = getCategoriaByName(nomeCategoria);
        if (categoria != null) {
            categoria.modificaCampi();
        } else {
            System.out.println(ERRORE_MODIFICA_CATEGORIA_INESISTENTE);
        }
    }

    public Categoria getCategoriaByName(String nomeCategoria) {
        try {
            return this.categoriaRadice.cercaCategoria(nomeCategoria);
        } catch (CategoriaNotFoundException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<CategoriaFoglia> getCategorieFoglia() {
        return categorieFoglia;
    }

    public List<Component> getCategorie() {
        return categorie;
    }

    public void setCategorie(List<Component> categorie) {
        this.categorie = categorie;
    }

}