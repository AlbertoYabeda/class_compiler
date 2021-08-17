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
    ArrayList<String> classific;
    private final static Map<String, String> simbolos_reservados = new HashMap<String, String>();
    ArrayList<Simbolo> simbolos = new ArrayList<Simbolo>();
    ArrayList<Identificadores> identificadores = new ArrayList<Identificadores>();

    AnalisadorLexico(){
        palavras_reservadas = new ArrayList<String>();
        classific = new ArrayList<>();
        tipo = new Tipos();
        palavras_reservadas.add(0,tipo.PUBLIC);
        palavras_reservadas.add(1,tipo.PRIVATE);
        palavras_reservadas.add(2,tipo.CLASS_WORD);
        palavras_reservadas.add(3,tipo.INT);
        palavras_reservadas.add(4,tipo.LONG);
        classific.add(0,"PR - visibilidade");
        classific.add(1,"PR - visibilidade");
        classific.add(2,"PR - class");
        classific.add(3,"PR - tipo primitivo");
        classific.add(4,"PR - tipo primitivo");

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
            num_linhas++;
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
        String [] splittedLexema = lexema.split("[){;(}]");
        ArrayList<String> cadeia_lex = readyArrayList(splittedLexema);
        char simbol=' ';

        for(int i=0; i<lexema.length(); i++) {
           if (lexema.charAt(i) == '{') {
               simbol = '{';
               cadeia_lex.add(simbol + "");
           }

           if (lexema.charAt(i) == '}') {
               simbol = '}';
               cadeia_lex.add(simbol + "");
           }

           if (lexema.charAt(i) == ';') {
               simbol = ';';
               cadeia_lex.add(simbol + "");
           }

           if (lexema.charAt(i) == ')') {
               simbol = ')';
               cadeia_lex.add(simbol + "");
           }

           if (lexema.charAt(i) == '(') {
               simbol = '(';
               cadeia_lex.add(simbol + "");
           }
        }

       for(int i = 0; i < cadeia_lex.size(); i++){
           imprimirLexema(cadeia_lex.get(i));
       }
    }

    public ArrayList<String> readyArrayList(String [] arrayPlug){
        ArrayList<String> filledArrayList = new ArrayList<>();
        int cont = 0;

        while(cont < arrayPlug.length){
            filledArrayList.add(cont,arrayPlug[cont]);
            cont++;
        }
        return filledArrayList;
    }

    public void imprimirLexema(String lexema){
        if(palavras_reservadas.contains(lexema))
            criarPRSource(lexema);
        else if (simbolos_reservados.containsKey(lexema))
            System.out.println(lexema);
        else
            System.out.println("nao e palavra reservada mas ok "+lexema);
    }

    // cria palavra reservada fornecida pelo programador que coincide com a lista de palavras do programa
    public void criarPRSource(String nome){
        String classificacao = "";
        Simbolo s = new Simbolo();

        for(int i=0; i < palavras_reservadas.size(); i++){
            if (nome.equals(palavras_reservadas.get(i)))
              classificacao = classific.get(i);
        }

        s.setNome(nome);
        s.setLinha(num_linhas);
        s.setClassificacao(classificacao);
        System.out.println("criou objecto");
        simbolos.add(s);
        System.out.println(s.toString());
    }

    public static void main(String[] args) {
        AnalisadorLexico al = new AnalisadorLexico();
        al.GestorFicheiro();
    }
}
