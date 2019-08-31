package com.krontobi;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;

public class Parser {
    public void doParse(BufferedReader bufferedReader) throws IOException {
        String[] str = new String[4];
        String s = "";
        Properties parseString = new PropertyReader().getProperties();
        //System.out.println(parseString);
        System.out.println("URL: " + parseString.getProperty("url"));
        while ((s = bufferedReader.readLine()) != null) {
            if (s.contains(parseString.getProperty("price"))) {
                str[0] = str[1] = s;
                str[0] = doReplace(str[0], parseString, ". |", "-in ");
                str[1] = doReplace(str[1], parseString, parseString.getProperty("price"), " руб");
            }
            else if (s.contains(parseString.getProperty("reviews"))) {
                str[2] = str[3] = s;
                str[2] = doReplace(str[2], parseString, parseString.getProperty("reviews"), ",\"trialReviewNum\":0,");
                str[3] = doReplace(str[3], parseString, parseString.getProperty("orders"), ",\"tradeCountUnit\"");
            }
        }
        new PrintResult().print(str);
    }

    public String doReplace(String str, Properties parseString, String beginIndex, String endIndex) {
        str = str.substring(str.indexOf(beginIndex), str.indexOf(endIndex));
        str = str.replace(beginIndex, "");
        return str;
    }
}

