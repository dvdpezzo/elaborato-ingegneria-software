package it.ingbs.ingegneria_software.gestione_accesso;

import it.ingbs.ingegneria_software.model.ComprensorioGeografico;
import it.ingbs.ingegneria_software.model.Configuratore;
import it.ingbs.ingegneria_software.model.Fruitore;
import it.ingbs.ingegneria_software.model.GestoreComprensorio;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class MenuAccesso {

    private static final String INSERISCI_PASSWORD = "Inserisci password: ";
    private static final String INSERIRE_NOME_UTENTE = "Inserire nome utente: ";
    private MenuAccesso() {
        throw new IllegalStateException("Utility class");
      }
    /**
     * chiede all'utente di inserire le credenziali per accedere al sistema
     * @return Configuratore valido se le credenziali sono corrette
     */
    public static Configuratore loginConfiguratore (){
        GestoreAccesso gestoreAccesso = new GestoreAccesso();
        Configuratore result;
        do {
            String nomeUtente= InputDati.leggiStringaNonVuota(INSERIRE_NOME_UTENTE);
            String passUtente= InputDati.leggiStringaNonVuota(INSERISCI_PASSWORD);
            result = gestoreAccesso.accessoConfiguratore(nomeUtente, passUtente);
        } while (result == null);
        return result;
    }


     /**
     * chiede all'utente di inserire le credenziali per accedere al sistema
     * @return Fruitore valido se le credenziali sono corrette
     */

    public static Fruitore loginFruitore(){
        GestoreAccesso gestoreAccesso = new GestoreAccesso();
        Fruitore result=null;
        do{ String nomeUtente= InputDati.leggiStringaNonVuota(INSERIRE_NOME_UTENTE);
        String passUtente= InputDati.leggiStringaNonVuota(INSERISCI_PASSWORD);
        result = gestoreAccesso.accessoFruitore(nomeUtente, passUtente);

        }while(result==null);
        return result;
    }




    public static void menuFruitore(){
        GestoreComprensorio gc = new GestoreComprensorio();
        final String [] VOCI ={"ACCESSO UTENTE","REGISTRAZIONE UTENTE"};
        MenuUtil menu = new MenuUtil("Seleziona la modalità di accesso:", VOCI);
        boolean valore = true;
        do{
            int opzione = menu.scegli();
            switch(opzione){
                case 1:
                loginFruitore();
                valore=false;
                break;

                case 2:
                //primo acccesso utente, con scelta del comprensorio e inserimento della mail
                 gc.visualizzaComprensori();
                 int code = InputDati.leggiIntero("Inserisci il codice del tuo comprensorio:");
                 ComprensorioGeografico comprensorio =gc.getComprensorio(code);
                 if(comprensorio==null) 
                 {
                    System.out.println("IL CODICE DEL COMPRENSORIO INSERITO E ERRATO!");
                    break;
                 }

                 String email = InputDati.leggiStringaNonVuota("Inserisci la tua email:");
                 String nomeUtente = InputDati.leggiStringaNonVuota("Inserisci il tuo nome utente:");
                 //controllo sul nome utente ancora da inserire
                 String pass = InputDati.leggiStringa("Inserisci la tua password:");
                 new Fruitore(nomeUtente,pass,comprensorio,email);
                 valore=false;
                 break;
            }
        }while(valore);


    }

}
