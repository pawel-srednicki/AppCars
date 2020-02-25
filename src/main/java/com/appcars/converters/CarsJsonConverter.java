package com.appcars.converters;

import com.appcars.model.Car;
//import com.appcars.service.CarService;

import java.util.List;

public class CarsJsonConverter extends JsonConverter<List<Car>> {
    public CarsJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
