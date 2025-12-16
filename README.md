# Hotel-reservation-Application


A simple Java console application to manage hotel reservations using **OOP concepts**, **collections**, and **singleton services**.

---

## How to Run (VS Code)

1. Open VS Code and install the **Java Extension Pack**.  
2. Open the project folder.  
3. Make sure the source folder is `src/main/java`.  
4. Open `ui/HotelApplication.java` and click **Run Java**.

---

## How to Run (Command Line)

```bash
cd hotelreservationapp/src/main/java
javac $(find . -name "*.java")
java ui.HotelApplication
```

## Project Structure

- **model**: IRoom, Room, FreeRoom, RoomType, Customer, Reservation  
- **service**: CustomerService, ReservationService  
- **api**: HotelResource, AdminResource  
- **ui**: MainMenu, AdminMenu, HotelApplication  
- **util**: DateUtil (for parsing and formatting dates)  

---

## Features

- Create a customer account with **email validation**  
- Find available rooms and **book without double booking**  
- Optional filter for **free rooms only**  
- Recommend rooms if none are available  
- View customer reservations  
- Admin can: list customers, rooms, reservations, add rooms  

---

## Key Points

- Uses **interfaces** and **inheritance** for polymorphism  
- **Private fields** with getters/setters for encapsulation  
- **Singleton services** for managing data  
- **Collections** used for rooms, customers, and reservations  
- **DateUtil** used for consistent date parsing and formatting  
- **Switch statements** for menu navigation  
- **Try/catch blocks** to prevent crashes  

