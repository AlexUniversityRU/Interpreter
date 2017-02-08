package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;


public class Lexer {

    private Queue<Token> tokens = new LinkedList<Token>();

    public Lexer() {
        this.nextToken();
    };

    private void splitInput(String input) {

        String[] splitted = input.split("\\s+");

        for (String line : splitted) {
            String subString = "";
            boolean digitOrLetter = false;
            for (int i = 0; i < line.length(); i++) {
                char currentChar = line.charAt(i);
                if (Character.isLetter(currentChar) || Character.isDigit(currentChar)) {
                    subString += currentChar;
                    digitOrLetter = true;
                } else {
                    if (digitOrLetter) {
                        digitOrLetter = false;
                        addToken(subString);
                        subString = "";
                    }
                    addToken((String.valueOf(currentChar)));
                }
            }
            if (!subString.isEmpty()) {
                addToken(subString);
                subString = "";
            }
        }
    }

    private void addToken(String subString) {

        Token.TokenCode tokenCode;

        if (subString.matches("[0-9]+")) {
            tokenCode = Token.TokenCode.INT;
        } else if (subString.contentEquals("print")) {
            tokenCode = Token.TokenCode.PRINT;
        } else if (subString.contentEquals("end")) {
            tokenCode = Token.TokenCode.END;
        } else if (subString.matches("[A-Za-z]+")) {
            tokenCode = Token.TokenCode.ID;
        } else if (subString.length() == 1) {
            if (subString == "*") {
                tokenCode = Token.TokenCode.MULT;
            } else if (subString.contentEquals("+")) {
                tokenCode = Token.TokenCode.ADD;
            } else if (subString.contentEquals("-")) {
                tokenCode = Token.TokenCode.SUB;
            } else if (subString.contentEquals("(")) {
                tokenCode = Token.TokenCode.LPAREN;
            } else if (subString.contentEquals(")")) {
                tokenCode = Token.TokenCode.RPAREN;
            } else if (subString.contentEquals(";")) {
                tokenCode = Token.TokenCode.SEMICOL;
            } else if (subString.contentEquals("=")) {
                tokenCode = Token.TokenCode.ASSIGN;
            } else if (subString.contentEquals("*")) {
                tokenCode = Token.TokenCode.MULT;
            } else {
                tokenCode = Token.TokenCode.ERROR;
            }
        } else {
            tokenCode = Token.TokenCode.ERROR;
        }
        this.tokens.add(new Token(subString, tokenCode));
    }


    public void nextToken() {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String s;
        String input = "";

        try {
            while ((s = in.readLine()) != null && s.length() != 0) {
                input += s;

            }
        } catch(IOException e) {
            System.out.print(e.getStackTrace());
        }

        this.splitInput(input);
    }

    public Token getNextToken() {
        return this.tokens.remove();
    }

}
