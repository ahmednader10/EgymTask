package egymTask.gameObjects;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<Room> rooms= new ArrayList<>();

    public void addRoom(Room r) {
        rooms.add(r);
    }

    public Room getRoom(int id) {
        return rooms.get(id -1);
    }

    public String toString() {
        String map = "";
        for(int i = 0; i < rooms.size(); i++) {
            map+=rooms.get(i)+"\n";
        }
        return map;
    }
}
