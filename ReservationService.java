package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {

    private static final ReservationService INSTANCE = new ReservationService();
    private final Map<String, IRoom> roomMap = new HashMap<>();
    private final Collection<Reservation> reservations = new ArrayList<>();

    private ReservationService() { }

    public static ReservationService getInstance() {
        return INSTANCE;
    }

    public void addRoom(IRoom room) {
        roomMap.put(room.getRoomNumber(), room);
    }

    public IRoom getRoom(String roomNumber) {
        return roomMap.get(roomNumber);
    }

    public Reservation reserveRoom(Customer customer, IRoom room,
                                   Date checkInDate, Date checkOutDate) {
        Reservation reservation =
                new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = new ArrayList<>(roomMap.values());

        for (Reservation reservation : reservations) {
            if (datesOverlap(checkInDate, checkOutDate,
                    reservation.getCheckInDate(),
                    reservation.getCheckOutDate())) {
                availableRooms.remove(reservation.getRoom());
            }
        }
        return availableRooms;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate, boolean freeOnly) {
        Collection<IRoom> availableRooms = new ArrayList<>();

        for (IRoom room : roomMap.values()) {

            if (freeOnly && room.getRoomPrice() > 0) {
                continue;
            }

            boolean isAvailable = true;

            for (Reservation reservation : reservations) {
                if (reservation.getRoom().equals(room) &&
                        datesOverlap(checkInDate, checkOutDate,
                                reservation.getCheckInDate(),
                                reservation.getCheckOutDate())) {
                    isAvailable = false;
                    break;
                }
            }

            if (isAvailable) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Collection<IRoom> getRecommendedRooms(Date checkInDate,
                                                 Date checkOutDate,
                                                 int days) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(checkInDate);
        calendar.add(Calendar.DATE, days);
        Date newCheckIn = calendar.getTime();

        calendar.setTime(checkOutDate);
        calendar.add(Calendar.DATE, days);
        Date newCheckOut = calendar.getTime();

        return findRooms(newCheckIn, newCheckOut);
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        Collection<Reservation> customerReservations = new ArrayList<>();

        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }

    public Collection<IRoom> getAllRooms() {
        return roomMap.values();
    }

    public void printAllReservations() {
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    private boolean datesOverlap(Date start1, Date end1,
                                 Date start2, Date end2) {
        return start1.before(end2) && end1.after(start2);
    }
}
