package br.com.locadora.model;

import java.util.ArrayList;

public class Titulo {
    protected  int id;
    protected String titulo;
    protected  int ano;
    protected  double precoBase;
    protected StatusTitulo status;
    protected  int contadorLocacoes;

    private ArrayList<Cliente> filaEspera = new ArrayList<>();

    public Titulo(int id, String titulo, int ano, double precoBase, StatusTitulo status, int contadorLocacoes) {
        this.id = id;
        this.titulo = titulo;
        this.ano = ano;
        this.precoBase = precoBase;
        this.status = status;
        this.contadorLocacoes = contadorLocacoes;
    }


}
