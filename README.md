# J2V8ModuleJs 

A modular Js engine based on J2V8，基于J2V8实现的模块化Js引擎

模块化功能由略加修改的*sea.js*实现，语法类似于CommonJs

## **Usage**   

注意：在使用该库前，你需要将J2V8库一并加入到项目依赖中

```java  
J2V8ModuleJS moduleJS = new J2V8ModuleJS("/../../main.js");
try {
    moduleJS.init(true);//初始化并开启print函数，以便测试
}catch (FileNotFoundException e){
    e.printStackTrace();
}
moduleJS.execute();//执行
moduleJS.release();//释放资源
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
* [jar]()  

## **Addtional**
```java
moduleJs.getV8Runtime();//获取V8
```




