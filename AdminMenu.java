package ui;

import api.AdminResource;
import model.Customer;
import model.IRoom;

import java.util.Collection;
import java.util.Scanner;

public class AdminMenu {

    private static final AdminResource adminResource = AdminResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);
    

    public static void start() {
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    seeAllCustomers();
                    break;
                case "2":
                    seeAllRooms();
                    break;
                case "3":
                    seeAllReservations();
                    break;
                case "4":
                    addARoom();
                    break;
                case "5":
                    running = false;
                    break;              
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n Admin Menu ");
        System.out.println("1. See all customers");
        System.out.println("2. See all rooms");
        System.out.println("3. See all reservations");
        System.out.println("4. Add a room");
        System.out.println("5. Back to Main Menu");
        //System.out.println("6. Load test data");
        System.out.print("Please select an option: ");
    }

    private static void seeAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    private static void seeAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
            return;
        }
        for (IRoom room : rooms) {
            System.out.println(room);
        }
    }

    private static void seeAllReservations() {
        adminResource.displayAllReservations();
    }

    private static void addARoom() {
        System.out.print("Enter room number: ");
        String roomNumber = scanner.nextLine();

        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter room type (SINGLE/DOUBLE): ");
        String typeInput = scanner.nextLine();
        typeInput = typeInput.toUpperCase();

        try {
            adminResource.addRoom(new model.Room(roomNumber, price, model.RoomType.valueOf(typeInput)));
            System.out.println("Room added successfully.");
        } catch (Exception e) {
            System.out.println("Error adding room. Please try again.");
        }
    }
}
