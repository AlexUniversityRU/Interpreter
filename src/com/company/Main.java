package com.company;

public class Main {

    public static void main(String[] args) {
        String input1 =
                "PUSH var\n" +
                "PUSH 3\n" +
                "ASSIGN\n";
        String input2 =
                "PUSH var\n" +
                "PUSH 3\n" +
                "ASSIGN\n" +
                "PUSH b\n" +
                "PUSH 4\n" +
                "PUSH 7\n" +
                "PUSH var\n" +
                "SUB\n" +
                "MULT\n" +
                "ASSIGN\n";


        Interpreter interpreter = new Interpreter(input2);

    }
}
