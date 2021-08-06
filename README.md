# J2V8ModuleJs 

A modular Js engine based on J2V8，基于J2V8实现的模块化Js引擎

模块化功能由略加修改的*sea.js*实现，语法类似于CommonJs

## **Usage**   

注意：在使用该库前，你需要将J2V8库一并加入到项目依赖中  

### **Gradle**  

```gradle  
dependencies{
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.eclipsesource.j2v8:j2v8_win32_x86_64:4.6.0'
    //implementation 'com.eclipsesource.j2v8:j2v8_macosx_x86_64:4.6.0'
    //implementation 'com.eclipsesource.j2v8:j2v8_linux_x86_64:4.8.0'
    //implementation 'com.eclipsesource.j2v8:j2v8_android_armv7l:3.0.2'
    //implementation 'com.eclipsesource.j2v8:j2v8_android_x86:3.0.1'
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
* [jar](https://raw.githubusercontent.com/Dobmod/J2V8ModuleJs/main/out/artifacts/J2V8ModuleJS_jar/J2V8ModuleJS.jar)  

## **Addtional**
```java
moduleJs.getV8Runtime();//获取V8
```




