package br.com.locadora.model;

public class Filme extends Titulo{

    public Filme(int id, String titulo, int ano, double precoBase){
        super(id,titulo,ano,precoBase);
    }

    @Override
    public double calcularMulta(int diasAtraso){
        return diasAtraso * 2.0;
    }


}
