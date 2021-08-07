# J2V8ModuleJs 

A modular Js engine based on J2V8，基于J2V8实现的模块化Js引擎

模块化功能由略加修改的*sea.js*实现，语法类似于CommonJs

## **Usage**   

注意：在使用该库前，你需要将J2V8库一并加入到项目依赖中  

### **Gradle**  

```gradle  
dependencies{
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.eclipsesource.j2v8:j2v8:6.2.0'
}
```  

### **Main.java**  

```java  
J2V8ModuleJs moduleJs = new J2V8ModuleJs("/../../main.js");
try {
    moduleJs.init(true);//初始化并开启print函数，以便调试
    moduleJs.execute();//执行
}catch (FileNotFoundException e){
    e.printStackTrace();
}
moduleJs.release();//释放资源
```  

### **main.js**  
```javascript  
var person = require("person.js");
person.sayHello();
``` 

### **person.js**  
```javascript
exports.sayHello = function(){
    print("hello");
}
```

## **Download**  
* [jar](https://github.com/Dobmod/J2V8ModuleJs/releases)  

## **Addtional**  
```java  
moduleJs.getV8Runtime();//获取V8
moduleJs.defineModule(String moduleName,String script);//定义一个内部模块
moduleJs.hasModule(String moduleName);//获取模块是否存在
moduleJs.setRequireListener(new RequireListener() {
    @Override
    public boolean require(String filePath) {
        return true;
    }
});//监听js中require的模块，返回false则拦截require
```




