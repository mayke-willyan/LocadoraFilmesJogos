package br.com.locadora.model;

import java.util.ArrayList;

public abstract class Titulo {
    protected  int id;
    protected String titulo;
    protected  int ano;
    protected  double precoBase;
    protected StatusTitulo status;
    protected  int contadorLocacoes;

    private ArrayList<Cliente> filaEspera = new ArrayList<>();

    public abstract double calcularMulta(int diasAtraso);

    public Titulo(int id, String titulo, int ano, double precoBase) {
        this.id = id;
        this.titulo = titulo;
        this.ano = ano;
        this.precoBase = precoBase;
        this.status = StatusTitulo.DISPONIVEL;
        this.contadorLocacoes = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(double precoBase) {
        this.precoBase = precoBase;
    }

    public StatusTitulo getStatus() {
        return status;
    }

    public void setStatus(StatusTitulo status) {
        this.status = status;
    }

    public int getContadorLocacoes() {
        return contadorLocacoes;
    }

    public void setContadorLocacoes(int contadorLocacoes) {
        this.contadorLocacoes = contadorLocacoes;
    }

    public ArrayList<Cliente> getFilaEspera() {
        return filaEspera;
    }

    public void setFilaEspera(ArrayList<Cliente> filaEspera) {
        this.filaEspera = filaEspera;
    }

    public final void imprimirComprovanteRenovacao(){
        String comprovante = "====================================\n" +
                "      COMPROVANTE DE RENOVAÇÃO      \n" +
                "====================================\n" +
                " ID do Título: " + this.id + "\n" +
                " Nome: " + this.titulo + "\n" +
                " Ano de Lançamento: " + this.ano + "\n" +
                " Status Atual: " + this.status + "\n" +
                " Preço Base: R$ " + String.format("%.2f", this.precoBase) + "\n" +
                "====================================\n" +
                "   Renovação efetuada com sucesso!  \n" +
                "====================================";

        // Exibe no console para registro do sistema
        System.out.println(comprovante);
    }
}
