package br.com.locadora.model;

public class Serie extends Titulo{
    public Serie(int id, String titulo, int ano, double precoBase){
        super(id,titulo,ano,precoBase);
    }
    @Override
    public double calcularMulta(int diasAtraso){
        return diasAtraso * 3.0;
    }


}
