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
//
//    public void visualizaClasse(){
//        File myObj = new File("classe.txt");
//        try {
//            Scanner myReader = new Scanner(myObj);
//            while (myReader.hasNextLine()) {
//                String data = myReader.nextLine();
//                //System.out.println(data);
//                analizaLinha(data);
//            }
//            myReader.close();
//        } catch (FileNotFoundException e) {
//            System.out.println("Ficheiro nao foi encontrado.");
//            e.printStackTrace();
//        }
//    }
//
//    public void analizaLinha(String linha){
//        Scanner scanner = new Scanner(linha);
//        String lexema = "";
//        while(scanner.hasNext()){
//            lexema = scanner.next();
//            if(palavras_reservadas.contains(lexema))
//                System.out.println(lexema);
//            else
//                System.out.println("nao e palavra reservada");
//        }
//        scanner.close();
//    }
//
//    public void criaIdentificador(String nome){
//
//    }

    public static void main(String[] args) {
        AnalisadorLexico al = new AnalisadorLexico();
        al.visualizaClasse();
    }
}
