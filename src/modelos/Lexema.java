package modelos;

import constantes.Tipos;

public class Lexema {
    String nome;
    int linha;

    public Lexema() {}

    public Lexema(String nome, int linha) {
        this.nome = nome;
        this.linha = linha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
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
