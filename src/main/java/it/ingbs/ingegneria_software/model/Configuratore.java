package it.ingbs.ingegneria_software.model;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.ingbs.ingegneria_software.utilita_generale.InputDati;

public class Configuratore extends Utente  {
    
    public Configuratore(String nomeUtente, String passwordUtente) {
        super(nomeUtente, passwordUtente);
    }

    public void salvaCambiamenti(){}
    public void visualizzaGerarchia(){
   //     gestoreCategorie.visualizzaAlberoCategorie();
    }
    public void visualizzaFattoriConversione(){}

  //  public List<Categoria> creaGerarchia(String nomeGerarchia) {
   //     return gestoreCategorie.creaGerarchiaRicorsiva(nomeGerarchia); 
    

    public Categoria creaCategoria(String nomeCategoria) {
        return null;
    }

}
