package com.krontobi;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;

public class Parser {
    public void doParse(BufferedReader bufferedReader) throws IOException {
        String[] str = new String[3];

        Properties parseString = new PropertyReader().getProperties();
        System.out.println(parseString);
        while ((str[0] = bufferedReader.readLine()) != null) {
            if (str[0].contains(parseString.getProperty("price"))) {

                str[0] = str[0].substring(0, str[0].indexOf(" руб"));
                str[0] = str[0].replaceAll(parseString.getProperty("price"), "");
                System.out.println(str);
                break;
            }
        }

        while ((str = bufferedReader.readLine()) != null) {
            if (str.contains(parseString.getProperty("reviews"))) {
                str = str.substring(str.indexOf("\"totalValidNum\":"), str.indexOf(",\"trialReviewNum\":0,"));
                str = str.replaceAll(parseString.getProperty("reviews"), "");
                System.out.println(str);
                break;
            }
        }

        while ((str = bufferedReader.readLine()) != null) {
            if (str.contains(parseString.getProperty("orders"))) {
                str = str.substring(str.indexOf(parseString.getProperty("orders")), str.indexOf(",\"tradeCountUnit\""));
                str = str.replaceAll(parseString.getProperty("orders"), "");
                System.out.println(str);
                break;
            }
        }
    }
}

