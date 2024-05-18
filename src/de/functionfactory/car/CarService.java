package de.functionfactory.car;

public class CarService {
    private final CarDAO carDAO = new CarDAO();

    public Car[] getAllCars() {
        return carDAO.getAllCars();
    }

    public Car getCar(String regNumber) {
        for(Car car : getAllCars()) {
            if(regNumber.equals(car.getRegNumber())) {
                return car;
            }
        }
        throw new IllegalStateException(String.format("Car with reg %s not found", regNumber));
    }

    public Car[] getAllElectricCars() {
        Car[] cars = carDAO.getAllCars();

        if(cars.length == 0) {
            return new Car[0];
        }

        int numberOfElectricCars = 0;

        for (Car car : cars) {
            if(car.isElectric()) {
                numberOfElectricCars++;
            }
        }

        if(numberOfElectricCars == 0) {
            return new Car[0];
        }

        Car[] electricCars = new Car[numberOfElectricCars];

        int index = 0;

        for (int i = 0; i < cars.length; i++) {
            if(cars[i].isElectric()) {
                electricCars[index++] = cars[i];
            }
        }
        return electricCars;
    }
}
