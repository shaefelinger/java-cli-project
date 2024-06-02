package de.functionfactory.booking;

import de.functionfactory.car.Car;
import de.functionfactory.car.CarService;
import de.functionfactory.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class CarBookingService {
    private final CarBookingDAO carBookingDAO;
    private final CarService carService;

    public CarBookingService(CarBookingDAO carBookingDAO, CarService carService) {
        this.carBookingDAO = carBookingDAO;
        this.carService = carService;
    }

    public UUID bookCar(User user, String regNumber) {
        Car[] availableCars = getAvailableCars();

        if (availableCars.length == 0) {
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

    public Car[] getUserBookedCars(UUID userId) {
        CarBooking[] carBookings = carBookingDAO.getCarBookings();

        int numberOfBookingsForUser = 0;

        for (CarBooking booking : carBookings) {
            if (booking != null && booking.getUser().getUuid().equals(userId)) {
                numberOfBookingsForUser++;
            }
        }

        if (numberOfBookingsForUser == 0) {
            return new Car[0];
        }

        Car[] userCars = new Car[numberOfBookingsForUser];

        int index = 0;

        for (CarBooking carBooking : carBookings) {
            if (carBooking != null && carBooking.getUser().getUuid().equals(userId)) {
                userCars[index++] = carBooking.getCar();
            }
        }
        return userCars;
    }

    public Car[] getAvailableCars() {
        return getCars(carService.getAllCars());
    }

    public Car[] getAvailableElectricCars() {
        return getCars(carService.getAllElectricCars());
    }

    private Car[] getCars(Car[] cars) {
        if (cars.length == 0) {
            return new Car[0];
        }

        CarBooking[] carBookings = carBookingDAO.getCarBookings();

        if (carBookings.length == 0) {
            return cars;
        }

        int availableCarsCount = 0;

        for (Car car : cars) {
            boolean booked = false;
            for (CarBooking carBooking : carBookings) {
                if (carBooking == null || !carBooking.getCar().equals(car)) {
                    continue;
                }
                booked = true;
            }
            if(!booked) {
                ++availableCarsCount;
            }
        }

        Car[] availableCars = new Car[availableCarsCount];
        int index = 0;

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
                availableCars[index++] = car;
            }
        }
        return availableCars;
    }

    public CarBooking[] getBookings() {
        CarBooking[] carBookings = carBookingDAO.getCarBookings();

        int numberOfBookings = 0;

        for (CarBooking booking : carBookings) {
            if (booking != null) {
                ++numberOfBookings;
            }
        }

        CarBooking[] bookings = new CarBooking[numberOfBookings];

        int index = 0;
        for (CarBooking bk : carBookings) {
            if (bk != null) {
                bookings[index++] = bk;
            }
        }

        return bookings;
    }
}
