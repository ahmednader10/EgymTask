package egymTask.gameObjects;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int id;
    private String name;
    private List<Direction> directions = new ArrayList<>();
    private List<CollectibleObject> Objects= new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }

    public List<CollectibleObject> getObjects() {
        return Objects;
    }

    public void setObjects(List<CollectibleObject> objects) {
        Objects = objects;
    }

    public void addObject(CollectibleObject o) {
        this.Objects.add(o);
    }
}
