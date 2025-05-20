package it.ingbs.ingegneria_software.model.utenti;

import java.util.HashMap;

import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.model.comprensori.ComprensorioGeografico;
import it.ingbs.ingegneria_software.model.comprensori.GestoreComprensorio;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;


public class GestoreFruitori {
    private static final String MSG_PASS = "Inserisci la tua password:";
    private static final String MSG_NOME_UTENTE = "Inserisci il tuo nome utente:";
    private static final String MSG_EMAIL = "Inserisci la tua email:";
    private static final String ERRORE_COMPRENSORIO = "IL CODICE DEL COMPRENSORIO INSERITO E ERRATO!";
    private static final String MSG_COD_COMPRENSORIO = "Inserisci il codice del tuo comprensorio:";
    
    private final HashMap<String,Fruitore> mappaDatiFruitori;
    private final HashMap<String,String> mappaCredenziali;
    private final GestoreDati gestoreDati;
    
    public GestoreFruitori(GestoreDati gestoreDati) {
        this.gestoreDati = gestoreDati;
        this.mappaCredenziali = gestoreDati.getCredenzialiFruitori();
        this.mappaDatiFruitori = gestoreDati.getDatiFruitori();
    }          

    public void visualizzaFruitori() {
        mappaDatiFruitori.values().forEach(f -> {
            System.out.println("Utente:"); 
            f.infoFruitore();
        });
    }

    public Fruitore creaUtenteFruitore(GestoreComprensorio gestoreComprensorio, GestoreUtente gestoreUtente) {
        gestoreComprensorio.view();
        ComprensorioGeografico comprensorio;
        int code;
        do {
            code = InputDati.leggiIntero(MSG_COD_COMPRENSORIO);
            comprensorio = gestoreComprensorio.getComprensorio(code);
            if (comprensorio == null) System.out.println(ERRORE_COMPRENSORIO);
        } while (comprensorio == null);

        String email, nomeUtente;
        while (true) {
            email = InputDati.leggiStringaNonVuota(MSG_EMAIL);
            final String emailFinal = email; //Variabile finale per l'uso nel lambda
            if (mappaDatiFruitori.values().stream()
                    .anyMatch(f -> f.getEmail().equalsIgnoreCase(emailFinal))) {
                continue;
            }
            break;
        }

        do {
            nomeUtente = InputDati.leggiStringaNonVuota(MSG_NOME_UTENTE);
        } while (gestoreUtente.controlloUtente(nomeUtente));

        Fruitore newFruitore = new Fruitore(nomeUtente, InputDati.leggiStringa(MSG_PASS), code, email);
        mappaDatiFruitori.put(nomeUtente, newFruitore);
        mappaCredenziali.put(nomeUtente, newFruitore.getPassword());
        
        gestoreDati.setDatiFruitori(mappaDatiFruitori);
        gestoreDati.setCredenzialiFruitori(mappaCredenziali);
        return newFruitore;
    }

    public Fruitore trovaFruitore(String nomeUtente) {
        return mappaDatiFruitori.computeIfAbsent(nomeUtente, 
            k -> { throw new IllegalArgumentException("Utente non trovato: " + k); });
    }
}
