package com.company;

import java.io.IOException;

/**
 * Created by Owner on 05-Feb-17.
 */
public class Compiler {


    public Lexer myLexer;
    public Parser myParser;

    public Compiler() {
        this.myLexer = new Lexer();
        this.myParser = new Parser(myLexer);

    }

    public static void main(String[] args) throws IOException {
        Compiler comp = new Compiler();
        comp.myParser.parse();
        //Interpreter interpreter = new Interpreter();



    }
}