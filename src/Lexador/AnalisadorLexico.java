package Lexador;

import constantes.Tipos;
import modelos.Identificadores;
import modelos.Simbolo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AnalisadorLexico {
    int count_pal_reser = 0, num_erros = 0, num_linhas = 1;
    Tipos tipo;
    ArrayList<String> palavras_reservadas;
    private final static Map<String, String> simbolos_reservados = new HashMap<String, String>();
    ArrayList<Simbolo> simbolos = new ArrayList<Simbolo>();
    ArrayList<Identificadores> identificadores = new ArrayList<Identificadores>();

    AnalisadorLexico(){
        palavras_reservadas = new ArrayList<String>();
        tipo = new Tipos();
        palavras_reservadas.add(0,tipo.PUBLIC);
        palavras_reservadas.add(1,tipo.PRIVATE);
        palavras_reservadas.add(2,tipo.CLASS_WORD);
        palavras_reservadas.add(3,tipo.INT);
        palavras_reservadas.add(4,tipo.LONG);

        simbolos_reservados.put("(",tipo.DELIMITADOR_INICIO_PARAMETRO);
        simbolos_reservados.put(")", tipo.DELIMITADOR_FIM_PARAMETRO);
        simbolos_reservados.put("{",tipo.INICIO_ESCOPO);
        simbolos_reservados.put("}",tipo.FIM_ESCOPO);
        simbolos_reservados.put(";",tipo.FIM_DE_INSTRUCAO);
        simbolos_reservados.put(".",tipo.PONTO);
    }

    public void GestorFicheiro(){
        try {
            buscaFicheiro();
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro nao foi encontrado.");
            e.printStackTrace();
        }
    }

    public void buscaFicheiro() throws FileNotFoundException{
        File myObj = new File("classe.txt");
        Scanner myReader = new Scanner(myObj);
        String copia_do_programa = "";
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            copia_do_programa += data + "\n";
            analizaLinha(data);
        }
        myReader.close();
        imprimeCodigoFonte(copia_do_programa);
    }

    public void imprimeCodigoFonte(String codigoFonte){
        System.out.println(codigoFonte);
    }

    public void analizaLinha(String linha){
        Scanner scanner = new Scanner(linha);
        String lexema = "";
        while(scanner.hasNext()){
            lexema = scanner.next();
            analisarLexema(lexema);
        }
        scanner.close();
    }

    public void analisarLexema(String lexema){
        if(palavras_reservadas.contains(lexema))
            System.out.println(lexema);
        else if (simbolos_reservados.containsKey(lexema))
            System.out.println(lexema);
        else
            System.out.println("nao e palavra reservada mas ok "+lexema);
    }

    public void criaIdentificador(String nome){

    }

    public static void main(String[] args) {
        AnalisadorLexico al = new AnalisadorLexico();
        al.GestorFicheiro();
    }
}
