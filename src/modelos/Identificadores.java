package modelos;

import constantes.Tipos;

public class Identificadores extends Lexema{
    String memoria;
    String valor;
    String tipo;

    public Identificadores(){}

    public Identificadores(String memoria, String valor, String tipo, String nome, int linha) {
        super(nome,linha);
        this.memoria = memoria;
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getMemoria() {
        return memoria;
    }

    public void setMemoria(String memoria) {
        this.memoria = memoria;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        String s = "";
        // TODO Auto-generated method stub
        s += s.format("%s%14s%2s%13s%2s%11s%s%13s%s%7s%s", "|",nome,"|",tipo,"|",valor,"|",memoria,"|",linha+"\t","|");
        s += "\n--------------------------------------------------------------------";
        return s;
    }
//    public String toString() {
//        return "Identificadores{" +
//                "memoria='" + memoria + '\'' +
//                ", valor='" + valor + '\'' +
//                ", nome='" + nome + '\'' +
//                ", tipo=" + tipo +
//                ", linha='" + linha + '\'' +
//                '}';
//    }
}
