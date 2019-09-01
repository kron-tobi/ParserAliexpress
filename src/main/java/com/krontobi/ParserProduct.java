package com.krontobi;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;

public class ParserProduct {

    public void doParse(BufferedReader bufferedReader) throws IOException {
        String[] str = new String[4];
        String s;
        Properties parseString = new PropertyReader("config.properties").getProperties();
        //System.out.println(parseString);
        System.out.println("URL: " + parseString.getProperty("product.url"));
        while ((s = bufferedReader.readLine()) != null) {
            //System.out.println(s);
            if (s.contains(parseString.getProperty("product.price"))) {
                str[0] = str[1] = s;
                str[0] = doReplace(str[0], ". |", "-in ");
                str[1] = doReplace(str[1], parseString.getProperty("product.price"), " руб");
            } else if (s.contains(parseString.getProperty("product.reviews"))) {
                str[2] = str[3] = s;
                str[2] = doReplace(str[2], parseString.getProperty("product.reviews"), ",\"trialReviewNum\":0,");
                str[3] = doReplace(str[3], parseString.getProperty("product.orders"), ",\"tradeCountUnit\"");
                break;
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