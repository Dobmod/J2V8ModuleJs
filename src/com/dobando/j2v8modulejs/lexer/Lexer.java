package com.dobando.j2v8modulejs.lexer;

public class Lexer {
    public static String parse(String old){
        StringBuilder builder = new StringBuilder();
        builder.append("define(function(require,exports,module){\n");
        builder.append(old);
        builder.append("\n");
        builder.append("});");
        return builder.toString();
    }
}