package com.hamza;
// TODO 1. create a new branch called initial-implementation
// TODO 2. create a package with your name. i.e com.franco and move this file inside the new package
// TODO 3. implement https://amigoscode.com/learn/java-cli-build/lectures/3a83ecf3-e837-4ae5-85a8-f8ae3f60f7f5

import com.hamza.booking.Booking;
import com.hamza.booking.BookingDAO;
import com.hamza.booking.BookingService;
import com.hamza.car.Car;
import com.hamza.car.CarDAO;
import com.hamza.car.CarService;
import com.hamza.car.CarType;
import com.hamza.user.User;
import com.hamza.user.UserDAO;
import com.hamza.user.UserService;
import com.hamza.util.DateInput;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        UserService userService = new UserService();
        CarService carService = new CarService();
        BookingService bookingService = new BookingService();
        DateInput dateInput = new DateInput();


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
                        bookCar(userService, carService, bookingService, dateInput ,scanner);
                        break;

                    case 2:
                        viewUserBookedCars(userService, bookingService, scanner);
                        break;

                    case 3:
                        viewAllBookings(bookingService);
                        break;

                    case 4:
                        viewAllBookings(bookingService);
                        if (bookingService.getNumOfBookings() > 0){
                            deleteBookings(bookingService, scanner);
                        }
                        break;

                    case 5:
                        viewAllCars(carService, 0);
                        break;

                    case 6:
                        viewAllCars(carService, 1);
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
        System.out.println("Hi! Welcome to your car booking app. ");
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

    private static void bookCar(UserService userService, CarService carService, BookingService bookingService, DateInput dateInput, Scanner scanner) throws Exception {
        LocalDate startDate = null;
        LocalDate endDate = null;
        System.out.println("Which user is booking a vehicle? ");

//        for (User u : userService.getUsers()) {
//            System.out.println(u);
//        }
        viewUsers(userService);
        //null or incorrect user input addressed
        User tempUser = userService.getUser(UUID.fromString(scanner.nextLine()));

        System.out.println("Which vehicle would user like to rent? ");


        Car[] list =  carService.getAvailableCars(0);
        carService.printCars(list);
        //null or incorrect user input addressed
        //tempCar points to the same object that lives inside the carList
        Car tempCar = carService.getCar(UUID.fromString(scanner.nextLine()));

        System.out.println("What is the start date for your reservation? (mm dd yyyy)");

        try {
            startDate = dateInput.parseDateFromInput(scanner.nextLine());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("What is the end date for your reservation? (mm dd yyyy)");

        try {
            endDate = dateInput.parseDateFromInput(scanner.nextLine());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        if (!dateInput.isStartDateBeforeEndDate(startDate, endDate)){
            throw new Exception("Start date must be before End date!");
        }

        //BigDecimal finalPrice = bookingService.calculatePrice(startDate, endDate, tempCar.getRentalRate());

        Booking newBooking = new Booking(
                tempUser,
                tempCar,
                startDate,
                endDate
        );
        bookingService.addBooking(newBooking);

    }

    private static void viewUserBookedCars(UserService userService, BookingService bookingService, Scanner scanner) {
        System.out.println("Which user's bookings would you like to view?");
        viewUsers(userService);
        User tempUser = userService.getUser(UUID.fromString(scanner.nextLine()));
        Booking[] list = bookingService.getUserBookings(tempUser.getUserID());

    }

    private static void viewAllCars(CarService carService, int i){
        carService.printCars(carService.getAvailableCars(i));
    }

    private static void viewAllBookings(BookingService bookingService) {
        bookingService.getBookings();
    }

    private static void deleteBookings(BookingService bookingService, Scanner scanner){
        System.out.println("Which booking would you like to delete? ");
       boolean isSuccessful =  bookingService.deleteBooking(UUID.fromString(scanner.nextLine()));
    }

    private static void viewUsers(UserService userService){
        for (User u : userService.getUsers()) {
            System.out.println(u.toString());
        }
    }


}