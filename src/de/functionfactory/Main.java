package de.functionfactory;

import de.functionfactory.booking.CarBooking;
import de.functionfactory.booking.CarBookingService;
import de.functionfactory.car.Car;
import de.functionfactory.car.CarService;
import de.functionfactory.user.User;
import de.functionfactory.user.UserService;

import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserService();
        CarService carService = new CarService();
        System.out.println("MCBS - Mega Car Booking System");

        boolean keepOnLooping = true;

        while (keepOnLooping) {
            displayMenu();
            Scanner scanner = new Scanner(System.in);
            String selection = scanner.next();

//            System.out.println("You selected " + selection);

            switch (selection) {
                case "1" -> {
                    displayAllUsers(userService);
                    displayAvailableCars(false);
                    bookCar();
                }

                case "2" -> displayAllUserBookedCars();

                case "3" -> viewAllBookings();

                case "4" -> {
                    displayAvailableCars(false);
                }

                case "5" -> {
                    displayAvailableCars(true);
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
        User[] users = userService.getUsers();

        if (users.length == 0) {
            System.out.println("No Users in the system");
        }

        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    private static void bookCar() {
        Scanner scanner = new Scanner(System.in);
        CarBookingService carBookingService = new CarBookingService();
        UserService userService = new UserService();

        System.out.println("User-Id? ");
        UUID userId = UUID.fromString(scanner.nextLine());


        System.out.println("regId-Id? ");
        String regId = scanner.nextLine();

        try {
            User user = userService.getUserById(userId);
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

    private static void displayAllUserBookedCars() {
        UserService userService = new UserService();
        CarBookingService carBookingService = new CarBookingService();

        Scanner scanner = new Scanner(System.in);

        displayAllUsers(userService);

        System.out.println("➡️ select user id");
        String userId = scanner.nextLine();

        User user = userService.getUserById(UUID.fromString(userId));

        if (user == null) {
            System.out.println("❌ No user found with id " + userId);
            return;
        }

        Car[] userBookedCars = carBookingService.getUserBookedCars(UUID.fromString(userId));

        if (userBookedCars.length == 0) {
            System.out.printf("❌ user %s has no cars booked", user);
            return;
        }

        for (Car userBookedCar : userBookedCars) {
            System.out.println(userBookedCar);
        }
    }

    private static void displayAvailableCars(boolean isElectric) {
        CarBookingService carBookingService = new CarBookingService();
        Car[] availableCars = isElectric ? carBookingService.getAvailableElectricCars() : carBookingService.getAvailableCars();

        if (availableCars.length == 0) {
            System.out.println("No Available Cars for renting");
            return;
        }

        for (Car car : availableCars) {
            System.out.println(car);
        }
    }

    private static void viewAllBookings() {
        CarBookingService carBookingService = new CarBookingService();
        CarBooking[] allCarBookings = carBookingService.getBookings();

        if (allCarBookings.length == 0) {
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
