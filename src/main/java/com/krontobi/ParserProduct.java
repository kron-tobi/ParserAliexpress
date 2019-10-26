package com.krontobi;

import com.krontobi.customException.NotFindOrdersException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;

public class ParserProduct {

    private static final Properties properties = new PropertyReader("config.properties").getProperties();

    public String[] doParse(BufferedReader bufferedReader) throws IOException, NotFindOrdersException {
        String[] str = new String[5];
        String s;
        StringBuilder fullString = new StringBuilder();

        str[0] = properties.getProperty("product.url");
        while ((s = bufferedReader.readLine()) != null) {
            fullString.append(s);
            //System.out.println(s);
        }
        String result = fullString.toString();
        str[1] = doFindStr(result, properties, "product.name", "-in ");
        str[2] = doFindStr(result, properties, "product.price", "руб.");
        str[3] = doFindStr(result, properties, "product.reviews", ",\"trialReviewNum\":0,");
        str[4] = doFindStr(result, properties, "product.orders", ",\"tradeCountUnit\"");
        if (str[4] == null) {
            throw new NotFindOrdersException("Not found orders");
        }
        //print(str);
        return str;
    }

    private String doReplace(String str, String beginIndex, String endIndex) {
        int firstIndex = -4;
        int secondIndex = -4;
        while (firstIndex < 0 || secondIndex < 0) {
            firstIndex = str.indexOf(beginIndex);
            secondIndex = str.indexOf(endIndex);
        }

        str = str.substring(firstIndex, secondIndex);
        str = str.replace(beginIndex, "");
        return str;
    }

    public void print(String[] str) {
        for (String s : str) {
            System.out.println(s);
        }
    }

    private String doFindStr(String result, Properties parseString, String beginWord, String endWord) {
        if (result.contains(parseString.getProperty(beginWord))) {
            result = doReplace(result, parseString.getProperty(beginWord), endWord);
        } else result = null;
        return result;
    }

}