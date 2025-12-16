package ui;

public class HotelApplication {
    public static void main(String[] args) {
        TestDataLoader.loadTestRooms();
        MainMenu.start();
    }
}
