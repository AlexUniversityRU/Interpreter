package com.company;

import static java.lang.System.exit;

public class Parser {

    private Lexer lexer;
    private Token currToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public void parse() {
        this.currToken = lexer.getNextToken();
        if(currToken.tCode == Token.TokenCode.ERROR)
            error("parse");
        statements();
    }

    private void statements() {
        if(currToken.tCode == Token.TokenCode.END) {
            return;
        } else {
            statement();
            if(currToken.tCode == Token.TokenCode.SEMICOL) {
                this.getNextToken("statments");
                statements();
                return;
            } else {
                error("statements");
            }
        }
    }

    private void statement() {
        if(currToken.tCode == Token.TokenCode.ID) {
            System.out.println("PUSH " + currToken.lexeme);
            this.getNextToken("");
            if(currToken.tCode == Token.TokenCode.ASSIGN) {
                this.getNextToken("statement");
                expr();
                System.out.println("ASSIGN");
                return;
            }
        } else if (currToken.tCode == Token.TokenCode.PRINT) {
            this.getNextToken("statement");
            System.out.println("PUSH " + currToken.lexeme);
            if (currToken.tCode == Token.TokenCode.ID) {
                this.getNextToken("statement");
                System.out.println("PRINT");
                return;
            }
            error("statement");
        }
        error("statement");
    }

    private void expr() {
        term();
        if (currToken.tCode == Token.TokenCode.ADD) {
            this.getNextToken("expr");
            expr();
            System.out.println("ADD");
            return;
        } else if (currToken.tCode == Token.TokenCode.SUB) {
            this.getNextToken("expr");
            expr();
            System.out.println("SUB");
            return;
        }
    }

    private void term() {
        factor();
        if(currToken.tCode == Token.TokenCode.MULT) {
            this.getNextToken("temr");
            term();
            System.out.println("MULT");
        }
        return;

    }

    private void factor() {
        if(currToken.tCode == Token.TokenCode.INT || currToken.tCode == Token.TokenCode.ID) {
            System.out.println("PUSH " + currToken.lexeme);
            this.getNextToken("factor");
            return;
        } else if ( currToken.tCode == Token.TokenCode.LPAREN) {
            this.getNextToken("factor");
            expr();
            if(currToken.tCode == Token.TokenCode.RPAREN) {
                this.getNextToken("factor");
                return;
            }

        }
        error("factor");


    }

    private void error(String from ) {
        System.out.println("Syntax Error");
        exit(1);
    }


    private void getNextToken(String str) {
        this.currToken = this.lexer.getNextToken();
        if (currToken.tCode == Token.TokenCode.ERROR) {
            error(str);
        }
    }
}