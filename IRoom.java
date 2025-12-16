package model;

public interface IRoom {
    String getRoomNumber();
    RoomType getRoomType();
    Double getRoomPrice();
    boolean isFree();
}
