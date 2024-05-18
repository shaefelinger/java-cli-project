package de.functionfactory.booking;

import de.functionfactory.car.Car;
import de.functionfactory.car.CarService;
import de.functionfactory.user.UserService;

import java.time.LocalDateTime;
import java.util.UUID;

public class CarBookingDAO {
    public static final CarBooking[] carBookings;

    static {
        CarService carService = new CarService();
        UserService userService = new UserService();
        carBookings = new CarBooking[10];
    }

    public CarBooking[] getCarBookings() {
        return carBookings;
    }

    public void book(CarBooking carBooking) {
        int nextFreeIndex = -1;

        for (int i = 0; i < carBookings.length; i++) {
            if (carBookings[i] == null) {
                nextFreeIndex = i;
                break;
            }
        }

        if (nextFreeIndex > -1) {
            carBookings[nextFreeIndex] = carBooking;
            return;
        }

        CarBooking[] biggerBookingsArray = new CarBooking[carBookings.length + 10];

        for (int i = 0; i < carBookings.length; i++) {
            biggerBookingsArray[i] = carBookings[i];
        }

        biggerBookingsArray[carBookings.length] = carBooking;
    }


}
