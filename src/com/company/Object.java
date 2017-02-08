package com.company;


public class Object {
    private String variable;
    private int value = 0;
    private boolean varSet;
    private boolean valSet;

    public Object(){
        varSet = false;
        valSet = false;
    }

    public Object(String var, int val){
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
