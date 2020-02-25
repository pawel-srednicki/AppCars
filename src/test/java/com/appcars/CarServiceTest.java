package com.appcars;

import com.appcars.model.Car;
import com.appcars.service.CarService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Matcher;

public class CarServiceTest {

    private CarService carService = null;

    @BeforeEach
    public void beforeEach(){
        carService = new CarService("cars_store_test.json");
    }

    @Test
    @DisplayName("CORRECT MILEAGE")
    public void test1(){

        var cars = carService.mileageGreaterThan(50000);
        Assertions.assertEquals(1, cars.size(), "TEST FAILED");
    }

    @Test
    @DisplayName("MAP HAS GOOD SIZE")
    public void test2(){

        var colorMap = carService.groupedByColor();
        MatcherAssert.assertThat(colorMap.size(), Matchers.is(3));
    }

    @Test
    @DisplayName("MAP HAS GOOD KEYS")
    public void test3(){

        var componentMap = carService.groupedByComponents();

        Assertions.assertAll(
                () -> {
                    MatcherAssert.assertThat(componentMap, Matchers.hasKey("TRACTION CONTROL"));
                    MatcherAssert.assertThat(componentMap, Matchers.hasKey("ABS"));
                    MatcherAssert.assertThat(componentMap, Matchers.hasKey("AIR CONDITION"));
                }
                );
    }

    @Test
    @DisplayName("CAR WITH MAX PRICE")
    public void test4(){

    }

}
