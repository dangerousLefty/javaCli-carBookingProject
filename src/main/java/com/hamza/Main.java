package com.hamza;

import com.hamza.booking.*;
import com.hamza.car.*;
import com.hamza.user.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        UserDAO userDAO = new UserFileDataAccessService();
        CarDAO carDAO = new CarListDataAccessService();
        BookingDAO bookingDao = new BookingFileDataAccessService();

        UserService userService = new UserService(userDAO);
        CarService carService = new CarService(carDAO);
        BookingService bookingService = new BookingService(bookingDao, userService, carService);


        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean flag = false;
        while (!flag) {


            try {
                welcomeMessage();
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 8) {
                    throw new Exception("Invalid option ❌ Try again");
                }


                switch (choice) {
                    case 1:
                        bookCar(userService, carService, bookingService, scanner);
                        break;

                    case 2:
                        viewUserBookedCars(userService, bookingService, scanner);
                        break;

                    case 3:
                        viewAllBookings(bookingService);
                        break;

                    case 4:
                        viewAllBookings(bookingService);
                        if (bookingService.getCurrentNumberOfBookings() > 0) {
                            deleteBookings(bookingService, scanner);
                        }

                        break;

                    case 5:
                        viewAllCars(carService);
                        break;

                    case 6:
                        viewCarsByType(carService, CarType.EV);
                        break;

                    case 7:
                        viewUsers(userService);
                        break;

                    case 8:
                        flag = true;
                }

            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

    }

    private static void welcomeMessage() {
        System.out.println("🚗 Welcome to Car Booking System! 🚗");
        System.out.println("=====================================\n");
        System.out.println("Please select one of the options to get started: ");
        System.out.println("1️⃣ - Book Car");
        System.out.println("2️⃣ - View All User Booked Cars");
        System.out.println("3️⃣ - View All Bookings");
        System.out.println("4️⃣ - Delete Bookings");
        System.out.println("5️⃣ - View Available Cars");
        System.out.println("6️⃣ - View Available Electric Cars");
        System.out.println("7️⃣ - View all users");
        System.out.println("8️⃣ - Exit");
    }

    private static void bookCar(UserService userService, CarService carService, BookingService bookingService, Scanner scanner) throws Exception {
        System.out.println("Which user is booking a vehicle? ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy");
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        viewUsers(userService);
        UUID userId = UUID.fromString(scanner.nextLine());

        System.out.println("Which vehicle would user like to rent? ");
        List<Car> carList = carService.getAvailableCars();
        printList(carList);
        UUID carId = UUID.fromString(scanner.nextLine());

        System.out.println("What is the start date for your reservation? (mm dd yyyy)");
        String startDateStr = scanner.nextLine();

        System.out.println("What is the end date for your reservation? (mm dd yyyy)");
        String endDateStr = scanner.nextLine();

        try {
            startDate = LocalDateTime.of(LocalDate.parse(startDateStr, formatter), LocalTime.of(12, 00));
            endDate = LocalDateTime.of(LocalDate.parse(endDateStr, formatter), LocalTime.of(12, 00));
        } catch (DateTimeParseException e) {
            throw e;
        }

        boolean result = bookingService.bookCar(userId, carId, startDate, endDate, formatter);
        if (result) {
            System.out.println("✅ Booking is created successfully!");
        } else {
            System.out.println("❌ There was an issue saving the booking, please try again");
        }
    }

    private static void viewUserBookedCars(UserService userService, BookingService bookingService, Scanner scanner) {

        System.out.println("Which user's bookings would you like to view?");
        viewUsers(userService);
        User tempUser = userService.getUser(UUID.fromString(scanner.nextLine()));
        List<Booking> bookingList = bookingService.getUserBookings(tempUser.getUserID());
        if (bookingList.size() > 0) {
            System.out.println("Here are the list of Bookings in the system");
            System.out.println("-------------------------------------------");
        } else {
            System.out.println("❌ No bookings found!");
        }
        printList(bookingList);
    }

    private static void viewAllBookings(BookingService bookingService) {
        if (bookingService.getCurrentNumberOfBookings() == 0) {
            System.out.println("❌ No bookings found!");
            return;
        }

        List<Booking> bookingList = bookingService.getBookings();
        System.out.println("Here are the list of Bookings in the system");
        System.out.println("-------------------------------------------");
        printList(bookingList);
    }

    private static void viewAllCars(CarService carService) {
        System.out.println("Here are the available cars that i found");
        List<Car> carList = carService.getAvailableCars();
        printList(carList);
    }

    private static void viewCarsByType(CarService carService, CarType type) {
        System.out.println("Here are the " + type + " cars that i found");
        List<Car> carList = carService.getAvailableCarsByType(type);
        printList(carList);
    }

    private static void deleteBookings(BookingService bookingService, Scanner scanner) {

        System.out.println("Which booking would you like to delete? ");
        boolean isSuccessful = bookingService.deleteBooking(UUID.fromString(scanner.nextLine()));
        if (isSuccessful) {
            System.out.println("✅ Booking deleted successfully");
        } else {
            System.out.println("❌ Booking does not exist");
        }
    }

    private static void viewUsers(UserService userService) {
        printList(userService.getUsers());
    }

    private static <T> void printList(List<T> arr) {
        for (T item : arr) {
            if (item != null) {
                System.out.println(item);
            }
        }
    }
}