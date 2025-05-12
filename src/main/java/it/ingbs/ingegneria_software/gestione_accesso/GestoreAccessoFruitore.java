package it.ingbs.ingegneria_software.gestione_accesso;

import it.ingbs.ingegneria_software.controller.ServiceProvider;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;
import it.ingbs.ingegneria_software.model.utenti.GestoreFruitori;
import it.ingbs.ingegneria_software.model.utenti.GestoreUtente;
import it.ingbs.ingegneria_software.utilita_generale.InputDati;

public class GestoreAccessoFruitore implements GestoreAccessoUtente<Fruitore> {
    
    private final GestoreFruitori gestoreFruitori;
    private final ServiceProvider serviceFactory;

    public GestoreAccessoFruitore(ServiceProvider serviceFactory) {
        this.serviceFactory = serviceFactory;
        this.gestoreFruitori = serviceFactory.getGestoreFruitori();
    }

    @Override
    public Fruitore accesso(String nomeUtente, String password) {
        if (controlloEsistenzaFruitore(nomeUtente, password)) {
            System.out.println("Accesso effettuato correttamente!");
            return gestoreFruitori.trovaFruitore(nomeUtente);
        } else {
            boolean risposta = InputDati.yesOrNo("Non sei registrato! Vuoi registrarti? (S/N): ");
            if (risposta) {
                return registrazioneNuovoUtente();
            } else {
                System.out.println("Accesso negato!");
                return null;
            }
        } 
    }
    

    @Override
    public Fruitore registrazioneNuovoUtente() {
        System.out.println("Sei stato reindirizzato alla creazione del tuo Nome utente e Password personali:");
        GestoreUtente gestoreUtente = serviceFactory.getGestoreUtente();
        return gestoreFruitori.creaUtenteFruitore(serviceFactory.getGestoreComprensorio(), gestoreUtente);
    }

    private boolean controlloEsistenzaFruitore(String nomeUtente, String pass) {
        return gestoreFruitori.getMappaCredenziali().containsKey(nomeUtente) &&
                                gestoreFruitori.getMappaCredenziali().containsValue(pass);
    }
                
}
