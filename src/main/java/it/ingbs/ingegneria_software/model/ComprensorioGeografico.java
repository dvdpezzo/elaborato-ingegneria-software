package it.ingbs.ingegneria_software.model;

import java.util.*;
public class ComprensorioGeografico {

    private final int codice;
    private List<String> listaComuni = new ArrayList<>(); //non posso mettere List()
    
    
    public ComprensorioGeografico(List<String> listaComuni){
        this.codice=generaCodice();
        this.listaComuni=listaComuni;
    }

    public ComprensorioGeografico(int codice, String listaComuni) {
        this.codice = codice;
        this.listaComuni = parseComuni(listaComuni);
    }

    private List<String> parseComuni(String listaComuni) {
        String[] comuniArray = listaComuni.substring(1, listaComuni.length() - 1).split(", ");
        return new ArrayList<>(Arrays.asList(comuniArray));
    }


    public int getCodice() {
        return codice;
    }
  
    //metodo per la creazione del codice da 0 a 9000
    private int generaCodice(){
        Random random = new Random();
        return 0000 + random.nextInt(9000);

    }

    public void aggiungiComune(String nomeComune) {
        if (!listaComuni.contains(nomeComune)) {
            listaComuni.add(nomeComune);
            System.out.println("Comune " + nomeComune + " aggiunto con successo al comprensorio " + getCodice());
        } else {
            System.out.println("Comune già presente nel comprensorio geografico.");
        }
    }
    
    public List<String> getListaComuni() {
        return listaComuni;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Codice = ").append(getCodice()).append(" Comuni = [");
        for (String comune : getListaComuni()) {
            sb.append(comune).append(", ");
        }
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2); // remove the trailing comma and space
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
