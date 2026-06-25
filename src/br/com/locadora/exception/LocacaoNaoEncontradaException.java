package br.com.locadora.exception;

public class LocacaoNaoEncontradaException extends RuntimeException {
    public LocacaoNaoEncontradaException(String message) {
        super(message);
    }
}
