package it.ingbs.ingegneria_software.model;

/**
 * Rappresenta un valore nel dominio di un campo caratteristico.
 * viene utilizzata per ricordare i possibili valori che un campo può assumere,
 * insieme a eventuali informazioni relative a tali valori
 */
public class Valore {

    private String nome;
    private String descrizione;

    public Valore(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}