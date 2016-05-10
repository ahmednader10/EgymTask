package egymTask.gameObjects;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<Room> rooms= new ArrayList<>();

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room r) {
        rooms.add(r);
    }

    public Room getRoom(int id) {
        for (int i = 0; i < rooms.size(); i++) {
            if(rooms.get(i).getId() == id) {
                return rooms.get(i);
            }
        }
        return null;
    }

    public boolean hasRoom(int id) {
        for (int i = 0; i < rooms.size(); i++) {
            if(rooms.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void removeRoom(int id) {
        for (int i = 0; i < rooms.size(); i++) {
            if(rooms.get(i).getId() == id) {
                rooms.remove(i);
            }
        }
    }

    public String toString() {
        String map = "";
        for(int i = 0; i < rooms.size(); i++) {
            map+=rooms.get(i)+"\n";
        }
        return map;
    }
}
