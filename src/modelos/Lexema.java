package modelos;

import constantes.Tipos;

public class Lexema {
    String nome;
    String linha;

    public Lexema() {}

    public Lexema(String nome, String linha) {
        this.nome = nome;
        this.linha = linha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLinha() {
        return linha;
    }

    public void setLinha(String linha) {
        this.linha = linha;
    }

    @Override
    public String toString() {
        return "Lexema{" +
                "nome='" + nome + '\'' +
                ", linha='" + linha + '\'' +
                '}';
    }

}
