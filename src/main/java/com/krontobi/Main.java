package com.krontobi;

import java.io.*;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        try {
            PropertyReader propertyReader = new PropertyReader();
            URL url = new URL(propertyReader.getProperties().getProperty("url"));
            InputStream reader = url.openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(reader));
            new Parser().doParse(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
