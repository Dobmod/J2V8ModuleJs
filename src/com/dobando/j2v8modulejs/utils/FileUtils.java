package com.dobando.j2v8modulejs.utils;

import java.io.*;

public class FileUtils {
    public static String readFile(File file) {

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            fileInputStream.close();
            return outputStream.toString("utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String readFileFromResource(String path){
        try{
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while (true) {
                assert is != null;
                if ((len = is.read(buffer)) == -1) break;
                outputStream.write(buffer, 0, len);
            }
            is.close();
            return outputStream.toString("utf-8");
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
