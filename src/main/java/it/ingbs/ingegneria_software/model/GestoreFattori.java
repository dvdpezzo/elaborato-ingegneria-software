package it.ingbs.ingegneria_software.model;

import java.io.File;
import java.util.HashMap;
import it.ingbs.ingegneria_software.model.gerarchie.Categoria;


public class GestoreFattori {
    
    public static String ERRORE_CATEGORIA ="Le categorie che hai inserito non sono categorie foglia!";
    private HashMap<String,FattoriConversione> mappaFattori = new HashMap<String,FattoriConversione>();
    File nomeFile = new File("src\\Data File\\elencoFattoriConversione.txt");


    /**
     * Creo un fattore di conversione e lo inserisco nella mappa dei fattori 
     * e in automatico creo quello opposto
     * 
     * @param cat1 prima categoria inserita 
     * @param cat2 seconda categoria inserita 
     * @param valore indica il valore di conversione tra la prima e la seconda categoria 
     */
    private void assegnaFattoreConversione(Categoria cat1, Categoria cat2, double valore){
        if(cat1.hasFiglio(cat1)==false && cat2.hasFiglio(cat2)==false){

            String chiave = cat1.getNome()+"->"+cat2.getNome();
            FattoriConversione fattore = new FattoriConversione(valore, cat1, cat2);
            mappaFattori.put(chiave,fattore);
            
            //creo in automatico il fattore opposto 
            fattoreOpposto(cat1,cat2,valore);

        }
        else{
            System.out.println(ERRORE_CATEGORIA);
        }
    }

    /**
     * Creo il fattore di conversione opposto rispetto a quello inserito nel metodo assegnaFattoreConversione()
     * 
     * @param cat1 
     * @param cat2
     * @param valore
     */
    private void fattoreOpposto(Categoria cat1, Categoria cat2, double valore){

        String chiaveOpposta = cat2.getNome()+"->"+cat1.getNome();
        FattoriConversione fatOpposto = new FattoriConversione(1/valore, cat2, cat1);
        mappaFattori.put(chiaveOpposta,fatOpposto);
    }


    /**
     * Creo un fattore di conversione derivandolo da due fattori già esistenti
     * @param cat1 categoria dalla quale prendo il valore
     * @param cat2 categoria dalla quale prendo il valore 
     * @param cat3 categoria delle quale voglio creare un valore 
     */
    public void fattoreDerivato(Categoria cat1, Categoria cat2, Categoria cat3){
        if(cat1.hasFiglio(cat1)==false && cat2.hasFiglio(cat2)==false && cat3.hasFiglio(cat3)==false){
            String controllo12 = cat1.getNome()+" -> "+cat2.getNome();
            String controllo23 = cat2.getNome()+" -> "+cat3.getNome();
            if(mappaFattori.containsKey(controllo12) && mappaFattori.containsKey(controllo23)){
                double val1 = mappaFattori.get(controllo12).getValore();
                double val2= mappaFattori.get(controllo23).getValore();
                double valDerivato =val1*val2;

                assegnaFattoreConversione(cat1, cat3, valDerivato);
            }
        }
    }

    public void nuovoFattore(){
        /*Devo permettere al controllore di inserire il fattore di conversione
         PROBLEMA: come conosce le varie categorie? 
         SOLUZIONE: a) le ha viste dalla gerarchia e se le ricorda (non funzionale, ma semplice)
                    b) può visualizzare le varie categorie/categorie foglia (come implemento sta cosa?)
                    */

       
    }

    


}
