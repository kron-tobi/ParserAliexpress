package com.krontobi;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;

public class ParserListProducts {

    public void doParse(BufferedReader bufferedReader) throws IOException {
        String[] str = new String[4];
        String s;
        Properties parseString = new PropertyReader("config.properties").getProperties();
        //System.out.println(parseString);
        System.out.println("URL: " + parseString.getProperty("list.product.url"));
        while ((s = bufferedReader.readLine()) != null) {
            System.out.println(s);
            if (s.contains(parseString.getProperty("list.product.name"))) {
                str[0] = s;
                str[0] = doReplace(str[0], parseString.getProperty("list.product.name"), "\",\"");
            }
        }
        new PrintResult().print(str);
    }

    private String doReplace(String str, String beginIndex, String endIndex) {
        str = str.substring(str.indexOf(beginIndex), str.indexOf(endIndex));
        str = str.replace(beginIndex, "");
        return str;
    }

}
