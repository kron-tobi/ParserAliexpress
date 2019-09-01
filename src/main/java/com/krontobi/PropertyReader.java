package com.krontobi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    private Properties properties;

    public PropertyReader(String prop) throws FileNotFoundException {
        properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(("C:\\Program krontobi\\ParserAliexpress\\src\\main\\resources\\").concat(prop));
        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {
        return properties;
    }

}
