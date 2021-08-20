package Sintacticador;

public class Erro {
    String tipo;
    String nome;
    int linha;

    public Erro(){}

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        return "Erro{" +
                "tipo='" + tipo + '\'' +
                ", nome='" + nome + '\'' +
                ", linha=" + linha +
                '}';
    }
}
