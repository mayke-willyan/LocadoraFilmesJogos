package br.com.locadora.model;

public class Jogo extends Titulo{
    public Jogo(int id, String titulo, int ano, double precoBase){
        super(id,titulo,ano,precoBase);
    }

    @Override
    public double calcularMulta(int diasAtraso){
        return diasAtraso * 5.0;
    }
}
