package it.ingbs.ingegneria_software.model;

import java.util.*;
public class ComprensorioGeografico {

    private final int codice;
    public List<String> listaComuni = new ArrayList<>(); //non posso mettere List()
    
    
    public ComprensorioGeografico(List<String> listaComuni){
        this.codice=generaCodice();
        this.listaComuni=listaComuni;
    }


    public int getCodice() {
        return codice;
    }
  
    //metodo per la creazione del codice da 0 a 9000
    private int generaCodice(){
        Random random = new Random();
        return 0000 + random.nextInt(9000);

    }

    public void aggiungiComune(String nomeComune){
        listaComuni.add(nomeComune);
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




}
