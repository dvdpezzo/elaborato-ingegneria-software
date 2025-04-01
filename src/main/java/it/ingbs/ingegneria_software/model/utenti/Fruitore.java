package it.ingbs.ingegneria_software.model.utenti;


public class Fruitore extends Utente{

    private final int codiceComprensorio ;
    private final String email;



    /*
    * Creo un nuovo oggetto Fruitore
    */
    public Fruitore(String nomeUtente, String passUtente, int codiceComprensorio ,String email){
        super(nomeUtente, passUtente);
        this.codiceComprensorio= codiceComprensorio;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public int getComprensorio() {
        return codiceComprensorio;
    }
    
    /**
     * visualizza a video le informazioni di un utente
     */
    public void infoFruitore(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getNomeUtente());
        sb.append((this.getPassword()));
        sb.append(String.valueOf(this.getComprensorio()));
        sb.append((this.getEmail()));
        sb.toString();
    }

}