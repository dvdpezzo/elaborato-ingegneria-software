package it.ingbs.ingegneria_software.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class GestoreFruitori {

    private ArrayList<Fruitore> listaFruitori = new ArrayList();
    private HashMap<String,String> mappaPass = new HashMap();
    File credenzialiFruitori = new File("src\\File di accesso\\credenzialiFruitori");

    /*
     * aggiunge un oggetto fruitore all'arrayList di fruitori (listaFruitori)
     */
    public void aggiungiFrutiore(Fruitore fr){
        listaFruitori.add(fr);
    }

    /*
     * aggiunge i dati relativi ad un fruitore alla Mappa dei dati (MappaFruitori)
     */
    public void aggiungiDati(String nomeUtente, String password){
        mappaPass.put(nomeUtente, password);
    }

    /*
     * visualizza elenco fruitori con informazioni annesse 
     */
    public void visualizzaFruitori(){
        for(Fruitore f : listaFruitori){
            System.out.println("Utente:"); 
            f.infoFruitore();
        }
        }
}


    



     
     /**
      * 1)
      * a)salvare i dati dei vari fruitori (nome utente, pass, comprensorio tutto sullo stesso file) guarda l'esempio delle gerarchie
        b)CONTROLLO UNIVICO DEL NOME UTENTE
       * crea una classe padre gestoreUtente che controlla se il nome utente è ripetuto oppure no
         passo tutti  i fruitori e configuratori con mappa utente stringa (stringa è nomeutente)
         Avrà un metodo che riempie la mappa prendendo eleco frutiori e configuratori con un metodo che contrlla il nume utente (boolean)

      */
