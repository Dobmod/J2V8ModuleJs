package com.dobando.j2v8modulejs;

import com.dobando.j2v8modulejs.lexer.Lexer;
import com.dobando.j2v8modulejs.utils.FileUtils;
import com.eclipsesource.v8.*;

import java.io.File;
import java.io.FileNotFoundException;

public class J2V8ModuleJs {

    private final String entrance;
    private final V8 runtime;

    public J2V8ModuleJs(String entrance) {
        this.entrance = entrance;
        this.runtime = V8.createV8Runtime();
    }

    public Object execute() {
        return runtime.executeScript("seajs.use('"+new File(entrance).getName()+"');");
    }

    public void init(boolean enablePrintFunc) throws FileNotFoundException {
        File entranceFile = new File(entrance);
        if (!entranceFile.exists() || entranceFile.isDirectory()) {
            throw new FileNotFoundException(entrance + " is not exist,or it is a directory.");
        }
        JavaVoidCallback importCallback = (receiver, parameters) -> {
            if (parameters.length() > 0) {
                Object arg1 = parameters.get(0);
                runtime.executeScript(Lexer.parse(FileUtils.readFile(new File(arg1.toString()))));
                if (arg1 instanceof Releasable) {
                    ((Releasable) arg1).release();
                }
            }
        };
        runtime.registerJavaMethod(importCallback, "importScripts");
        JavaCallback getCwdCallback = new JavaCallback() {
            @Override
            public Object invoke(V8Object v8Object, V8Array v8Array) {
                return new File(entrance).getParent()+File.separator;
            }
        };
        runtime.registerJavaMethod(getCwdCallback, "getCwd");
        JavaVoidCallback callback = new JavaVoidCallback() {
            public void invoke(final V8Object receiver, final V8Array parameters) {
                if (parameters.length() > 0) {
                    Object arg1 = parameters.get(0);
                    System.out.println(arg1);
                    if (arg1 instanceof Releasable) {
                        ((Releasable) arg1).release();
                    }
                }
            }
        };
        if(enablePrintFunc) {
            runtime.registerJavaMethod(callback, "print");
        }
        runtime.executeScript(loadRequireJs());
    }

    public V8 getV8Runtime() {
        return runtime;
    }

    public void release() {
        runtime.release(false);
    }

    private String loadRequireJs() {
        return FileUtils.readFile(new File(Thread.currentThread().getContextClassLoader().getResource("sea.js").getPath()));
    }
}
