package it.ingbs.ingegneria_software.model.comprensori;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import it.ingbs.ingegneria_software.gestione_file.GestoreDati;
import it.ingbs.ingegneria_software.utilita_generale.*;

public class GestoreComprensorio implements Visualizzabile, Salvabile, Rimuovibile, Aggiungibile {
    private static final String MSG_CODICE_RIMOZIONE = "Inserisci il codice del comprensorio da rimuovere:";
    private static final String MSG_CODICE_AGGIUNTA = "Inserisci il codice del comprensorio al quale si vuole aggiungere il comune:";
    private static final String MSG_NOME_COMUNE = "Inserisci il nome del comune:";
    private static final String MSG_ALTRO_COMUNE = "Vuoi aggiungere un altro comune?";
    private static final int MIN_NUMERO_COMUNI = 3;

    private final HashMap<Integer, ComprensorioGeografico> mappaComprensori;
    private final GestoreDati gestoreDati;

    public GestoreComprensorio(GestoreDati gestoreDati) {
        this.gestoreDati = gestoreDati;
        this.mappaComprensori = gestoreDati.getComprensori();
    }

    public void impostaComuni() {
        mappaComprensori.values().forEach(c -> c.setGestoreComuni(new GestoreComuni(gestoreDati)));
    }

    public ComprensorioGeografico getComprensorio(int codice) {
        return mappaComprensori.get(codice);
    }

    public void aggiungiComuneAlComprensorio() {
        view();
        int codice = InputDati.leggiIntero(MSG_CODICE_AGGIUNTA);
        ComprensorioGeografico comprensorio;
        
        while ((comprensorio = getComprensorio(codice)) != null) {
            try {
                String nomeComune = InputDati.leggiStringa(MSG_NOME_COMUNE);
                if (comprensorio.getGestoreComuni().controlloComuni(nomeComune)) {
                    comprensorio.aggiungiComuneNuovo(nomeComune);
                    salva();
                } else {
                    System.out.println("Comune non trovato!");
                }
            } catch (IOException e) {
                System.out.println("Errore: " + e.getMessage());
            }
            if (!InputDati.yesOrNo(MSG_ALTRO_COMUNE)) break;
        }
        if (comprensorio == null) System.out.println("Codice comprensorio non valido!");
    }

    public ComprensorioGeografico creaComprensorioGeografico() {
        List<String> comuni = new LinkedList<>();
        GestoreComuni gestoreComuni = new GestoreComuni(gestoreDati);
        gestoreComuni.view();
        gestoreComuni.inserimentoComuni(comuni, MIN_NUMERO_COMUNI);
        Collections.sort(comuni);
        return new ComprensorioGeografico(comuni);
    }

    @Override
    public void view() {
        mappaComprensori.values().forEach(System.out::println);
    }

    @Override
    public void rimuovi() {
        view();
        int codice = InputDati.leggiIntero(MSG_CODICE_RIMOZIONE);
        if (mappaComprensori.remove(codice) != null) {
            salva();
            System.out.println("Comprensorio rimosso con successo.");
        } else {
            System.out.println("Codice comprensorio non trovato.");
        }
    }

    @Override
    public void aggiungi() {
        ComprensorioGeografico nuovo = creaComprensorioGeografico();
        if (mappaComprensori.values().stream().noneMatch(c -> c.equals(nuovo))) {
            mappaComprensori.put(nuovo.getCodice(), nuovo);
            salva();
            System.out.println("Comprensorio aggiunto.");
        } else {
            System.out.println("Comprensorio già esistente.");
        }
    }

    @Override
    public void salva() {
        gestoreDati.setComprensori(mappaComprensori);
    }
}