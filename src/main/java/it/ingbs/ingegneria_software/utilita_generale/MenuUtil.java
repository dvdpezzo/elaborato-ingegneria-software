package it.ingbs.ingegneria_software.utilita_generale;

import java.util.logging.Logger;

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
  private static final Logger LOGGER = Logger.getLogger(MenuUtil.class.getName());

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
    LOGGER.info(CORNICE);
    LOGGER.info(titolo);
    LOGGER.info(CORNICE);
    for (int i=0; i<voci.length; i++)
	 {
	  LOGGER.info( (i+1) + "\t" + voci[i]);
	 }    
    LOGGER.info(VOCE_USCITA);    
  }
		
}

