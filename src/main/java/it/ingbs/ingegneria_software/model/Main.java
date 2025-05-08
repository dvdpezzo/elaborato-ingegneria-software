package it.ingbs.ingegneria_software.model;

import it.ingbs.ingegneria_software.controller.GestoreMenu;
import it.ingbs.ingegneria_software.controller.ServiceFactory;
import it.ingbs.ingegneria_software.gestione_file.GestoreFile;
import it.ingbs.ingegneria_software.utilita_generale.MenuUtil;

public class Main {

    public static void main(String[] args) {

        final String[] VOCI = {"ACCESSO CONFIGURATORE", "ACCESSO FRUITORE"};
        MenuUtil menu = new MenuUtil("Seleziona la tua identità", VOCI);

        ServiceFactory factory = new ServiceFactory();

        // Ottieni un servizio solo quando necessario
        GestoreFile gestoreFile = factory.getGestoreFile();
        GestoreMenu sistemaGenerale = factory.getSistemaGenerale();
}
}


