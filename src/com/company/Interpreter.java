package com.company;


import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Interpreter {

    String S;
    // Not really a 'stack' but treated as one until we have to grab
    ArrayList<ArithmeticObject> stack = new ArrayList<>();
    Queue<String> fetchqueue = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        Interpreter interpret = new Interpreter();

    }

    public Interpreter(){
        S = getInput();
        fetch();
        while(!fetchqueue.isEmpty())
            decode();
    }

    // Loop through and fetch the important parts of each line,
    // add to a queue to be used in decode
    private void fetch(){
        String[] splitted = S.split("\\s+");
        for(int i=0; i<splitted.length; i++){
            int next = i+1;

            // PUSH
            if(splitted[i].matches("PUSH")){
                fetchqueue.add(splitted[next]);
                i = next;
            }
            // SUB
            else if(splitted[i].matches("SUB")){
                fetchqueue.add(splitted[i]);
            }
            // MULT
            else if(splitted[i].matches("MULT")){
                fetchqueue.add(splitted[i]);
            }
            // ASSIGN '='
            else if(splitted[i].matches("ASSIGN")){
                fetchqueue.add(splitted[i]);
            }
            // ADD '+'
            else if(splitted[i].matches("ADD")){
                fetchqueue.add(splitted[i]);
            }
            // PRINT
            else if(splitted[i].matches("PRINT")){
                fetchqueue.add(splitted[i]);
            } else {
                System.out.println("error for operator: " + splitted[i]);
                return;
            }
        }
    }

    // If integer push on stack
    // else if operation -> execute
    // else if variable exists, find and push it onto the top
    private void decode(){

        if(fetchqueue.isEmpty()) {
            return;
        }

        String front = fetchqueue.remove();

        try {
            // is Integer number
            int integer = Integer.parseInt(front);
            ArithmeticObject ArithmeticObject = new ArithmeticObject();
            ArithmeticObject.setVal(integer);
            stack.add(ArithmeticObject);
        } catch (NumberFormatException e) {

            if(isOp(front)){
                execute(front);

            } else {
                // is Variable name

                // check if variable exists in stack, if so move to top.
                boolean exists = false;
                int index = 0;
                ArithmeticObject push_top = null;
                for(ArithmeticObject t : stack){
                    if(t.varSet()) {
                        if (t.getVar().matches(front)) {
                            push_top = stack.get(index);
                            exists = true;
                        }
                    }
                    index++;
                }

                if(!exists) {
                    ArithmeticObject ArithmeticObject = new ArithmeticObject();
                    ArithmeticObject.setVar(front);
                    stack.add(ArithmeticObject);
                } else {
                    // Replace existing variable to top of stack
                    stack.add(stack.remove(stack.indexOf(push_top)));
                }

            }
        }

    }

    // Pop two off stack perform operation and push
    // calculated value back on stack
    private void execute(String op){

        if(stack.size() == 1){
            if(op.matches("PRINT")){
                printTopStack();
            } else {
                System.out.println("error : Not enough operands to perform operation: " + op);
            }
            return;
        }


        int front = stack.size()-2;
        ArithmeticObject F = stack.remove(front);
        front = stack.size()-1;
        ArithmeticObject S = stack.remove(front);


        if(op.matches("ASSIGN")){
            assign(F,S);
        }
        else if(op.matches("SUB")){
            sub(F,S);
        }
        else if(op.matches("ADD")){
            add(S,F);
        }
        else if(op.matches("MULT")){
            mult(F, S);
        }
        else if(op.matches("PRINT")){
            printTopStack();
        } else {
            System.out.println("error for operator: " + op);
        }

    }

    private void printTopStack(){
        System.out.println(stack.get(stack.size()-1).getVal());
    }

    // ********************* //
    // ARITHMETIC OPERATIONS
    // ********************* //

    // F * S
    private void mult(ArithmeticObject F, ArithmeticObject S){
        switch(switchSet(F, S)){
            case 0:
                // F-var, S-var
                if(S.valSet() && F.valSet()){
                    int x = F.getVal() * S.getVal();
                    ArithmeticObject ArithmeticObject = new ArithmeticObject();
                    ArithmeticObject.setVal(x);
                    stack.add(ArithmeticObject);
                } else {
                    System.out.println("error :" + S.getVar() + " or " + F.getVar() + " -> variable not initialized!");
                }
                break;
            case 1:
                // F-var, S-val
                if(F.valSet()){
                    int x = F.getVal() * S.getVal();
                    ArithmeticObject ArithmeticObject = new ArithmeticObject();
                    ArithmeticObject.setVal(x);
                    stack.add(ArithmeticObject);
                } else {
                    System.out.println("error : " + F.getVar() + " -> variable not initialized!");
                }
                break;
            case 2:{
                // F-val, S-val
                int x = F.getVal() * S.getVal();
                ArithmeticObject ArithmeticObject = new ArithmeticObject();
                ArithmeticObject.setVal(x);
                stack.add(ArithmeticObject);
                break;}
            case 3:
                // F-val, S-var
                if(S.valSet()){
                    int x = F.getVal() * S.getVal();
                    ArithmeticObject ArithmeticObject = new ArithmeticObject();
                    ArithmeticObject.setVal(x);
                    stack.add(ArithmeticObject);
                } else {
                    System.out.println("error : " + S.getVar() + " -> variable not initialized!");
                }
                break;
            default:
                System.out.println("error : Op not found");
                break;
        }
    }

    // F + S
    private void add(ArithmeticObject F, ArithmeticObject S){
        switch(switchSet(F, S)){
            case 0:
                // F-var, S-var
                if(S.valSet() && F.valSet()){
                    int x = F.getVal() + S.getVal();
                    ArithmeticObject ArithmeticObject = new ArithmeticObject();
                    ArithmeticObject.setVal(x);
                    stack.add(ArithmeticObject);
                } else {
                    System.out.println("error : " + S.getVar() + " or " + F.getVar() + " -> variable not initialized!");
                }
                break;
            case 1:
                // F-var, S-val
                if(F.valSet()){
                    int x = F.getVal() + S.getVal();
                    ArithmeticObject ArithmeticObject = new ArithmeticObject();
                    ArithmeticObject.setVal(x);
                    stack.add(ArithmeticObject);
                } else {
                    System.out.println("error : " + F.getVar() + " -> variable not initialized!");
                }
                break;
            case 2:{
                // F-val, S-val
                int x = F.getVal() + S.getVal();
                ArithmeticObject ArithmeticObject = new ArithmeticObject();
                ArithmeticObject.setVal(x);
                stack.add(ArithmeticObject);
                break;}
            case 3:
                // F-val, S-var
                if(S.valSet()){
                    int x = F.getVal() + S.getVal();
                    ArithmeticObject ArithmeticObject = new ArithmeticObject();
                    ArithmeticObject.setVal(x);
                    stack.add(ArithmeticObject);
                } else {
                    System.out.println("error : " + S.getVar() + " -> variable not initialized!");
                }
                break;
            default:
                System.out.println("error : Op not found");
                break;
        }
    }

    // F - S
    private void sub(ArithmeticObject F, ArithmeticObject S){

        switch(switchSet(F, S)){
            case 0:
                // F-var, S-var
                if(S.valSet() && F.valSet()){
                    int x = F.getVal() - S.getVal();
                    ArithmeticObject ArithmeticObject = new ArithmeticObject();
                    ArithmeticObject.setVal(x);
                    stack.add(ArithmeticObject);
                } else {
                    System.out.println("error : " + S.getVar() + " or " + F.getVar() + " -> variable not initialized!");
                }

                break;
            case 1:{
                // F-var, S-val
                int x = F.getVal() - S.getVal();
                ArithmeticObject ArithmeticObject = new ArithmeticObject(F.getVar(), x);
                stack.add(ArithmeticObject);
                break;}
            case 2:{
                // F-val, S-val
                int x = F.getVal() - S.getVal();
                ArithmeticObject ArithmeticObject = new ArithmeticObject();
                ArithmeticObject.setVal(x);
                stack.add(ArithmeticObject);
                break;}
            case 3:{
                // F-val, S-var
                if(S.valSet()){
                    int x = F.getVal() - S.getVal();
                    ArithmeticObject ArithmeticObject = new ArithmeticObject();
                    ArithmeticObject.setVal(x);
                    stack.add(ArithmeticObject);
                }
                break;}
            default:
                System.out.println("error : Op not found");
                break;
        }

    }

    // x = x
    private void assign(ArithmeticObject F, ArithmeticObject S){
        switch(switchSet(F, S)){
            case 0:
                // F-var, S-var
                if(S.valSet()){
                    F.setVal(S.getVal());
                    stack.add(F);
                    //printStack();
                } else {
                    System.out.println("error : " + S.getVar() + " -> variable not initialized!");
                }
                break;
            case 1:
                // F-var, S-val
                F.setVal(S.getVal());
                stack.add(F);
                break;
            case 2:{
                // F-val, S-val
                System.out.println("error : val = val -> Failure");
                break;}
            case 3:
                // F-val, S-var
                System.out.println("error : val = var -> Failure");
                break;
            default:
                System.out.println("error : Op not found in 'assign'");
                break;
        }
    }


    // ********************* //
    // HELPER FUNCTIONS
    // ********************* //

    private boolean isOp(String str){
        if(str.matches("ASSIGN") || str.matches("SUB") || str.matches("MULT") || str.matches("ADD")
                || str.matches("PRINT")){
            return true;
        }
        return false;
    }

    private int switchSet(ArithmeticObject F, ArithmeticObject S){
        if(F.varSet() && S.varSet()){
            // F-var, S-var
            return 0;
        }
        else if(F.varSet() && S.valSet()){
            // F-var, S-val
            return 1;
        }
        else if(F.valSet() && S.valSet()){
            // F-val, S-val
            return 2;
        }
        else if(F.valSet() && S.varSet()){
            // F-val, S-var
            return 3;
        }
        return 4;
    }

    public String getInput() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String s;
        String input = "";
        try {
            while ((s = in.readLine()) != null && s.length() != 0) {
                input += s + " ";

            }
        } catch(IOException e) {
            System.out.print(e.getStackTrace());
        }
        return input;
    }


}

// ********************* //
// Object for holding variable
// names and/or their values.
// ********************* //
class ArithmeticObject {
    private String variable;
    private int value = 0;
    private boolean varSet;
    private boolean valSet;

    public ArithmeticObject(){
        varSet = false;
        valSet = false;
    }

    public ArithmeticObject(String var, int val){
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
