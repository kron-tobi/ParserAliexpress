package com.krontobi;

import java.io.*;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        selectParsingPages("product.url");
        //selectParsingPages("list.product.url");
    }

    private static void selectParsingPages(String address){
        try {
            PropertyReader propertyReader = new PropertyReader("config.properties");
            URL url = new URL(propertyReader.getProperties().getProperty(address));
            InputStream reader = url.openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(reader));
            new ParserProduct().doParse(bufferedReader);
            //new ParserListProducts().doParse(bufferedReader);
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
