package modelos;

import constantes.Tipos;

public class Simbolo extends Lexema{
    String classificacao;

    public Simbolo(){}

    public Simbolo(String classificacao, String nome, int linha){
        super(nome,linha);
        this.classificacao = classificacao;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    @Override
    public String toString() {
        return "Simbolo{" +
                "nome='" + nome + '\'' +
                ", linha='" + linha + '\'' +
                ", classificacao='" + classificacao + '\'' +
                '}';
    }
}
