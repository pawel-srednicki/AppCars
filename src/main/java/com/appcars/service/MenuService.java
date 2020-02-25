package com.appcars.service;

import com.appcars.exceptions.MyException;
import com.appcars.service.enums.SortingType;

import java.math.BigDecimal;
import java.util.Scanner;

public class MenuService {

    private final String filename;
    private final CarService carService;
    private final UserDataService userDataService;

    public MenuService(String filename) {
        this.filename = filename;
        carService = new CarService(filename);
        userDataService = new UserDataService();
    }

    public void mainMenu() {
        while (true) {
            try {
                int option = userDataService.getInt("Ktora opcja?");
                switch (option) {
                    case 1:
                        option1();
                        break;
                    case 2:
                        option2();
                        break;
                    case 3:
                        option3();
                        break;
                    case 4:
                        option4();
                        break;
                    case 5:
                        option5();
                        break;
                    case 6:
                        option6();
                        break;
                    case 7:
                        option7();
                        break;
                    case 8:
                        option8();
                        break;
                    case 9:
                        option9();
                        break;
                    case 10:
                        option10();
                        break;

                }
                userDataService.close();
                return;
            } catch (MyException e) {
                System.err.println(e.getExceptionMessage());
            }

        }
    }

    private void option1() {
        System.out.println(carService.toString());
    }

    private void option2() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Przebieg wiekszy niz:");
        int przebieg = sc.nextInt();
        System.out.println(carService.mileageGreaterThan(przebieg));
    }

    private void option3() {
        System.out.println(carService.groupedByComponents());
    }

    private void option4() {
        System.out.println(carService.groupedByColor());
    }

    private void option5() {
        System.out.println(carService.sortComponent());
    }

    private void option6() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter first price:");
        BigDecimal a = sc.nextBigDecimal();
        System.out.println("Enter second price:");
        BigDecimal b = sc.nextBigDecimal();

        if(a.compareTo(b) > 0){
            throw new IllegalArgumentException("First price should be lower than second");
        }

        carService.carsFromRangePrice(a, b).forEach(System.out::println);
    }

    private void option7() {
        carService.priceAndMileageSummary();
    }

    private void option8() {
        System.out.println(carService.maxPriceCars());
    }

    private void option9() {
        SortingType sortingType = userDataService.getSortingType();
        boolean descending = userDataService.getBoolean("Descending ");
        carService.sort(sortingType, descending).forEach(System.out::println);
    }

    private void option10() {
        System.out.println(carService.modelAndBiggestPriceModel());
    }

}
