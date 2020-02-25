package com.appcars.service;

import com.appcars.exceptions.MyException;
import com.appcars.service.enums.SortingType;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

class UserDataService {

    private Scanner sc = new Scanner(System.in);

    int getInt(String message) {
        System.out.println(message);

        String text = sc.nextLine();
        if (!text.matches("\\d+")) {
            throw new MyException("INT VALUE IS NOT CORRECT: " + text);
        }

        return Integer.parseInt(text);
    }

    SortingType getSortingType() {

        AtomicInteger counter = new AtomicInteger(1);

        SortingType[] sortingTypes = SortingType.values();
        Arrays.stream(sortingTypes).forEach(sortingType -> {
            System.out.println(counter.getAndIncrement() + ". " + sortingType.toString().toLowerCase());
        });
        System.out.println("Enter sorting type");
        String text = sc.nextLine();

        if (!text.matches("[1-" + sortingTypes.length + "]")) {
            throw new MyException("INT VALUE IS NOT CORRECT: " + text);
        }
        return sortingTypes[Integer.parseInt(text) - 1];
    }

    boolean getBoolean(String message) {

        System.out.println(message + "[y/n]?");
        return sc.nextLine().equals("y");
    }

    void close() {
        if (sc != null) {
            sc.close();
            sc = null;
        }
    }

}
