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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public static void setTotalClientesAtivos(int totalClientesAtivos) {
        Cliente.totalClientesAtivos = totalClientesAtivos;
    }

    public ArrayList<Titulo> getHistoricoLocacoes() {
        return historicoLocacoes;
    }

    public void setHistoricoLocacoes(ArrayList<Titulo> historicoLocacoes) {
        this.historicoLocacoes = historicoLocacoes;
    }
}
