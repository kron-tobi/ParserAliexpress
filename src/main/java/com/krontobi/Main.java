package com.krontobi;

public class Main {

    public static void main(String[] args) {
        Executor item = new Executor();
        item.selectParsingPages("product.url");
        item.sendProductInDataBase();
    }

}
