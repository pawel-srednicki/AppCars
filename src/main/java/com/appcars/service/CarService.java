package com.appcars.service;

import com.appcars.converters.CarsJsonConverter;
import com.appcars.exceptions.MyException;
import com.appcars.model.Car;
import com.appcars.model.enums.Color;
import com.appcars.service.enums.SortingType;
import com.appcars.validation.CarValidator;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/*czy musi byc public, zeby testowac??*/public class CarService {

    private Set<Car> cars;

    public CarService(String filename) {
        cars = getCars(filename);
    }

    private Set<Car> getCars(String filename) {

        CarValidator carValidator = new CarValidator();

        return new CarsJsonConverter(filename)
                .fromJson()
                .orElseThrow(() -> new MyException("CARS SERVICE - FROM JSON EXCEPTION"))
                .stream()
                .filter(car -> {
                    carValidator.validate(car).forEach((k, v) -> System.out.println(k + " - " + v));
                    return !carValidator.hasErrors();
                })
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return cars
                .stream()
                .map(Car::toString)
                .collect(Collectors.joining("\n"));
    }

    public List<Car> sort(SortingType sortingType, boolean descending) {

        if (sortingType == null) {
            throw new MyException("Sorting type is null");
        }

        Stream<Car> carStream = null;

        switch (sortingType) {
            case COLOR:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getColor));
            case MODEL:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getModel));
            case PRICE:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getPrice));
            case MILEAGE:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getMileage));
        }

        List<Car> cars = carStream.collect(Collectors.toList());

        if (descending) {
            Collections.reverse(cars);
        }

        return cars;

    }

    public Set<Car> mileageGreaterThan(int mileage) {
        return cars
                .stream()
                .filter(c -> c.getMileage() > mileage)
                .collect(Collectors.toSet());
    }

    public Map<String, List<Car>> groupedByComponents() {
        return cars
                .stream()
                .flatMap(car -> car.getComponents().stream())
                .distinct()
                .collect(Collectors.toMap(
                        component -> component,
                        component -> cars.stream().filter(car -> car.getComponents().contains(component)).collect(Collectors.toList())
                ))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(e -> e.getValue().size(), Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue(),
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }

    // kiedy zakladamy ze dla danego modelu bierzemy tylko jeden samochod
    /*public Map<String, Car> modelAndBiggestPriceModel() {
        return cars
                .stream()
                .collect(Collectors.groupingBy(Car::getModel, Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Car::getPrice)), carOp -> carOp.orElseThrow(() -> new MyException("EX")))));
    }*/

    public Map<String, List<Car>> modelAndBiggestPriceModel() {
        return cars
                .stream()
                .collect(Collectors.groupingBy(
                            Car::getModel,
                            Collectors.collectingAndThen(
                                    Collectors.groupingBy(Car::getPrice),
                                    map -> map.entrySet()
                                            .stream()
                                            .max(Comparator.comparing(Map.Entry::getKey))
                                            .orElseThrow(() -> new MyException("EX"))
                                            .getValue()
                                )
                            )
                );
    }

    public Map<Color, Long> groupedByColor() {
        return cars
                .stream()
                .map(car -> car.getColor())
                .distinct()
                .collect(Collectors.toMap(
                        color -> color,
                        color -> cars.stream().filter(car -> car.getColor() == color).count()
                ))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue(),
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }

    Set<Car> sortComponent() {
        return cars
                .stream()
                // .peek(car -> car.setComponents(car.getComponents().stream().sorted().collect(Collectors.toCollection(() -> new LinkedHashSet<>()))))
                .peek(car -> car.setComponents(car.getComponents().stream().sorted().collect(Collectors.toList())))
                .collect(Collectors.toSet());
    }

    // na YT film o kolektorach obejrzec i dla BigDecimal statystyke zrobic
    void priceAndMileageSummary() {
        //int maxMileage = cars.stream().mapToInt(Car::getMileage).max().getAsInt();
        int maxMileage = cars.stream().map(Car::getMileage).reduce(0, (c1, c2) -> c1 > c2 ? c1 : c2);
        System.out.println("Mileage statistics:");
        System.out.println("Max mileage: " + maxMileage);

        //int minMileage = cars.stream().mapToInt(Car::getMileage).min().getAsInt();
        int minMileage = cars.stream().map(Car::getMileage).reduce(maxMileage, (c1, c2) -> c1 < c2 ? c1 : c2);
        System.out.println("Min mileage: " + minMileage);

        double averageMileage = cars.stream().mapToDouble(Car::getMileage).average().getAsDouble();
        System.out.println("Average mileage: " + averageMileage);

    }

    public Set<Car> carsFromRangePrice(BigDecimal a, BigDecimal b) {
        return cars
                .stream()
                .filter(car -> car.getPrice().compareTo(a) >= 0 && car.getPrice().compareTo(b) <= 0)
                // .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Car::getModel))));
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public List<Car> maxPriceCars() {
        return cars
                .stream()
                .collect(Collectors.groupingBy(Car::getPrice))
                .entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getKey))
                .orElseThrow(() -> new MyException("No cars with max price"))
                .getValue();
    }
}


