package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

    private static final HotelResource INSTANCE = new HotelResource();

    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();

    private HotelResource() { }

    public static HotelResource getInstance() {
        return INSTANCE;
    }

    public void createCustomer(String firstName, String lastName, String email) {
        customerService.addCustomer(firstName, lastName, email);
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut, boolean freeOnly) {
        return reservationService.findRooms(checkIn, checkOut, freeOnly);
    }

    public Collection<IRoom> getRecommendedRooms(Date checkIn, Date checkOut, int days) {
        return reservationService.getRecommendedRooms(checkIn, checkOut, days);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkIn, Date checkOut) {
        Customer customer = customerService.getCustomer(customerEmail);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }
        return reservationService.reserveRoom(customer, room, checkIn, checkOut);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = customerService.getCustomer(customerEmail);
        if (customer == null) {
            return null;
        }
        return reservationService.getCustomersReservation(customer);
    }
}
