package ui;

import api.AdminResource;
import model.Room;
import model.RoomType;

public class TestDataLoader {
    public static void loadTestRooms() {
        AdminResource adminResource = AdminResource.getInstance();
        adminResource.addRoom(new Room("A-21", 1200.0, RoomType.SINGLE));
        adminResource.addRoom(new Room("B-22", 2800.0, RoomType.DOUBLE));
        adminResource.addRoom(new Room("FF-00", 0.0, RoomType.SINGLE)); // free room 
    }
}
