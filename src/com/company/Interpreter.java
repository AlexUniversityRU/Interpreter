package com.company;


import java.util.ArrayList;
import java.util.Stack;

public class Interpreter {
    String S;
    Stack<String> stack = new Stack<>();
    ArrayList<String> decode = new ArrayList<>();

    public Interpreter(String input){
        S = input;
        System.out.println("\nFetching...");
        fetch();
        System.out.println("\nDecoding...");
        decode();
        System.out.println("\nExecuting...");
        execute();
    }

    // Fetch
    private void fetch(){
        String[] splitted = S.split("\\s+");
        for(int i=0; i<splitted.length; i++){
            int next = i+1;

            // PUSH onto stack
            if(splitted[i].matches("PUSH")){
                stack.push(splitted[next]);
                i = next;
            }
            // SUB
            else if(splitted[i].matches("SUB")){
                stack.push(splitted[i]);
            }
            // MULT
            else if(splitted[i].matches("MULT")){
                stack.push(splitted[i]);
            }
            // ASSIGN '='
            else if(splitted[i].matches("ASSIGN")){
                stack.push(splitted[i]);
            }

        }

        for(String s : stack){
            System.out.println(s);
        }

    }

    // Decode
    private void decode(){

        for(String instr : stack){
            if(!instr.matches("ASSIGN") && !instr.matches("SUB") && !instr.matches("MULT")){
                decode.add(instr);

            } else if(decode.size() == 2){
                decode.add(1, instr);

            } else {
                for(int index =decode.size()-2; index > 0 ; index--){
                    if(insertInstruction(index)){
                        decode.add(index+1, instr);
                        break;
                    }

                }

            }
        }

        printList();

    }

    // Execute
    private void execute(){

        for(int index = decode.size()-1; index > 0; index--){

        }

    }

    private boolean insertInstruction(int index){
        int after = index+1;
        if(!decode.get(after).matches("ASSIGN") && !decode.get(after).matches("SUB") && !decode.get(after).matches("MULT") &&
                !decode.get(index).matches("ASSIGN") && !decode.get(index).matches("SUB") && !decode.get(index).matches("MULT")){
            return true;
        }
        return false;
    }

    private void printList(){

        for(String s : decode){
            System.out.print("[" + s + "]");
        }
        System.out.println();
    }



}
