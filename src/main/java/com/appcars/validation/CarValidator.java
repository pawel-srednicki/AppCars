package com.appcars.validation;

import com.appcars.model.Car;
import com.appcars.model.enums.Color;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CarValidator {
    private final Map<String, String> errors = new HashMap<>();

    public Map<String, String> validate(Car car) {

        errors.clear();

        if (car == null) {
            errors.put("car", "car object is not correct");
        }

        if (!isModelValid(car)) {
            errors.put("model", "car model is not correct");
        }

        if (!isPriceValid(car)) {
            errors.put("price", "price is not correct");
        }

        if (!isMileageValid(car)) {
            errors.put("mileage", "mileage is not correct");
        }

        if (!isColorValid(car)) {
            errors.put("color", "color is not correct");
        }

        if (!areComponentsValid(car)) {
            errors.put("components", "components are not correct");
        }

        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    private boolean isModelValid(Car car) {
        return car.getModel() != null && car.getModel().matches("[A-Z ]+");
    }

    private boolean isPriceValid(Car car) {
        return car.getPrice() != null && car.getPrice().compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean isMileageValid(Car car) {
        return car.getMileage() > 0;
    }

    private boolean isColorValid(Car car) {
        return Arrays.stream(Color.values()).anyMatch(c -> c.equals(car.getColor()));
    }

    private boolean areComponentsValid(Car car) {
        return car.getComponents().stream().allMatch(c -> c.matches("[A-Z ]+"));
    }
}
