package com.krontobi;

import com.krontobi.connection.ConnectionDB;

import java.io.*;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        selectParsingPages("product.url");
    }

    private static void selectParsingPages(String address){
        try {
            PropertyReader propertyReader = new PropertyReader("config.properties");
            URL url = new URL(propertyReader.getProperties().getProperty(address));
            InputStream reader = url.openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(reader));
            String[] result = new ParserProduct().doParse(bufferedReader);
            new ConnectionDB().postInfoInDB(result);
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
