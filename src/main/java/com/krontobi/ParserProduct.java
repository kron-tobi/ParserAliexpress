package com.krontobi;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;

public class ParserProduct {

    public String[] doParse(BufferedReader bufferedReader) throws IOException {
        String[] str = new String[5];
        String s;
        Properties parseString = new PropertyReader("config.properties").getProperties();
        str[0] = parseString.getProperty("product.url");
        while ((s = bufferedReader.readLine()) != null) {
            //System.out.println(s);
            if (s.contains(parseString.getProperty("product.price"))) {
                str[1] = str[2] = s;
                str[1] = doReplace(str[1], ". |", "-in ");
                str[2] = doReplace(str[2], parseString.getProperty("product.price"), " руб");
            } else if (s.contains(parseString.getProperty("product.reviews"))) {
                str[3] = str[4] = s;
                str[3] = doReplace(str[3], parseString.getProperty("product.reviews"), ",\"trialReviewNum\":0,");
                str[4] = doReplace(str[4], parseString.getProperty("product.orders"), ",\"tradeCountUnit\"");
                break;
            }
        }
        //print(str);
        return str;
    }

    private String doReplace(String str, String beginIndex, String endIndex) {
        str = str.substring(str.indexOf(beginIndex), str.indexOf(endIndex));
        str = str.replace(beginIndex, "");
        return str;
    }

    public void print(String[] str) {
        for(String s : str) {
            System.out.println(s);
        }
    }

}