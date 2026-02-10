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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        UserService userService = new UserService();
        CarService carService = new CarService();
        BookingService bookingService = new BookingService();


        Scanner scanner = new Scanner(System.in);
        int choice = 0;
//        for (int i = 0; i < 11; i++){
//            System.out.println(UUID.randomUUID().toString());
//        }
        boolean flag = false;
        while (!flag) {
//            User tempUser = null;
//            Car tempCar = null;

            try {
                welcomeMessage();
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 8) {
                    throw new Exception();
                }

                switch (choice) {
                    case 1:
                        bookCar(userService, carService, bookingService, scanner);
                        break;

                    case 2:
                        viewUserBookedCars(userService, bookingService, scanner);
                        break;

                    case 3:
                        System.out.println("Here is the list of current bookings in the system: ");
                        viewAllBookings(bookingService);
                        break;

                    case 4:
                        //carService.getAvailableCars();
                        //TODO: Delete Bookings
                        System.out.println("Which booking would you like to delete? ");
                        int result = viewAllBookings(bookingService);
                        if (result > 0){deleteBookings(bookingService, scanner);}
                        break;

                    case 5:
                        carService.getAvailableCars();
                        break;

                    case 6:
                        carService.getAvailableEVcars();
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
                System.out.println("Invalid option ❌ Try again");
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

    private static void bookCar(UserService userService, CarService carService, BookingService bookingService, Scanner scanner) {
        //System.out.println("Book car");
        System.out.println("Which user is booking a vehicle? ");

        for (User u : userService.getUsers()) {
            System.out.println(u);
        }

        //null or incorrect user input addressed
        User tempUser = userService.getUser(UUID.fromString(scanner.nextLine()));

        //carService.getCarList();
        System.out.println("Which vehicle would user like to rent? ");

        //null or incorrect user input addressed
        carService.getAvailableCars();
        //null or incorrect user input addressed
        //tempCar points to the same object that lives inside the carList
        Car tempCar = carService.getCar(UUID.fromString(scanner.nextLine()));

        Booking newBooking = new Booking(
                tempUser,
                tempCar
        );
        bookingService.addBooking(newBooking);

    }

    private static void viewUserBookedCars(UserService userService, BookingService bookingService, Scanner scanner) {

        if (bookingService.getNumOfBookings() == 0) {
            System.out.println("❌ No bookings found");
        } else {
            System.out.println("Pick a user to inspect below: ");

            for (User u : userService.getUsers()) {
                System.out.println(u);
            }

            User tempUser = userService.getUser(UUID.fromString(scanner.nextLine()));
            //User tempUser = userService.getUser(scanner.nextLine());

            Booking[] list = bookingService.findBookingByUserId(tempUser.getUserID());
        }

    }

    private static int viewAllBookings(BookingService bookingService) {
        if (bookingService.getNumOfBookings() == 0) {
            System.out.println("❌ No bookings found");
            return 0;
        } else {
            for (Booking b : bookingService.getBookings()) {
                if (b == null){
                    continue;
                }
                System.out.println(b);
            }
        }
        return 1;
    }

    private static void deleteBookings(BookingService bookingService, Scanner scanner){
       boolean isSuccessful =  bookingService.deleteBooking(UUID.fromString(scanner.nextLine()));
    }

    private static void viewUsers(UserService userService){
        for (User u : userService.getUsers()) {
            System.out.println(u.toString());
        }
    }



}