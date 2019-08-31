package com.krontobi;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;

public class Parser {
    public void doParse(BufferedReader bufferedReader) throws IOException {
        String[] str = new String[4];

        Properties parseString = new PropertyReader().getProperties();
        //System.out.println(parseString);
        while ((str[1] = bufferedReader.readLine()) != null) {
            //System.out.println(str[1]);
            if (str[1].contains(parseString.getProperty("price"))) {
                str[1] = str[1].substring(0, str[1].indexOf(" руб"));
                str[1] = str[1].replaceAll(parseString.getProperty("price"), "");
                System.out.println("Цена: " + str[1]);
            }
            else if (str[1].contains(parseString.getProperty("reviews"))) {
                str[2] = str[1];
                str[3] = str[2];

                str[2] = str[2].substring(str[2].indexOf("\"totalValidNum\":"), str[2].indexOf(",\"trialReviewNum\":0,"));
                str[2] = str[2].replaceAll(parseString.getProperty("reviews"), "");
                System.out.println("Отзывы: " + str[2]);

                str[3] = str[3].substring(str[3].indexOf(parseString.getProperty("orders")), str[3].indexOf(",\"tradeCountUnit\""));
                str[3] = str[3].replaceAll(parseString.getProperty("orders"), "");
                System.out.println("Заказ(ов): " + str[3]);
            }
        }
    }
}

