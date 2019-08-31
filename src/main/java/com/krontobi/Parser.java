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
            if (str[1].contains(parseString.getProperty("price"))) {
                str[1] = str[1].substring(1, str[1].indexOf(" руб"));
                str[1] = str[1].replaceAll(parseString.getProperty("price"), "");
                System.out.println("Цена: " + str[1]);
            }
            else if (str[1].contains(parseString.getProperty("reviews"))) {
                str[2] = str[1];
                str[3] = str[2];

                str[2] = str[2].substring(str[1].indexOf("\"totalValidNum\":"), str[1].indexOf(",\"trialReviewNum\":0,"));
                str[2] = str[2].replaceAll(parseString.getProperty("reviews"), "");
                System.out.println("Отзывы: " + str[1]);

                str[3] = str[3].substring(str[2].indexOf(parseString.getProperty("orders")), str[2].indexOf(",\"tradeCountUnit\""));
                str[3] = str[3].replaceAll(parseString.getProperty("orders"), "");
                System.out.println("Заказ(ов): " + str[2]);
            }
        }
    }
}

