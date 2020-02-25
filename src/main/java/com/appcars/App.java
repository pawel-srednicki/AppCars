package com.appcars;

import com.appcars.service.MenuService;

public class App 
{
    public static void main( String[] args ) {

        final String jsonFilename = "cars_store.json";
        new MenuService(jsonFilename).mainMenu();

    }
}
