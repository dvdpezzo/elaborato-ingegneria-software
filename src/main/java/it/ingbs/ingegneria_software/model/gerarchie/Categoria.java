package it.ingbs.ingegneria_software.model.gerarchie;

import java.util.Collection;
import java.util.Vector;

import it.ingbs.ingegneria_software.Eccezioni.CampoNotFoundException;
import it.ingbs.ingegneria_software.Eccezioni.IllegalCampoException;


//copiata da progetto Simone
public class Categoria {

    private final String descrizione;
    private final Vector<Campo> campiNativi;
    private final Vector<Categoria> figli;
    private String nome;
    private Categoria padre;

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

    public Categoria getPadre() {
        return this.padre;
    }

    public void setPadre(Categoria padre) {
        this.padre = padre;
    }

    public void addFiglio(Categoria nuova) {
        this.figli.add(nuova);
    }

    public void removeFiglio(Categoria daRimuovere) {
        this.figli.remove(daRimuovere);
    }

    public void eliminaCampiNativi() {
        this.campiNativi.clear();
    }

    public void eliminaCampoNativo(String nomeCampo) throws CampoNotFoundException {
        Campo daEliminare = this.getCampoNativo(nomeCampo);
        this.campiNativi.remove(daEliminare);
    }

    public Campo getCampoNativo(String nome) throws CampoNotFoundException {
        for (Campo c : campiNativi) {
            if (c.nome.equalsIgnoreCase(nome))
                return c;
        }
        throw new CampoNotFoundException();
    }

    /**
     * Stampa una rappresentazione grafica dell'albero generato.
     * Un contatore tiene traccia del livello del nodo e, in base a questo valore, il metodo imposta quante indentazioni
     * iniziali presenta il suddetto nodo
     * Ogni nodo presenta una descrizione della categoria, in particolare il nome e i suoi campi nativi tra parentesi.
     *
     * @param count contatore del livello del nodo dell'albero
     * @return stringa con una rappresentazione grafica dell'albero
     */
    public String toString(int count) {
        StringBuilder sb = new StringBuilder();
        sb.append(nome);
        sb.append(this.stampaCampiNativi());
        sb.append("\n");
        count++;
        for (Categoria figlio : figli) {
            String repeated = new String(new char[count]).replace("\0", "\t");
            
            sb.append(repeated);
            sb.append(figlio.toString(count));
        }
        return sb.toString();
    }

    /**
     * Controlla se una categoria possiede dei figli (se size>0 ha figli)
     * 
     * @param cat categoria presa in considerazine
     * @return true se possiede figli, false se non li possiede
     */
    public boolean hasFiglio(Categoria cat){
        return !cat.figli.isEmpty();
    }

    /**
     * aggiunge campi alla categoria da cui viene invocato
     *
     * @param nome nome del campo
     * @param obb  obbligatorietà del campo
     */
    public void addCampoNativo(String nome) throws IllegalCampoException {
        for (Campo c : this.getCampi()) {
            if (c.nome.equalsIgnoreCase(nome)){
                throw new IllegalCampoException();
            }else if (c.nome.equals(" ") || c.nome.equals("")){
                campiNativi.clear();
            }
        }
        campiNativi.add(new Campo(nome));
    }

    /**
     * Restituisce tutti i campi ereditati di una categoria
     * Se la categoria selezionata è una radice allora restituisce i suoi campi nativi
     *
     * @return un Vector con i campi ereditati
     */
    public Vector<Campo> getCampi() {
        Vector<Campo> campiEreditati;
        if (padre != null) {
            campiEreditati = padre.getCampi();
        } else return new Vector<>(campiNativi);

        campiEreditati.addAll(campiNativi);

        return campiEreditati;

    }

    public String stampaCampiNativi() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (Campo c : campiNativi) {
            sb.append(c.nome).append(", ");
        }
        if (sb.length() > 3)
            sb.deleteCharAt(sb.length() - 2);
        sb.append("]");
        return sb.toString();
    }

    public Collection<Campo> getCampiNativi() {
        return campiNativi;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nuovoNome) {
        this.nome = nuovoNome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Vector<Categoria> getFigli() {
        return figli;
    }
}


class Campo {
    String nome;

    /**
     * Costruttore del campo
     *
     * @param n nome del campo
     */

    public Campo(String n) {
        this.nome = n;
    }

    @Override
    public String toString() {
        return String.format("#%s", nome);
    }
  
   }
