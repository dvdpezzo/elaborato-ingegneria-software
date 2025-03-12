package it.ingbs.ingegneria_software.utilita_generale;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe di utilità per la gestione dell'input da tastiera.
 */
public class InputDati {

    private static final Scanner lettore = creaScanner();

    private static final String ERRORE_FORMATO = "Attenzione: il dato inserito non e' nel formato corretto";
    private static final String ERRORE_MINIMO = "Attenzione: e' richiesto un valore maggiore o uguale a ";
    private static final String ERRORE_STRINGA_VUOTA = "Attenzione: non hai inserito alcun carattere";
    private static final String ERRORE_MASSIMO = "Attenzione: e' richiesto un valore minore o uguale a ";
    private static final String MESSAGGIO_AMMISSIBILI = "Attenzione: i caratteri ammissibili sono: ";

    private static final char RISPOSTA_SI = 'S';
    private static final char RISPOSTA_NO = 'N';

    private InputDati() {
        throw new IllegalStateException("Utility class");
    }

    private static Scanner creaScanner() {
        Scanner creato = new Scanner(System.in);
        creato.useDelimiter(System.getProperty("line.separator"));
        return creato;
    }

    /**
     * Legge una stringa dall'input.
     *
     * @param messaggio il messaggio da visualizzare all'utente
     * @return la stringa letta
     */
    public static String leggiStringa(String messaggio) {
        System.out.println(messaggio);
        return lettore.next();
    }

    /**
     * Legge una stringa non vuota dall'input.
     *
     * @param messaggio il messaggio da visualizzare all'utente
     * @return la stringa letta
     */
    public static String leggiStringaNonVuota(String messaggio) {
        boolean finito = false;
        String lettura = null;
        do {
            lettura = leggiStringa(messaggio);
            lettura = lettura.trim();
            if (lettura.length() > 0)
                finito = true;
            else
                System.out.println(ERRORE_STRINGA_VUOTA);
        } while (!finito);

        return lettura;
    }

    /**
     * Legge un carattere dall'input.
     *
     * @param messaggio il messaggio da visualizzare all'utente
     * @return il carattere letto
     */
    public static char leggiChar(String messaggio) {
        boolean finito = false;
        char valoreLetto = '\0';
        do {
            System.out.println(messaggio);
            String lettura = lettore.next();
            if (lettura.length() > 0) {
                valoreLetto = lettura.charAt(0);
                finito = true;
            } else {
                System.out.println(ERRORE_STRINGA_VUOTA);
            }
        } while (!finito);
        return valoreLetto;
    }

    /**
     * Legge un carattere maiuscolo dall'input, limitato ai caratteri ammissibili.
     *
     * @param messaggio    il messaggio da visualizzare all'utente
     * @param ammissibili i caratteri ammissibili
     * @return il carattere letto
     */
    public static char leggiUpperChar(String messaggio, String ammissibili) {
        boolean finito = false;
        char valoreLetto;
        do {
            valoreLetto = leggiChar(messaggio);
            valoreLetto = Character.toUpperCase(valoreLetto);
            if (ammissibili.indexOf(valoreLetto) != -1) {
                finito = true;
            } else {
                System.out.println(MESSAGGIO_AMMISSIBILI + ammissibili);
            }
        } while (!finito);
        return valoreLetto;
    }

    /**
     * Legge un intero dall'input.
     *
     * @param messaggio il messaggio da visualizzare all'utente
     * @return l'intero letto
     */
    public static int leggiIntero(String messaggio) {
        boolean finito = false;
        int valoreLetto = 0;
        do {
            System.out.println(messaggio);
            try {
                valoreLetto = lettore.nextInt();
                finito = true;
            } catch (InputMismatchException e) {
                System.out.println(ERRORE_FORMATO);
                lettore.nextLine();
            }
        } while (!finito);
        return valoreLetto;
    }

    /**
     * Legge un intero positivo dall'input.
     *
     * @param messaggio il messaggio da visualizzare all'utente
     * @return l'intero positivo letto
     */
    public static int leggiInteroPositivo(String messaggio) {
        return leggiInteroConMinimo(messaggio, 1);
    }

    /**
     * Legge un intero non negativo dall'input.
     *
     * @param messaggio il messaggio da visualizzare all'utente
     * @return l'intero non negativo letto
     */
    public static int leggiInteroNonNegativo(String messaggio) {
        return leggiInteroConMinimo(messaggio, 0);
    }

    /**
     * Legge un intero dall'input con un valore minimo.
     *
     * @param messaggio il messaggio da visualizzare all'utente
     * @param minimo    il valore minimo accettabile
     * @return l'intero letto
     */
    public static int leggiInteroConMinimo(String messaggio, int minimo) {
        boolean finito = false;
        int valoreLetto = 0;
        do {
            valoreLetto = leggiIntero(messaggio);
            if (valoreLetto >= minimo)
                finito = true;
            else
                System.out.println(ERRORE_MINIMO + minimo);
        } while (!finito);

        return valoreLetto;
    }

    /**
     * Legge un intero dall'input con un valore minimo e massimo.
     *
     * @param messaggio il messaggio da visualizzare all'utente
     * @param minimo    il valore minimo accettabile
     * @param massimo   il valore massimo accettabile
     * @return l'intero letto
     */
    public static int leggiIntero(String messaggio, int minimo, int massimo) {
        boolean finito = false;
        int valoreLetto = 0;
        do {
            valoreLetto = leggiIntero(messaggio);
            if (valoreLetto >= minimo && valoreLetto <= massimo)
                finito = true;
            else if (valoreLetto < minimo) {
                System.out.println(ERRORE_MINIMO + minimo);
            } else {
                System.out.println(ERRORE_MASSIMO + massimo);
            }
        } while (!finito);

        return valoreLetto;
    }

    /**
     * Legge un double dall'input.
     *
     * @param messaggio il messaggio da visualizzare all'utente
     * @return il double letto
     */
    public static double leggiDouble(String messaggio) {
        boolean finito = false;
        double valoreLetto = 0;
        do {
            System.out.println(messaggio);
            try {
                valoreLetto = lettore.nextDouble();
                finito = true;
            } catch (InputMismatchException e) {
                System.out.println(ERRORE_FORMATO);
                lettore.nextLine();
            }
        } while (!finito);
        return valoreLetto;
    }

    /**
     * Legge un double dall'input con un valore minimo.
     *
     * @param messaggio il messaggio da visualizzare all'utente
     * @param minimo    il valore minimo accettabile
     * @return il double letto
     */
    public static double leggiDoubleConMinimo(String messaggio, double minimo) {
        boolean finito = false;
        double valoreLetto = 0;
        do {
            valoreLetto = leggiDouble(messaggio);
            if (valoreLetto >= minimo)
                finito = true;
            else
                System.out.println(ERRORE_MINIMO + minimo);
        } while (!finito);

        return valoreLetto;
    }

    /**
     * Legge un double dall'input con un valore minimo e massimo.
     *
     * @param messaggio il messaggio da visualizzare all'utente
     * @param minimo    il valore minimo accettabile
     * @param massimo   il valore massimo accettabile
     * @return il double letto
     */
    public static double leggiDoubleLimitato(String messaggio, double minimo, double massimo) {
        boolean finito = false;
        double valoreLetto = 0;
        do {
            valoreLetto = leggiDouble(messaggio);
            if (valoreLetto >= minimo && valoreLetto <= massimo)
                finito = true;
            else {
                System.out.println(ERRORE_MINIMO + minimo);
            }
        } while (!finito);

        return valoreLetto;
    }

    /**
     * Chiede all'utente una risposta sì o no.
     *
     * @param messaggio il messaggio da visualizzare all'utente
     * @return true se la risposta è sì, false altrimenti
     */
    public static boolean yesOrNo(String messaggio) {
        String mioMessaggio = messaggio + "(" + RISPOSTA_SI + "/" + RISPOSTA_NO + ")";
        char valoreLetto = leggiUpperChar(mioMessaggio, String.valueOf(RISPOSTA_SI) + String.valueOf(RISPOSTA_NO));

        return valoreLetto == RISPOSTA_SI;
    }
}
