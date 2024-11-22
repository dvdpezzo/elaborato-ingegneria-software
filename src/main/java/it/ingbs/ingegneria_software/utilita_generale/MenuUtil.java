package it.ingbs.ingegneria_software.utilita_generale;


/*
Questa classe rappresenta un menu testuale generico a piu' voci
Si suppone che la voce per uscire sia sempre associata alla scelta 0 
e sia presentata in fondo al menu
*/
public class MenuUtil
{
  private static final String CORNICE = "--------------------------------";
  private static final String VOCE_USCITA = "\n0\tEsci\n";
  private static final String RICHIESTA_INSERIMENTO = "Digita il numero dell'opzione desiderata > ";

  private final String titolo;
  private final String [] voci;

	
  public MenuUtil (String titolo, String [] voci)
  {
	this.titolo = titolo;
	this.voci = voci;
  }

  public int scegli ()
  {
	stampaMenu();
	return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, 0, voci.length);	 
  }
		
  public void stampaMenu ()
  {
    System.out.println(CORNICE);
    System.out.println(titolo);
    System.out.println(CORNICE);
    for (int i=0; i<voci.length; i++)
	 {
	  System.out.println( (i+1) + "\t" + voci[i]);
	 }    
   System.out.println(VOCE_USCITA);    
  }
		
}

