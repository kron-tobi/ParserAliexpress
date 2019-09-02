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

            ConnectionDB connectionDB = new ConnectionDB();
            connectionDB.setConnectionDB();
            ParserURL parserURL = new ParserURL();
            String shortURL = parserURL.getShortURL(url.toString());
            result[0] = shortURL;
            // если ссылка на товар новая
            if(!connectionDB.existURL(shortURL)) {
                connectionDB.addNewURL(url.toString(),shortURL);
                //connectionDB.getId("id_url_product", shortURL, "url_all_products");
            }
            connectionDB.insertInDB(result);
            connectionDB.closeConnectionDB();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
