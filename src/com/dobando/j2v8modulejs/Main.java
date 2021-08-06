package com.dobando.j2v8modulejs;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        J2V8ModuleJs moduleJs = new J2V8ModuleJs("main.js");
        try {
            moduleJs.init(true);
            moduleJs.execute();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        moduleJs.release();
    }
}
