package de.functionfactory.booking;

import de.functionfactory.car.Car;
import de.functionfactory.car.CarService;
import de.functionfactory.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CarBookingService {
    private final CarBookingDAO carBookingDAO;
    private final CarService carService;

    public CarBookingService(CarBookingDAO carBookingDAO, CarService carService) {
        this.carBookingDAO = carBookingDAO;
        this.carService = carService;
    }

    public UUID bookCar(User user, String regNumber) {
        List<Car> availableCars = getAvailableCars();

        if (availableCars.isEmpty()) {
            throw new IllegalStateException("No car available ");
        }

        for (Car availableCar : availableCars) {
            if (availableCar.getRegNumber().equals(regNumber)) {
                // book
                Car car = carService.getCar(regNumber);

                UUID bookingId = UUID.randomUUID();

                carBookingDAO.book(
                        new CarBooking(bookingId, car, user, LocalDateTime.now())
                );

                return bookingId;
            }
        }
        throw new IllegalStateException("car with reg-nr " + regNumber + " is already booked");
    }

    public List<Car> getUserBookedCars(UUID userId) {
        List<CarBooking> carBookings = carBookingDAO.getCarBookings();

        List<Car> userCars = new ArrayList<>();

        for (CarBooking carBooking : carBookings) {
            if (carBooking != null && carBooking.getUser().getUuid().equals(userId)) {
                userCars.add(carBooking.getCar());
            }
        }
        return userCars;
    }

    public List<Car> getAvailableCars() {
        return getCars(carService.getAllCars());
    }

    public List<Car> getAvailableElectricCars() {
        return getCars(carService.getAllElectricCars());
    }

    private List<Car> getCars(List<Car> cars) {
        if (cars.isEmpty()) {
            return Collections.emptyList();
        }

        List<CarBooking> carBookings = carBookingDAO.getCarBookings();

        if (carBookings.isEmpty()) {
            return cars;
        }

        List<Car> availableCars = new ArrayList<>();

        // populate available cars
        for (Car car : cars) {
            boolean booked = false;
            for (CarBooking carBooking : carBookings) {
                if (carBooking == null || !carBooking.getCar().equals(car)) {
                    continue;
                }
                booked = true;
            }
            if(!booked) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }

    public List<CarBooking> getBookings() {
        List<CarBooking> carBookings = carBookingDAO.getCarBookings();

        List<CarBooking> bookings = new ArrayList<>();

        for (CarBooking bk : carBookings) {
            if (bk != null) {
                bookings.add(bk);
            }
        }

        return bookings;
    }
}
