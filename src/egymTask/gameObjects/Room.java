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

    public void addDirection(Direction d) {
        this.directions.add(d);
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

    public String toString() {
        String items = "";
        for(int i = 0; i < this.Objects.size(); i++) {
            if(i == Objects.size() -1) {
                items += Objects.get(i).getName();
            }
            else {
                items += Objects.get(i).getName() + ", ";
            }
        }
       /* String direction = " Directions: ";
        for(int j = 0; j < this.directions.size(); j++) {
            if(j == directions.size() -1) {
                direction += directions.get(j).getCd()+" :" + directions.get(j).getId();
            }
            else {
                direction += directions.get(j).getCd()+" :" + directions.get(j).getId()+ ", ";
            }
        }*/
        return "ID: "+ this.id+" Room: "+ this.name+" Items: "+items;
    }
}
