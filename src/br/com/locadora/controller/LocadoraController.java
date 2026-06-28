package br.com.locadora.controller;


import br.com.locadora.model.*;
import br.com.locadora.exception.*;

import java.util.ArrayList;

public class LocadoraController {
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private  ArrayList<Titulo> acervo = new ArrayList<>();

    public void cadastrarCliente(Cliente cliente){
        try {
            clientes.add(cliente);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void cadastrarTitulo(Titulo titulo) {
        try {
            acervo.add(titulo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Cliente buscarClienteCPF(String cpf){
       for(Cliente c : clientes){
           if(c.getCpf().equals(cpf)) return c;

       }

       throw new ClienteNaoEncontradoException("Cliente com cpf " + cpf + " não encontrado");
    }

    public Titulo buscarTituloId(int id){
        for(Titulo t : acervo){
            if(t.getId() == id) return t;
        }

        throw new TituloNaoEncontradoException("Titulo com o id " + id + " não encontrado no acervo");
    }


    public void realizarLocacao(int idTitulo, String cpfCliente) throws TituloIndisponivelException {
        Titulo titulo = buscarTituloId(idTitulo);
        Cliente cliente = buscarClienteCPF(cpfCliente);

        if(titulo.getStatus() == StatusTitulo.ALUGADO){
            throw new TituloIndisponivelException("O titulo desejado ja foi alugado");
        }

        if(titulo.getStatus() == StatusTitulo.ALUGADO){
            throw new TituloIndisponivelException("O titulo esta em manutenção no momento");
        }

        titulo.setStatus(StatusTitulo.ALUGADO);
        titulo.setContadorLocacoes(titulo.getContadorLocacoes() + 1);
        cliente.getHistoricoLocacoes().add(titulo);
    }

    public double registrarDevolucao(String cpfCliente, int idTitulo, int diasAtraso){
        Titulo titulo = buscarTituloId(idTitulo);
        Cliente cliente = buscarClienteCPF(cpfCliente);

        if (!cliente.getHistoricoLocacoes().contains(titulo)){
            throw new LocacaoNaoEncontradaException("Cliente não possui registo de aluguel do titulo");
        }

        titulo.setStatus(StatusTitulo.DISPONIVEL);

        double multa = 0;

        if (diasAtraso > 0){
            multa = titulo.calcularMulta(diasAtraso);
        }

        return multa;
    }

    public void renovarLocacao(int idTitulo) throws TituloNaoEncontradoException{
        Titulo titulo = buscarTituloId(idTitulo);

        Cliente clienteComTitulo = null;

        for (Cliente c : clientes){
            if(c.getHistoricoLocacoes().contains(titulo)){
                clienteComTitulo = c;
                break;
            }
        }

        if (clienteComTitulo == null || titulo.getStatus() != StatusTitulo.ALUGADO){
            throw new LocacaoNaoEncontradaException("Este titulo não esta alugado");
        }

        titulo.setStatus(StatusTitulo.ALUGADO);
        titulo.imprimirComprovanteRenovacao();

    }

    public void renovarLocacao (String cpfCliente) throws ClienteNaoEncontradoException{
        Cliente cliente = buscarClienteCPF(cpfCliente);

        if (cliente.getHistoricoLocacoes().isEmpty()){
            throw new LocacaoNaoEncontradaException("O cliente não possui nenhum titulo locado");
        }

        for (Titulo t : cliente.getHistoricoLocacoes()){
            if(t.getStatus() == StatusTitulo.ALUGADO){
                t.setStatus(StatusTitulo.ALUGADO);
                t.imprimirComprovanteRenovacao();
            }
        }

    }

    public ArrayList<Titulo> sugerirTitulo(){
        ArrayList<Titulo> listaOrdenadaAlugados = new ArrayList<>(this.acervo);
        int size = listaOrdenadaAlugados.size();

        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                Titulo t1 = listaOrdenadaAlugados.get(j);
                Titulo t2 = listaOrdenadaAlugados.get(j + 1);

                if (t2.getContadorLocacoes() > t1.getContadorLocacoes()) {
                    listaOrdenadaAlugados.set(j, t2);
                    listaOrdenadaAlugados.set(j + 1, t1);
                }
            }
        }

        ArrayList<Titulo> sugestoes = new ArrayList<>();

        for(Titulo t : listaOrdenadaAlugados){
            if(t.getStatus() == StatusTitulo.DISPONIVEL){
                sugestoes.add(t);
            }

            if(sugestoes.size() == 3){
                break;
            }
        }

        return sugestoes;
    }

    public ArrayList<Titulo> sugerirTitulo(int idTituloAnteriror) throws TituloNaoEncontradoException{
        Titulo anterior = buscarTituloId(idTituloAnteriror);

        ArrayList<Titulo> listaOrdenadaGenero = new ArrayList<>(this.acervo);
        int size = listaOrdenadaGenero.size();

        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                Titulo t1 = listaOrdenadaGenero.get(j);
                Titulo t2 = listaOrdenadaGenero.get(j + 1);

                if (t2.getContadorLocacoes() > t1.getContadorLocacoes()) {
                    listaOrdenadaGenero.set(j, t2);
                    listaOrdenadaGenero.set(j + 1, t1);
                }
            }
        }

        ArrayList<Titulo> sugestoes = new ArrayList<>();

        for(Titulo t : listaOrdenadaGenero){
            if(t.getStatus() == StatusTitulo.DISPONIVEL && t.getId() != anterior.getId()){
                if ((anterior instanceof Filme && t instanceof Filme) ||
                        (anterior instanceof Jogo && t instanceof Jogo) ||
                        (anterior instanceof Serie && t instanceof Serie)) {

                    sugestoes.add(t);
                }
            }

            if(sugestoes.size() == 3){
                break;
            }
        }

        return sugestoes;

    }

}
