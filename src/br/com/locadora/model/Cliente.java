package br.com.locadora.model;

import java.util.ArrayList;

public class Cliente {
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private Contato contato;
    private static int totalClientesAtivos = 0;

    private ArrayList<Titulo> historicoLocacoes = new ArrayList<>();

    public Cliente(int id, String nome, String cpf, String email, Contato contato) {

        totalClientesAtivos ++;

        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.contato = contato;
    }

    public static int getTotalClientesAtivos(){
        return totalClientesAtivos;
    }
}
