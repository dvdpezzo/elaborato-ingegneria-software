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
     * visualizza elenco fruitori 
     */
    public void visualizzaFruitori(){
        for(Fruitore f : listaFruitori){
            System.out.println("Utente:"); 
            f.infoFruitore();
        }
        }
    }


    /*
     * a)salvare i dati dei vari fruitori (nome utente, pass)
     * b)salvare le mail dei fruitori
     * c)salvare la lista dei vari fruitori 
     * d)metodo che controlla l'unicità del nomeUtente (unico = no ripetuto tra fruitori e configuratori)
     * e)metodi di utilità generale per l'esecuzione di questi compiti. 
     */


