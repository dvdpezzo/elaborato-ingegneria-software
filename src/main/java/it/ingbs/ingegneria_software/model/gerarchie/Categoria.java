package it.ingbs.ingegneria_software.model.gerarchie;

import java.util.*;
import it.ingbs.ingegneria_software.Eccezioni.*;


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
        this.campiNativi.removeAllElements();
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
        if(cat.figli.size()!=0){
            return true;
        }
        return false;
    }

    /**
     * aggiunge campi alla categoria da cui viene invocato
     *
     * @param nome nome del campo
     * @param obb  obbligatorietà del campo
     */
    public void addCampoNativo(String nome, boolean obb) throws IllegalCampoException {
        for (Campo c : this.getCampi()) {
            if (c.nome.equals(nome))
                throw new IllegalCampoException();
        }
        campiNativi.add(new Campo(nome, obb));
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
    boolean obblig;

    /**
     * Costruttore del campo
     *
     * @param n nome del campo
     * @param ob booleano che indica se la compilazione del campo è obbligatoria o meno
     */

    public Campo(String n, boolean ob) {
        this.nome = n;
        this.obblig = ob;
    }

    public String toString() {
        return String.format("#%s%s", obblig ? "!" : "", nome);
    }
  
   }
