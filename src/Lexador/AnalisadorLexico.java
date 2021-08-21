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
    int num_linhas = 1;
    Tipos tipo;
    ArrayList<String> palavras_reservadas;
    ArrayList<String> classificPRs;
    ArrayList<String> classificaSimbolos;
    private final static Map<String, String> simbolos_reservados = new HashMap<String, String>();
    public static ArrayList<Simbolo> simbolos = new ArrayList<Simbolo>();
    ArrayList<Identificadores> listaIdentificadores = new ArrayList<Identificadores>();
    ArrayList<String> cadeia_lex = new ArrayList<>();
    String ultimaPR = "";
    public static String copia_programa = "", erros="--- ERRO LEXICO ---\n";
    public static int num_erros = 0;
    public static boolean tem_erro_lexico = false;

    public AnalisadorLexico(){
        palavras_reservadas = new ArrayList<String>();
        classificPRs = new ArrayList<>();
        classificaSimbolos = new ArrayList<>();
        tipo = new Tipos();
        palavras_reservadas.add(0,tipo.PUBLIC);
        palavras_reservadas.add(1,tipo.PRIVATE);
        palavras_reservadas.add(2,tipo.CLASS_WORD);
        palavras_reservadas.add(3,tipo.INT);
        palavras_reservadas.add(4,tipo.LONG);
        classificPRs.add(0,"PR - visibilidade");
        classificPRs.add(1,"PR - visibilidade");
        classificPRs.add(2,"PR - class");
        classificPRs.add(3,"PR - tipo primitivo");
        classificPRs.add(4,"PR - tipo primitivo");

        simbolos_reservados.put("(",tipo.DELIMITADOR_INICIO_PARAMETRO);
        simbolos_reservados.put(")", tipo.DELIMITADOR_FIM_PARAMETRO);
        simbolos_reservados.put("{",tipo.INICIO_ESCOPO);
        simbolos_reservados.put("}",tipo.FIM_ESCOPO);
        simbolos_reservados.put(";",tipo.FIM_DE_INSTRUCAO);
        simbolos_reservados.put(".",tipo.PONTO);
        simbolos_reservados.put("=",tipo.ATRIBUICAO);
        simbolos_reservados.put(",",tipo.VIRGULA);
        simbolos_reservados.put("^[0-9]",tipo.CONSTANTE_NUM);
        classificaSimbolos.add(0,"Simbolo - inicio de parametro");
        classificaSimbolos.add(1,"Simbolo - fim de parametro");
        classificaSimbolos.add(2,"Simbolo - inicio de escopo");
        classificaSimbolos.add(3,"Simbolo - fim de escopo");
        classificaSimbolos.add(4,"Simbolo - fim de instrucao");
        classificaSimbolos.add(5,"Simbolo - de acesso a objecto");
        classificaSimbolos.add(6,"Simbolo - de atribuicao");
        classificaSimbolos.add(7,"Simbolo - separador de elementos");
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
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            copia_programa += data + "\n";
            analizaLinha(data);
            num_linhas++;
        }
        myReader.close();
        imprimeCodigoFonte(copia_programa);
    }

    public void imprimeCodigoFonte(String codigoFonte){
        System.out.println();
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
        String [] splittedLexema = lexema.split("[){;(},=]");
        cadeia_lex = readyArrayList(splittedLexema);
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

           if (lexema.charAt(i) == '='){
               simbol = '=';
               cadeia_lex.add(simbol + "");
           }

            if (lexema.charAt(i) == ','){
                simbol = ',';
                cadeia_lex.add(simbol + "");
            }
        }

       for(int i = 0; i < cadeia_lex.size(); i++){
           tipoLexema(cadeia_lex.get(i));
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

    public void tipoLexema(String lexema){
        if(palavras_reservadas.contains(lexema))
            encontrarClassificPalavra(lexema);
        else if (simbolos_reservados.containsKey(lexema))
            encontrarClassificSimbolo(lexema);
        else if (!lexema.chars().allMatch( Character::isDigit ))
                encontrarClassficIdentificador(lexema);
            else {
                criaSimbolo(tipo.CONSTANTE_NUM, lexema);
                atribuirValor(lexema);
            }
    }

    public void atribuirValor (String valor){
        listaIdentificadores.get(listaIdentificadores.size()-1).setValor(valor);
    }


    public void encontrarClassficIdentificador(String nome){
        String memoria="", valor="";
        int isExistente =  verIdExiste(nome);

        if(isExistente == -1) {
            if (ultimaPR.equals("int") || ultimaPR.equals("long")) {
                memoria = "primitiva";
                valor = 0 + "";
            } else {
                memoria = "---";
                valor = "---";
            }
            criarIndentificador(memoria, valor, nome, ultimaPR);
            criaSimbolo(ultimaPR, nome);
        }else{
            erros += "O identificador "+nome+" na linha ->"+num_linhas+"ja existe\n";
            num_erros++;
            tem_erro_lexico = true;
            String classificExistent = listaIdentificadores.get(isExistente).getTipo();
            criaSimbolo(classificExistent,nome);
        }
    }

    public int verIdExiste(String nome){
        int isExistente = -1;
        for (byte i =0; i < listaIdentificadores.size(); i++){
            if(nome.equals(listaIdentificadores.get(i).getNome()))
                isExistente = i;
        }
        return isExistente;
    }

    public void criarIndentificador(String memoria, String valor, String nome, String tipo){
            Identificadores identificadores = new Identificadores();
            identificadores.setNome(nome);
            identificadores.setMemoria(memoria);
            identificadores.setTipo(tipo);
            identificadores.setValor(valor);
            identificadores.setLinha(num_linhas);
            listaIdentificadores.add(identificadores);
    }

    // cria palavra reservada fornecida pelo programador que coincide com a lista de palavras do programa
    public void encontrarClassificPalavra(String nome){
        String classificacao = "";
        for(int i=0; i < palavras_reservadas.size(); i++){
            if (nome.equals(palavras_reservadas.get(i)))
              classificacao = classificPRs.get(i);
        }
        ultimaPR = nome;
        criaSimbolo(classificacao, nome);
    }

    public void encontrarClassificSimbolo(String simbolo){
        String classificacao = "";
        for (Map.Entry<String,String> entry : simbolos_reservados.entrySet())
            if (entry.getKey().equals(simbolo)){
                classificacao = entry.getValue();
            }
        criaSimbolo(classificacao, simbolo);
    }

    public void criaSimbolo(String classificacao, String nome){
        Simbolo s = new Simbolo();
        s.setNome(nome);
        s.setLinha(num_linhas);
        s.setClassificacao(classificacao);
        simbolos.add(s);
    }

    public void verSimbolos(){
        System.out.println("        ______________Tabela de Simbolos_______________________" +
                "\n----------------------------------------------------------------------------" +
                "\n|        Nome        |                Classificacao                |  linha |" +
                "\n----------------------------------------------------------------------------"
        );

        for(Simbolo simb : simbolos){
            System.out.println(simb);
        }
    }

    public void verIdentificadores(){
        System.out.println("      ______________Tabela de Identificadores_______________" +
                "\n---------------------------------------------------------------------" +
                "\n|      Nome     |     Tipo     |   Valor   |   Memoria   |   linha  |" +
                "\n---------------------------------------------------------------------"
        );
        for(Identificadores ident : listaIdentificadores){
            System.out.println(ident);
        }
        System.out.println();
    }

//    public static void main(String[] args) {
//        AnalisadorLexico al = new AnalisadorLexico();
//        al.GestorFicheiro();
//        al.verIdentificadores();
//        al.verSimbolos();
//    }
}
