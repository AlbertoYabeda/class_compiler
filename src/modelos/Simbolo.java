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
        String s = "";
        // TODO Auto-generated method stub
        s += s.format("%s%13s%2s%44s%2s%8s%s", "|",nome+"\t\t","|",classificacao,"|",linha+"\t","|");
        s += "\n----------------------------------------------------------------------------";
        return s;
    }
//    public String toString() {
//        return "Simbolo{" +
//                "nome='" + nome + '\'' +
//                ", linha='" + linha + '\'' +
//                ", classificacao='" + classificacao + '\'' +
//                '}';
//    }
}
