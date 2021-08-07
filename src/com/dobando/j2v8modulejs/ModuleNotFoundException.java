package com.dobando.j2v8modulejs;

public class ModuleNotFoundException extends Exception{

    public ModuleNotFoundException(String s){
        super(s + " is not defined.");
    }

}
