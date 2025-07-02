package it.ingbs.ingegneria_software.gestione_accesso;

public interface LoginStrategy<T> {
    
   public T accesso(String nomeUtente, String password);

   public  T registrazioneNuovoUtente();

}
