package it.ingbs.ingegneria_software.model.gerarchie;

import java.util.Collection;
import java.util.Vector;

import it.ingbs.ingegneria_software.Eccezioni.CampoNotFoundException;
import it.ingbs.ingegneria_software.Eccezioni.CategoriaNotFoundException;
import it.ingbs.ingegneria_software.Eccezioni.IllegalCampoException;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class Categoria implements Component {

    private static final String CAMPO_S_CORRETTAMENTE_AGGIUNTO = "Campo %s correttamente aggiunto!";
    private static final String SCELTA_ELIMINAZ_CAMPI = "1- Eliminare tutti i campi nativi\n2- Eliminare un campo nativo specifico\n";
    private static final String CAMPI_NATIVI_ELIMINATI = "Campi nativi eliminati";
    private static final String CAMPO_NATIVO_ELIMINATO = "Campo nativo eliminato";    
    private static final String VUOI_AGGIUNGERE_ALTRI_CAMPI = "Vuoi aggiungere altri campi? ";
    private static final String VUOI_ELIMINARE_ALTRI_CAMPI = "Vuoi eliminare altri campi? ";
    private static final String NOME_DEL_CAMPO = "Nome del campo: ";
    
    private final String descrizione;
    private final Vector<Campo> campiNativi;
    private final Vector<Component> figli;
    private String nome;
    private Categoria padre;
    
    // Costruttore
    public Categoria(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.padre = null;
        this.campiNativi = new Vector<>();
        this.figli = new Vector<>();
    }

    // Getter e Setter
    @Override
    public String getNome() {
        return nome;
    }

    public void setNome(String nuovoNome) {
        this.nome = nuovoNome;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    public Categoria getPadre() {
        return this.padre;
    }

    @Override
    public void setPadre(Categoria padre) {
        this.padre = padre;
    }

    public Vector<Component> getFigli() {
        return figli;
    }

    public Collection<Campo> getCampiNativi() {
        return campiNativi;
    }

    // Metodi di gestione dei campi
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

    public void eliminaCampiNativi() {
        this.campiNativi.clear();
    }

    public void eliminaCampoNativo(String nomeCampo) throws CampoNotFoundException {
        Campo campoDaEliminare = this.getCampoNativo(nomeCampo);
        this.campiNativi.remove(campoDaEliminare);
    }

    public Campo getCampoNativo(String nome) throws CampoNotFoundException {
        for (Campo campo : campiNativi) {
            if (campo.nome.equalsIgnoreCase(nome))
                return campo;
        }
        throw new CampoNotFoundException();
    }

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

    // Metodi di gestione della gerarchia
    public void addFiglio(Component nuovaCategoria) {
        this.figli.add(nuovaCategoria);
        nuovaCategoria.setPadre(this);
    }

    public void removeFiglio(Component categoriaDaRimuovere) {
        this.figli.remove(categoriaDaRimuovere);
        categoriaDaRimuovere.setPadre(null);
    }

    public boolean hasFiglio(Categoria categoria) {
        return !categoria.figli.isEmpty();
    }

    public Categoria cercaCategoria(String nomeCategoria) throws CategoriaNotFoundException {
        if (this.nome.equalsIgnoreCase(nomeCategoria)) {
            return this;
        }
        for (Component figlio : figli) {
            if (figlio instanceof Categoria) {
                Categoria risultato = ((Categoria) figlio).cercaCategoria(nomeCategoria);
                if (risultato != null) {
                    return risultato;
                }
            }
        }
        throw new CategoriaNotFoundException();
    }

    public void rimuoviCategoria(String nomeCategoria) throws CategoriaNotFoundException {
        for (Component figlio : figli) {
            if (figlio instanceof Categoria && figlio.getNome().equalsIgnoreCase(nomeCategoria)) {
                figli.remove(figlio);
                return;
            }
        }
        throw new CategoriaNotFoundException();
    }

    // Metodi di utilità
    @Override
    public String toString(int livello) {
        StringBuilder sb = new StringBuilder();
        sb.append(nome);
        sb.append(this.stampaCampiNativi());
        sb.append("\n");
        livello++;
        for (Component figlio : figli) {
            String indentazione = new String(new char[livello]).replace("\0", "\t");
            sb.append(indentazione);
            sb.append(figlio.toString(livello));
        }
        return sb.toString();
    }

}

class Campo {
    String nome;

    public Campo(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return String.format("#%s", nome);
    }
}