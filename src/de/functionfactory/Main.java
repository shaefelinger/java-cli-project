package de.functionfactory;

import de.functionfactory.booking.CarBooking;
import de.functionfactory.booking.CarBookingDAO;
import de.functionfactory.booking.CarBookingService;
import de.functionfactory.car.Car;
import de.functionfactory.car.CarDAO;
import de.functionfactory.car.CarService;
import de.functionfactory.user.User;
import de.functionfactory.user.UserDAO;
import de.functionfactory.user.UserService;
import de.functionfactory.user.UserFileDataAccessService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        UserDAO userDao = new UserFileDataAccessService();

        CarDAO carDAO = new CarDAO();
        UserService userService = new UserService(userDao);
        CarService carService = new CarService(carDAO);

        CarBookingDAO carBookingDAO = new CarBookingDAO();
        CarBookingService carBookingService = new CarBookingService(carBookingDAO, carService);

        Scanner scanner = new Scanner(System.in);

        System.out.println("MCBS - Mega Car Booking System");

        boolean keepOnLooping = true;

        while (keepOnLooping) {
            displayMenu();
            String selection = scanner.nextLine();

            switch (selection) {
                case "1" -> {
                    bookCar(carBookingService, userService, scanner);
                }

                case "2" -> displayAllUserBookedCars(userService, carBookingService, scanner);

                case "3" -> viewAllBookings(carBookingService);

                case "4" -> {
                    displayAvailableCars(false, carBookingService);
                }

                case "5" -> {
                    displayAvailableCars(true, carBookingService);
                }
                case "6" -> {
                    displayAllUsers(userService);
                }
                case "7" -> {
                    System.out.println("Goodbye");
                    keepOnLooping = false;
                }
                default -> System.out.println(selection + " is not a valid option ❌");
            }
        }
    }

    private static void displayAllUsers(UserService userService) {
        List<User> users = userService.getUsers();

        if (users.isEmpty()) {
            System.out.println("No Users in the system");
        }

        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    private static void bookCar(CarBookingService carBookingService, UserService userService, Scanner scanner) {

        displayAvailableCars(false, carBookingService);

        System.out.println("➡️ select car reg number");
        String regId = scanner.nextLine();

        displayAllUsers(userService);

        System.out.println("➡️ select user id");
        String userId = scanner.nextLine();

        try {
            User user = userService.getUserById(UUID.fromString(userId));
            if (user == null) {
                System.out.println("❌ No user found with id " + userId);
            } else {
                UUID bookingID = carBookingService.bookCar(user, regId);
                String confirmationMessage = """
                        Successfully booked car with reg number %s for user %s
                        Booking ref: %s
                        """.formatted(regId, user, bookingID);
                System.out.println(confirmationMessage);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void displayAllUserBookedCars(UserService userService, CarBookingService carBookingService, Scanner scanner) {

        displayAllUsers(userService);

        System.out.println("➡️ select user id");
        String userId = scanner.nextLine();

        User user = userService.getUserById(UUID.fromString(userId));

        if (user == null) {
            System.out.println("❌ No user found with id " + userId);
            return;
        }

        List<Car> userBookedCars = carBookingService.getUserBookedCars(UUID.fromString(userId));

        if (userBookedCars.isEmpty()) {
            System.out.printf("❌ user %s has no cars booked", user);
            return;
        }

        for (Car userBookedCar : userBookedCars) {
            System.out.println(userBookedCar);
        }
    }

    private static void displayAvailableCars(boolean isElectric, CarBookingService carBookingService) {
        List<Car> availableCars = isElectric ? carBookingService.getAvailableElectricCars() : carBookingService.getAvailableCars();

        if (availableCars.isEmpty()) {
            System.out.println("No Available Cars for renting");
            return;
        }

        for (Car car : availableCars) {
            System.out.println(car);
        }
    }

    private static void viewAllBookings(CarBookingService carBookingService) {
        List<CarBooking> allCarBookings = carBookingService.getBookings();

        if (allCarBookings.isEmpty()) {
            System.out.println("No bookings yet");
        }

        for (CarBooking booking : allCarBookings) {
            System.out.println(booking);
        }
    }

    private static void displayMenu() {
        System.out.println();
        System.out.println("1️⃣ - Book Car");
        System.out.println("2️⃣ - View All User Booked Cars");
        System.out.println("3️⃣ - View All Bookings");
        System.out.println("4️⃣ - View Available Cars");
        System.out.println("5️⃣ - View Available Electric Cars");
        System.out.println("6️⃣ - View all users");
        System.out.println("7️⃣ - Exit");
        System.out.println("YOUR SELECTION? ");
    }
}
