package com.krontobi;

import com.krontobi.connection.ConnectionDB;
import com.krontobi.customException.NotFindOrdersException;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Executor {
    private static Logger log = Logger.getLogger(Executor.class);
    private static int countParsingPage = 0;
    private static String[] result;
    private static URL url;

    public void selectParsingPages(String productURL) {
        try {
            PropertyReader propertyReader = new PropertyReader("config.properties");
            url = new URL(propertyReader.getProperties().getProperty(productURL));
            InputStream reader = url.openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(reader));
            try {
                result = new ParserProduct().doParse(bufferedReader);
            } catch (NotFindOrdersException e) {
                log.info(e);
                if (countParsingPage < 100) {
                    countParsingPage++;
                    bufferedReader.close();
                    reader.close();
                    selectParsingPages(productURL);
                }
                return;
            }
            bufferedReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Number of Updates Page: " + countParsingPage);
    }

    public void sendProductInDataBase() {
        ConnectionDB connectionDB = new ConnectionDB();
        connectionDB.setConnectionDB();
        ParserURL parserURL = new ParserURL();
        String shortURL = parserURL.getShortURL(url.toString());
        result[0] = shortURL;
        // если ссылка на товар новая
        if (!connectionDB.existURL(shortURL)) {
            connectionDB.addNewURL(url.toString(), shortURL);
            //connectionDB.getId("id_url_product", shortURL, "url_all_products");
        }
        connectionDB.insertInDB(result);
        connectionDB.closeConnectionDB();
    }
}