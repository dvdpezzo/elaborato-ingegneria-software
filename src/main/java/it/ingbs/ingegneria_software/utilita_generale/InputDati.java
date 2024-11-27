package it.ingbs.ingegneria_software.utilita_generale;

import java.util.InputMismatchException;
import java.util.Scanner;


import it.ingbs.ingegneria_software.model.CategoriaFoglia;


public class InputDati 
{

	      static CategoriaFoglia catFoglia = new CategoriaFoglia(null, null);
		  private static final Scanner lettore = creaScanner();
		  
		  private static final String ERRORE_FORMATO = "Attenzione: il dato inserito non e' nel formato corretto";
		  private static final String ERRORE_MINIMO= "Attenzione: e' richiesto un valore maggiore o uguale a ";
		  private static final String ERRORE_STRINGA_VUOTA= "Attenzione: non hai inserito alcun carattere";
		  private static final String ERRORE_MASSIMO= "Attenzione: e' richiesto un valore minore o uguale a ";
		  private static final String MESSAGGIO_AMMISSIBILI= "Attenzione: i caratteri ammissibili sono: ";
		  
		  private static final char RISPOSTA_SI='S';
		  private static final char RISPOSTA_NO='N';
	  
		  private InputDati() {
			throw new IllegalStateException("Utility class");
		  }
	   
		  private static Scanner creaScanner ()
		  {
		   Scanner creato = new Scanner(System.in);
		   creato.useDelimiter(System.getProperty("line.separator"));
		   return creato;
		  }
		  
		  public static String leggiStringa (String messaggio)
		  {
			  System.out.println(messaggio);
			  return lettore.next();
		  }
		  
		  public static String leggiStringaNonVuota(String messaggio)
		  {
		   boolean finito=false;
		   String lettura = null;
		   do
		   {
			 lettura = leggiStringa(messaggio);
			 lettura = lettura.trim();
			 if (lettura.length() > 0)
			  finito=true;
			 else
			 System.out.println(ERRORE_STRINGA_VUOTA);
		   } while (!finito);
		   
		   return lettura;
		  }
		  
		  public static char leggiChar (String messaggio)
		  {
		   boolean finito = false;
		   char valoreLetto = '\0';
		   do
			{
			 System.out.println(messaggio);
			 String lettura = lettore.next();
			 if (lettura.length() > 0)
			  {
			   valoreLetto = lettura.charAt(0);
			   finito = true;
			  }
			 else
			  {
				System.out.println(ERRORE_STRINGA_VUOTA);
			  }
			} while (!finito);
		   return valoreLetto;
		  }
		  
		  public static char leggiUpperChar (String messaggio, String ammissibili)
		  {
		   boolean finito = false;
		   char valoreLetto;
		   do
		   {
			valoreLetto = leggiChar(messaggio);
			valoreLetto = Character.toUpperCase(valoreLetto);
			if (ammissibili.indexOf(valoreLetto) != -1){
			 finito  = true;
			} else
			System.out.println(MESSAGGIO_AMMISSIBILI + ammissibili);
		   } while (!finito);
		   return valoreLetto;
		  }
		  
		  
	  
	  
		  public static int leggiIntero (String messaggio)
		  {
		   boolean finito = false;
		   int valoreLetto = 0;
		   do
			{
				System.out.println(messaggio);
			 try
			  {
			   valoreLetto = lettore.nextInt();
			   finito = true;
			  }
			 catch (InputMismatchException e)
			  {
				System.out.println(ERRORE_FORMATO);
			   lettore.nextLine();
			  }
			} while (!finito);
		   return valoreLetto;
		  }
	  
		  public static int leggiInteroPositivo(String messaggio)
		  {
			  return leggiInteroConMinimo(messaggio,1);
		  }
		  
		  public static int leggiInteroNonNegativo(String messaggio)
		  {
			  return leggiInteroConMinimo(messaggio,0);
		  }
		  
		  
		  public static int leggiInteroConMinimo(String messaggio, int minimo)
		  {
		   boolean finito = false;
		   int valoreLetto = 0;
		   do
			{
			 valoreLetto = leggiIntero(messaggio);
			 if (valoreLetto >= minimo)
			  finito = true;
			 else
			 System.out.println(ERRORE_MINIMO + minimo);
			} while (!finito);
			 
		   return valoreLetto;
		  }
	  
		  public static int leggiIntero(String messaggio, int minimo, int massimo)
		  {
		   boolean finito = false;
		   int valoreLetto = 0;
		   do
			{
			 valoreLetto = leggiIntero(messaggio);
			 if (valoreLetto >= minimo && valoreLetto<= massimo)
			  finito = true;
			 else
			  if (valoreLetto < minimo){
				System.out.println(ERRORE_MINIMO + minimo);
			  }else
			  System.out.println(ERRORE_MASSIMO + massimo); 
			} while (!finito);
			 
		   return valoreLetto;
		  }
	  
		  
		  public static double leggiDouble (String messaggio)
		  {
		   boolean finito = false;
		   double valoreLetto = 0;
		   do
			{
				System.out.println(messaggio);
			 try
			  {
			   valoreLetto = lettore.nextDouble();
			   finito = true;
			  }
			 catch (InputMismatchException e)
			  {
				System.out.println(ERRORE_FORMATO);
			  }
			} while (!finito);
		   return valoreLetto;
		  }
		 
		  public static double leggiDoubleConMinimo (String messaggio, double minimo)
		  {
		   boolean finito = false;
		   double valoreLetto = 0;
		   do
			{
			 valoreLetto = leggiDouble(messaggio);
			 if (valoreLetto >= minimo)
			  finito = true;
			 else
			 System.out.println(ERRORE_MINIMO + minimo);
			} while (!finito);
			 
		   return valoreLetto;
		  }
	  
		  
		  public static boolean yesOrNo(String messaggio)
		  {
			  String mioMessaggio = messaggio + "("+RISPOSTA_SI+"/"+RISPOSTA_NO+")";
			  char valoreLetto = leggiUpperChar(mioMessaggio,String.valueOf(RISPOSTA_SI)+String.valueOf(RISPOSTA_NO));
			  
					return valoreLetto == RISPOSTA_SI;
		  }
	  
	  
		  
		  public static double leggiDoubleLimitato (String messaggio, double minimo, double massimo)
		  {
		   boolean finito = false;
		   double valoreLetto = 0;
		   do
			{
			 valoreLetto = leggiDouble(messaggio);
			 if (valoreLetto >= minimo && valoreLetto <= massimo)
			  finito = true;
			 else
			 System.out.println(ERRORE_MINIMO + minimo);
			  lettore.nextLine();
			} while (!finito);
		    
		   return valoreLetto;
		  }
	  


		  
		  /* IDEA DEL CAZZO:
		   * Cerco una categoriaFoglia e la ritorno 
		   * Metodo di utilità generale messo in InputDati come test
		   *
		  public static CategoriaFoglia trovaCat(){
			String nome = leggiStringaNonVuota("Inserisci il nome della categoria");
			return catFoglia.cercaCatFoglia(nome);
         }
			
		 */


	 

}
