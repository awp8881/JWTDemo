package com.example.util;

import java.io.*;
import java.util.Properties;

public class ProUtil {

    private static Properties properties;

    public static void loadPro(String fileName) {
        InputStream inputStream = ProUtil.class.getClassLoader().getResourceAsStream(fileName);
         properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }


    public static String getCurrentPropertiesValue(String filePath,String key){
        properties  = new Properties();
        String val = "";
        try {
            //非实时动态获取
//            properties.load(new InputStreamReader(ProUtil.class.getClassLoader().getResourceAsStream(filePath), "UTF-8"));
            //下面为动态获取
            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            InputStream is = new FileInputStream(path + File.separator+ filePath);
            properties.load(is);
            val = properties.getProperty(key);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return val;
    }

}
