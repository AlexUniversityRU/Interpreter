package com.company;

/**
 * Created by Owner on 08-Feb-17.
 */
public class Token {
    private String variable;
    private int value = 0;
    private boolean varSet;
    private boolean valSet;

    public Token(){
        varSet = false;
        valSet = false;
    }

    public Token(String var, int val){
        variable = var;
        value = val;
        varSet = true;
        valSet = true;
    }

    public void setVar(String var){
        variable = var;
        varSet = true;
    }
    public void setVal(int val){
        value = val;
        valSet = true;
    }

    public String getVar(){
        return variable;
    }

    public int getVal(){
        return value;
    }

    public boolean varSet(){
        return varSet;
    }

    public boolean valSet(){
        return valSet;
    }


}
