package br.com.locadora.model;

public class Contato {
    private String telefone;
    private String emailAlternativo;

    public Contato(String telefone, String emailAlternativo) {
        this.telefone = telefone;
        this.emailAlternativo = emailAlternativo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmailAlternativo() {
        return emailAlternativo;
    }

    public void setEmailAlternativo(String emailAlternativo) {
        this.emailAlternativo = emailAlternativo;
    }
}
