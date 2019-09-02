package com.krontobi;

public class ParserURL {
    public String getShortURL(String str) {
        String result = str;
        result = result.substring(str.indexOf("item/"),str.indexOf(".html"));
        result = result.replace("item/", "");
        result = result.replace(".html", "");
        return result;
        //https://ru.aliexpress.com/item/32861343680.html
    }

}
