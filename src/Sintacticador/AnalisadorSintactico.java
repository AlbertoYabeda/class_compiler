package Sintacticador;

import Lexador.AnalisadorLexico;
import modelos.Simbolo;

public class AnalisadorSintactico {
    public int  errosEncontrados;
    public String erroTipo;
    public String linha_actual = AnalisadorLexico.copia_programa;
    ValidarFrase vf;
    public AnalisadorSintactico(){
        vf = new ValidarFrase();
    }

    //a tabela de simbolos ja tem todo o programa.
    //podemos pegar todas as palavras e verificar a sequencia delas.

    public void validarFrase(){
        String palavra;
        for(int i=0; i < AnalisadorLexico.simbolos.size(); i++){
            Simbolo s = AnalisadorLexico.simbolos.get(i);
            vf.recebeInfoLexema(s);
            vf.validarLexema();
            //System.out.println(ValidarFrase.estado);
        }
        vf.verLexemas();

        if (ValidarFrase.estado == 6){
            System.out.println("Programa Terminado!");
            if (ValidarFrase.hasError)
                ValidarFrase.verErros();
        }else {
            ValidarFrase.verErros();
        }
    }

    public Simbolo indiceDaPalavra(String palavra){
        int indice = -1;
        Simbolo simboloModelo = new Simbolo();
        if(AnalisadorLexico.simbolos.contains(palavra)) {
            indice = AnalisadorLexico.simbolos.indexOf(palavra);
            System.out.println(indice);
            simboloModelo = AnalisadorLexico.simbolos.get(indice);
        }
        return simboloModelo;
    }


    public static void main(String[] args) {
        AnalisadorLexico al = new AnalisadorLexico();
        AnalisadorSintactico as = new AnalisadorSintactico();
        al.GestorFicheiro();
        al.verIdentificadores();
        al.verSimbolos();
        as.validarFrase();
        }
}
