package it.ingbs.ingegneria_software.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;




public class GestoreFattoriConversione {
    
    GestoreCategorie gestoreCat = new GestoreCategorie();
    private HashMap<String,FattoriConversione> mappaFattori;
    File nomeFile = new File("src\\Data File\\elencoFattoriConversione.txt");
    

    /*
     * assegno un fattore di conversione tra 2 categorie foglia 
     */
    private void assegnaFattoreConversione(CategoriaFoglia categoria1, CategoriaFoglia categoria2, double valore){
        //creo la chiave che è data dal nome delle 2 categorie foglia
        String chiave = categoria1.getNome()+" -> "+categoria2.getNome();
        
        //creo un fattore di conversione e lo aggiungo alla mappa dei fattori
        FattoriConversione fattore = new FattoriConversione(valore, categoria1, categoria2);
        mappaFattori.put(chiave,fattore);
        
        //creo in automatico il fattore opposto.
        fattoreOpposto(categoria1, categoria2, valore);

    }


    /*
     * Crea un fattore di conversione opposto rispeto a quello appena creato. 
     */
    private void fattoreOpposto(CategoriaFoglia categoria1, CategoriaFoglia categoria2, double valore)
    {
        String chiaveOpposta = categoria2.getNome()+" -> "+categoria1.getNome();

        FattoriConversione fattoreOpposto = new FattoriConversione(1/valore, categoria2, categoria1);
        mappaFattori.put(chiaveOpposta,fattoreOpposto);
    }




    /*
     * Controllo se esistono i valori di conversione da 1 a 2 e da 2 a 3, se esistono creo il valore da 1 a 3 e lo aggiungo alla mappa.
     */
    public void fattoreDerivato(CategoriaFoglia cat1, CategoriaFoglia cat2, CategoriaFoglia cat3){
        String controllo12 = cat1.getNome()+" -> "+cat2.getNome();
        String controllo23 = cat2.getNome()+" -> "+cat3.getNome();
        if(mappaFattori.containsKey(controllo12) && mappaFattori.containsKey(controllo23)){
            double val1 = mappaFattori.get(controllo12).getValore();
            double val2 = mappaFattori.get(controllo23).getValore();
            double valDerivato = val1*val2;

            //aggiungo il valore derivato alla mappa (da cat1 a cat3)

            assegnaFattoreConversione(cat1, cat3, valDerivato);
        }
        
    }


    /*
     * metodo che permette di salvare i fattori di conversione su file txt
     * Salva nome delle categorie, valore di conversine ed eventuali descrizioni. (possibili modifiche da apportare se i dati vengono 
     * salvati in modo sbagliato) 
     */
     public void salvaFattori(File nomeFile2) throws IOException {
             try (FileWriter writer = new FileWriter(nomeFile2)) {
            // Itera sulla mappa dei fattori di conversione e scrive ogni coppia su una riga
            for (FattoriConversione fattore : mappaFattori.values()) {
                // Scrive la riga con le categorie e il valore del fattore
                writer.append(fattore.getCategoria1().getNome() + " -> " +
                              fattore.getCategoria2().getNome() + ": " +
                              fattore.getValore() + "["+
                              fattore.getCategoria1().getDescrizione()+"/"+
                              fattore.getCategoria2().getDescrizione()+"]"+
                               "\n");
            }
        }
    }

    
    /*
     * visualizza le categoria foglia e in seguito crea un fattore di conversione.
     */
    public void nuovoFattore(){
        gestoreCat.visualizzaCatFoglia();
        CategoriaFoglia cat1 = getCat();
        CategoriaFoglia cat2 = getCat();
        double valore = InputDati.leggiDoubleLimitato("Inserisci il fattore di conversione:", 0.5, 2);
        assegnaFattoreConversione(cat1,cat2,valore);
    }


    private CategoriaFoglia getCat () {
        String nome = InputDati.leggiStringaNonVuota("Inserisci il nome della categoria");		
        return gestoreCat.cercaCatFoglia(nome);
    }


    /*
     * menu che permette di eseguire operazioni sui fattori di conversione
     */
    public void modificaFattori() throws IOException{
        String [] voci = {"Aggiungi fattore di conversione","Salva fattori di conversione",
        "Visualizza fattori di conversione"};
        MenuUtil menuFattori = new MenuUtil("AZIONI SUI FATTORI DI CONVERSIONE",voci);
        int scelta=0;
        do{
           scelta=menuFattori.scegli();
           switch(scelta){
            case 1:
              nuovoFattore();
            break;

            case 2:
             salvaFattori(nomeFile);
             break;

            case 3: 
            //visualizzaFattori();
            break;
           }
        }while(scelta!=0);
    }
}



/*
 * 
 * OSS: da aggiungere il metodo che permette la lettura da file dei valori di conversione e li aggiunge alla mappa. 
 * L'ideale sarebbe trattare i fattori di conversione separatamente dalle categorie, cioè si creano 2 mappe di categorie:
 * 1)Lavora sulle gerarchie
 * 2)Lavora sui fattori di coversione
 * ovviamente le mappe dovranno essere uguale in quanto le categorie foglia sono uguali sia per le gerarchie sia per
 * i fattori di conversione. 
 */
