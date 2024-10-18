package it.ingbs.ingegneria_software.model;
import java.util.*;


public class CampoCaratteristico {

    private String nomeCampo; //es: TIPO
    private List<Valore> dominioValori; //es: {Teoria, Strumento}
    private Map<String, String> descrizioniValori; 

    public CampoCaratteristico(String tipo, List<Valore> valoriCampo) {
        this.nomeCampo = tipo;
        this.dominioValori = valoriCampo;
        this.descrizioniValori = new HashMap<>();
    }

    /**
     * Controlla che un dato valore sia valido per il dominio del campo caratteristico
     * @param value
     * @return true se valido
     */
    public boolean controlloValoreValido (String value) {
        for (Valore valore : dominioValori) {
            if (valore.getNome().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Associa una descrizione a un valore del campo caratteristico
     * @param value valore del campo
     * @param descrizione descrizione del valore
     */
    public void aggiungiDescrizione(String value, String descrizione) {
        descrizioniValori.put(value, descrizione);
    }

    /**
     * Restituisce la descrizione associata a un valore del campo caratteristico
     * @param value valore del campo
     * @return descrizione del valore
     */
    public String getDescrizione(String value) {
        return descrizioniValori.get(value);
    }

    public String getNomeCampo() {
        return nomeCampo;
    }

    public void setNomeCampo(String nomeCampo) {
        this.nomeCampo = nomeCampo;
    }

    public List<Valore> getDominioValori() {
        return dominioValori;
    }

    public void setDominioValori(List<Valore> dominioValori) {
        this.dominioValori = dominioValori;
    }
    
}
