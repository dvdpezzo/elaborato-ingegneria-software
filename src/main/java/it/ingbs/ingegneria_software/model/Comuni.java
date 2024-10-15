package it.ingbs.ingegneria_software.model;


public class Comuni {

    public String nome;
    public Integer numero=0;


    public Comuni(String nome){
        this.nome=nome;
        this.numero=numero+1;
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

    //controllo se i 2 comuni hanno lo stesso nome.
    public boolean equals(Comuni c){
        return this.getNome().equalsIgnoreCase(c.getNome());
    }
}


    

    
    


    








   









    
