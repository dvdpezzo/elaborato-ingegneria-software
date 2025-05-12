package it.ingbs.ingegneria_software.model.comprensori;

public class Comune {

    private String nome;
    private Integer numero=0;


    public Comune(String nome, Integer numero) {
        this.nome=nome;
        this.numero=numero;
    }


    public Integer getNumero(){
        return numero;
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Comune comuni = (Comune) obj;
        return this.getNome().equalsIgnoreCase(comuni.getNome());
    }

    

}


    

    
    


    








   









    
