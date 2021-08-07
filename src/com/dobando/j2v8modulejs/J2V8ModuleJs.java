package com.dobando.j2v8modulejs;

import com.dobando.j2v8modulejs.lexer.Lexer;
import com.dobando.j2v8modulejs.utils.FileUtils;
import com.eclipsesource.v8.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class J2V8ModuleJs {

    private final String entrance;
    private final V8 runtime;
    private RequireListener requireListener = null;
    private final Map<String,String> moduleMap;

    public J2V8ModuleJs(String entrance) {
        this.entrance = entrance;
        this.runtime = V8.createV8Runtime();
        this.moduleMap = new HashMap<>();
    }

    public Object execute() throws V8ScriptExecutionException {
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
                importScript(arg1.toString());
                if (arg1 instanceof Releasable) {
                    ((Releasable) arg1).release();
                }
            }
        };
        runtime.registerJavaMethod(importCallback, "importScripts");
        JavaCallback getCwdCallback = (v8Object, v8Array) -> (new File(entrance).getParent()+File.separator).replaceAll("\\\\","/");
        runtime.registerJavaMethod(getCwdCallback, "getCwd");
        JavaVoidCallback callback = (receiver, parameters) -> {
            if (parameters.length() > 0) {
                Object arg1 = parameters.get(0);
                System.out.println(arg1);
                if (arg1 instanceof Releasable) {
                    ((Releasable) arg1).release();
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

    public void setRequireListener(RequireListener listener){
        this.requireListener = listener;
    }

    public void defineModule(String moduleName,String script){
        moduleMap.put(moduleName,script);
    }

    public boolean hasModule(String moduleName){
        return moduleMap.containsKey(moduleName);
    }

    public void release() {
        moduleMap.clear();
        runtime.release(false);
    }

    private String loadRequireJs() {
        return FileUtils.readFileFromResource("sea.js");
    }

    private void importScript(String filePath) {
        File scriptFile = new File(filePath);
        if(scriptFile.exists()) {
            if (requireListener != null) {
                boolean canExec = requireListener.require(filePath);
                if (canExec) runtime.executeScript(Lexer.parse(FileUtils.readFile(new File(filePath))));
            } else {
                runtime.executeScript(Lexer.parse(FileUtils.readFile(new File(filePath))));
            }
        }else{
            String moduleName = scriptFile.getName().replace(".js","");
            System.out.println(moduleName);
            if(moduleMap.containsKey(moduleName)){
                runtime.executeScript(Lexer.parse(moduleMap.get(moduleName)));
            }
        }
    }

}
