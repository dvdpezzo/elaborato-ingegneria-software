import java.util.List;
import java.util.Map;

import it.ingbs.ingegneria_software.model.gerarchie.Categoria;
import it.ingbs.ingegneria_software.model.richieste.RichiestaScambio;
import it.ingbs.ingegneria_software.model.utenti.Fruitore;

public class VisualizzatoreRichieste {
    
    public void visualizzaRichiesteChiuse(Map<Fruitore, List<RichiestaScambio>> richiesteChiuse) {
        for(Map.Entry<Fruitore, List<RichiestaScambio>> entry : richiesteChiuse.entrySet()){
            System.out.println("Codice richiesta: " + entry.getKey());
            for(RichiestaScambio richiesta : entry.getValue()){

                System.out.println(richiesta.getFr().getNomeUtente()+" "+richiesta.getFr().getEmail());
                System.out.println(richiesta.toString()+"\n");
            }
        }
    }
    
    public void visualizzaRichiesteCategoria(Categoria categoria, Map<Fruitore, List<RichiestaScambio>> mappaRichieste) {
        if(categoria == null){
            System.out.println("Categoria non trovata.");
            return;
        }
        for(Map.Entry<Fruitore, List<RichiestaScambio>> entry : mappaRichieste.entrySet()){
            for(RichiestaScambio richiesta : entry.getValue()){
                if(richiesta.getCatRichiesta().equals(categoria)){
                    System.out.println(richiesta.getFr().getNomeUtente());
                    System.out.println(richiesta.toString());
                }
                else if(richiesta.getCatOfferta().equals(categoria)){
                    System.out.println(richiesta.getFr().getNomeUtente());
                    System.out.println(richiesta.toString());
                }
            }
        }
    }

    public void visualizzaRichiesteFruitore(Fruitore fruitore, Map<Fruitore, List<RichiestaScambio>> mappaRichieste) {
        for(Map.Entry<Fruitore, List<RichiestaScambio>> entry : mappaRichieste.entrySet()){
            if(entry.getKey().equals(fruitore)){
                for(RichiestaScambio richiesta : entry.getValue()){
                    System.out.println(richiesta.getFr().getNomeUtente());
                    System.out.println(richiesta.toString());
                }
            }
        }
    }
}
