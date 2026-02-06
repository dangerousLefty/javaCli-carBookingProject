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

        //should be removed later
        UserDAO userDAO = new UserDAO();
        CarDAO carDAO = new CarDAO();
        BookingDAO bookingDAO = new BookingDAO();

        UserService userService = new UserService(userDAO);
        CarService carService = new CarService(carDAO);
        BookingService bookingService = new BookingService(bookingDAO);



        Scanner scanner = new Scanner(System.in);
        int choice = 0;
//        for (int i = 0; i < 11; i++){
//            System.out.println(UUID.randomUUID().toString());
//        }
        boolean flag = false;
        while (!flag){
            User tempUser = null;
            Car tempCar = null;
            System.out.println("Hi! Welcome to your car booking app. ");
            System.out.println("Please select one of the options to get started: ");
            System.out.println("1\uFE0F⃣ - Book Car");
            System.out.println("2\uFE0F⃣ - View All User Booked Cars");
            System.out.println("3\uFE0F⃣ - View All Bookings");
            System.out.println("4\uFE0F⃣ - View Available Cars");
            System.out.println("5\uFE0F⃣ - View Available Electric Cars");
            System.out.println("6\uFE0F⃣ - View all users");
            System.out.println("7\uFE0F⃣ - Exit");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 7){
                    throw new Exception();
                }

                switch (choice){
                    case 1:
                        //System.out.println("Book car");
                        System.out.println("Which user is booking a vehicle? ");
                        userService.getUserList();
                        tempUser = userService.getUser(scanner.nextLine());

                        System.out.println("Which vehicle would user like to rent? ");
                        carService.getCarList();
                        tempCar = carService.getCar(scanner.nextLine());
                        //tempCar points to the same object that lives inside the carList

                        UUID bookingId = UUID.randomUUID();
                        Booking temp = new Booking(
                                bookingId,
                                tempUser,
                                tempCar,
                                LocalDateTime.now()
                                );

                        if (bookingService.canAddBooking()){
                            bookingService.addBooking(temp);
                            tempCar.setBooked(true);
                            System.out.println("✅ Booking is created successfully!");
                            System.out.println("Booking id: " + bookingId);
                        }
                        else {
                            System.out.println("All bookings full! ");
                        }


                        break;

                    case 2:
                        //System.out.println("View All User Booked Cars");
                        System.out.println("Pick a user to inspect below: ");
                        userService.getUserList();
                        tempUser = userService.getUser(scanner.nextLine());
                        //User tempUser = userService.getUser(scanner.nextLine());

                        bookingService.findBookingByUserId(tempUser.getUserIdString());
                        //System.out.println(tempUser.toString());
                        break;

                    case 3:
                        //TODO: View All Bookings
                        bookingService.getBookings();
                        break;

                    case 4:
                        //TODO: View Available cars
                        //System.out.println("View Available cars");
                        carService.getCarList();
                        break;

                    case 5:
                        //TODO: View Available Electric Cars
                        //System.out.println("View Available Electric Cars");
                        carService.getEvCarList();
                        break;

                    case 6:
                        //TODO: View all users
                        //System.out.println("View all users");
                        //for (User u : userDAO.getUsers()){
                        for (User u : userService.getUsers()){
                            System.out.println(u.toString());
                        }
                        break;

                    case 7:
                        flag = true;
                }

            }
            catch (NoSuchElementException e){
                System.out.println(e.getMessage());
            }
            catch (Exception e){
                System.out.println("Invalid option ❌ Try again");
            }

        }

    }
}
