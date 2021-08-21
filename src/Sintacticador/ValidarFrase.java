package Sintacticador;

import Lexador.AnalisadorLexico;
import modelos.Simbolo;

import java.util.ArrayList;

public class ValidarFrase {
    String p ="", nome ="", classificacao ="", cl = "", pub = "", chavE = "";
    public static String erros="!!!!!_ ERROS _!!!!!\n";
    public int linha=0;
    public static int num_erros=0;
    public static int estado = 1;
    public static boolean hasError = false;
//    public final String PUBLIC_WORD = "public";
//    public final String CLASS_WORD = "class";
//    public final String NOME_CLASS = "^[a-zA-Z0-9_-]*$";
//    public final String CHAVETA_E = "{";
    String [] regra_sequencia_palavras = {"public","class","","{"};
    ArrayList<String> sequencia_nomes_fornecidos = new ArrayList();

    public ValidarFrase(){}

    public void recebeInfoLexema(Simbolo s) {
        this.nome = s.getNome();
        this.classificacao = s.getClassificacao();
        this.linha = s.getLinha();
        if(classificacao.equals("PR - class"))
            regra_sequencia_palavras[2] = classificacao;
        sequencia_nomes_fornecidos.add(nome);
    }

    public void validarLexema(){
       // System.out.println(estado);
        if (estado == 1) {
            if (nome.equals("class"))
                estado = 3;
            else if (nome.equals("public"))
                estado = 2;
            else {
                erros += "ERRO LEXICO - Esperava encontrar palavra class ou public. linha - " + linha + "\n";
                num_erros++;
                hasError = true;
            }
        }else {
            if (estado == 2) {
                if (nome.equals("class"))
                    estado = 3;
                else {
                    erros += "ERRO LEXICO - Esperava encontrar palavra class. linha - " + linha + "\n";
                    num_erros++;
                    hasError = true;
                }
            }else {
                if (estado == 3) {
                    if (classificacao.equals("class"))
                        estado = 4;
                    else {
                        erros += "ERRO Sintatico- identificador invalido. linha - " + linha + "\n";
                        num_erros++;
                        hasError = true;
                    }
                }else{
                        if (estado == 4) {
                            if (nome.equals("{"))
                                estado = 5;
                            else {
                                erros += "ERRO Sintatico- Esperava encontrar { . linha - " + linha + "\n";
                                num_erros++;
                                hasError = true;
                            }
                        }else{
                                if (estado == 5) {
                                    if (nome.equals("public") || nome.equals("private"))
                                        estado = 7;
                                    else if (nome.equals("}"))
                                        estado = 6;
                                    else if (classificacao.equals("class"))
                                        estado = 10;
                                    else {
                                        erros += "ERRO Sintatico- Esperava encontrar {. linha - " + linha + "\n";
                                        num_erros++;
                                        hasError = true;
                                    }
                                }else{
                                    if (estado == 7) {
                                        if (nome.equals("int") || nome.equals("long"))
                                            estado = 8;
                                        else {
                                            erros += "ERRO Sintatico - Esperava encontrar int ou long. linha - " + linha + "\n";
                                            num_erros++;
                                            hasError = true;
                                        }
                                    }else{
                                        if (estado == 8) {
                                            if (classificacao.equals("long") || classificacao.equals("int"))
                                                estado = 9;
                                            else {
                                                erros += "ERRO sintatico - identificador invalido. linha - " + linha + "\n";
                                                num_erros++;
                                                hasError = true;
                                            }
                                        }else{
                                            if (estado == 9) {
                                                if (nome.equals(","))
                                                    estado = 8;
                                                else if (nome.equals(";"))
                                                    estado = 5;
                                                else if(nome.equals("="))
                                                    estado = 15;
                                                else{
                                                    erros += "ERRO Sintatico- Esperava encontrar (,) ou (;) linha - " + linha + "\n";
                                                    num_erros++;
                                                    hasError = true;
                                                }
                                            }else{
                                                if (estado == 10) {
                                                    if (nome.equals("("))
                                                        estado = 11;
                                                    else {
                                                        erros += "ERRO Sintatico- Esperava encontrar (. linha - " + linha + "\n";
                                                        num_erros++;
                                                        hasError = true;
                                                    }
                                                }else{
                                                    if (estado == 11) {
                                                        if (nome.equals(")"))
                                                            estado = 12;
                                                        else {
                                                            erros += "ERRO Sintatico- Esperava encontrar ). linha - " + linha + "\n";
                                                            num_erros++;
                                                            hasError = true;
                                                        }
                                                    }else{
                                                        if (estado == 12) {
                                                            if (nome.equals("{"))
                                                                estado = 13;
                                                            else {
                                                                erros += "ERRO Sintatico- Esperava encontrar {. linha - " + linha + "\n";
                                                                num_erros++;
                                                                hasError = true;
                                                            }
                                                        }else{
                                                            if (estado == 13) {
                                                                if (nome.equals("}"))
                                                                    estado = 14;
                                                                else {
                                                                    erros += "ERRO Sintatico- Esperava encontrar }. linha - " + linha + "\n";
                                                                    num_erros++;
                                                                    hasError = true;
                                                                }
                                                            }else{
                                                                if(estado == 14) {
                                                                    if (nome.equals("}")) {
                                                                        estado = 6;
                                                                    }else {
                                                                        erros += "ERRO Sintatico- Esperava encontrar }. linha - " + linha + "\n";
                                                                        num_erros++;
                                                                        hasError = true;
                                                                    }
                                                                }else{
                                                                    if(estado == 15){
                                                                        if (nome.chars().allMatch(Character::isDigit))
                                                                            estado = 9;
                                                                        else{
                                                                            erros += "ERRO Semantico - valor deve ser inteiro. linha - " + linha + "\n";
                                                                            num_erros++;
                                                                            hasError = true;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                        }
                }
            }
        }
    }

    public static void verErros(){
        System.out.println(erros);
        System.out.println();
        System.out.println("Programa terminou com "+(ValidarFrase.num_erros + AnalisadorLexico.num_erros)+" ERROS");
    }

    public void verLexemas(){
        //System.out.println(sequencia_nomes_fornecidos);
    }

    boolean isWordOK (){
        boolean isOK = false;

        return  isOK;
    }
}
