package ui;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {

    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public static void start() {
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    findAndReserveRoom();
                    break;
                case "2":
                    seeMyReservations();
                    break;
                case "3":
                    createAccount();
                    break;
                case "4":
                    AdminMenu.start();
                    break;
                case "5":
                    running = false;
                    System.out.println("Thank you for using the application!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.print("Please select an option: ");
    }

    private static void findAndReserveRoom() {
        try {
            System.out.print("Enter Check-In Date (MM/dd/yyyy): ");
            Date checkIn = dateFormat.parse(scanner.nextLine());

            System.out.print("Enter Check-Out Date (MM/dd/yyyy): ");
            Date checkOut = dateFormat.parse(scanner.nextLine());

            System.out.print("Do you want only free rooms? (y/n): ");
            boolean freeOnly = scanner.nextLine().equalsIgnoreCase("y");
            

            Collection<IRoom> rooms =
                    hotelResource.findARoom(checkIn, checkOut, freeOnly);

            if (rooms.isEmpty()) {
                System.out.print("No rooms available. How many days out should we search? ");
                int days = Integer.parseInt(scanner.nextLine());

                rooms = hotelResource.getRecommendedRooms(checkIn, checkOut, days);

                if (rooms.isEmpty()) {
                    System.out.println("No recommended rooms found.");
                    return;
                }
            }

            for (IRoom room : rooms) {
                System.out.println(room);
            }

            System.out.print("Would you like to book a room? (y/n): ");
            if (!scanner.nextLine().equalsIgnoreCase("y")) {
                return;
            }

            System.out.print("Enter your email: ");
            String email = scanner.nextLine();

            System.out.print("Enter room number: ");
            String roomNumber = scanner.nextLine();

            IRoom selectedRoom = rooms.stream()
                    .filter(r -> r.getRoomNumber().equals(roomNumber))
                    .findFirst()
                    .orElse(null);

            if (selectedRoom == null) {
                System.out.println("Invalid room number.");
                return;
            }

            Reservation reservation =
                    hotelResource.bookARoom(email, selectedRoom, checkIn, checkOut);

            System.out.println("Reservation successful:");
            System.out.println(reservation);

        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

    private static void seeMyReservations() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        Collection<Reservation> reservations =
                hotelResource.getCustomersReservations(email);

        if (reservations == null || reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    private static void createAccount() {
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        try {
            hotelResource.createCustomer(firstName, lastName, email);
            System.out.println("Account created successfully!");
        } catch (Exception e) {
            System.out.println("Error creating account.");
        }
    }
}
