package egymTask;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import egymTask.gameObjects.*;
import egymTask.gameObjects.Map;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import java.io.*;
import java.util.*;

public class InputOutput {
    static Map map = new Map();
    static List<String> itemsToCollect = new ArrayList<>();

    public static void readMap() throws IOException, SAXException, ParserConfigurationException {
        File fXmlFile = new File("src/inputFiles/map.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("room");

        for (int i = 0; i < nList.getLength(); i++) {
            Room room = new Room();
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                room.setId(Integer.parseInt(eElement.getAttribute("id")));
                room.setName(eElement.getAttribute("name"));

                if(eElement.hasAttribute("south")) {
                    Direction d = new Direction();
                    d.setCd(CardinalDirection.south);
                    d.setId(Integer.parseInt(eElement.getAttribute("south")));
                    room.addDirection(d);
                }
                if(eElement.hasAttribute("east")) {
                    Direction d = new Direction();
                    d.setCd(CardinalDirection.east);
                    d.setId(Integer.parseInt(eElement.getAttribute("east")));
                    room.addDirection(d);
                }

                if(eElement.hasAttribute("west")) {
                    Direction d = new Direction();
                    d.setCd(CardinalDirection.west);
                    d.setId(Integer.parseInt(eElement.getAttribute("west")));
                    room.addDirection(d);
                }
                if(eElement.hasAttribute("north")) {
                    Direction d = new Direction();
                    d.setCd(CardinalDirection.north);
                    d.setId(Integer.parseInt(eElement.getAttribute("north")));
                    room.addDirection(d);
                }

                if(eElement.hasChildNodes()) {
                    for(int j = 0; j < eElement.getElementsByTagName("object").getLength(); j++) {
                        CollectibleObject o = new CollectibleObject();
                        o.setName(eElement.getElementsByTagName("object").item(j).getAttributes().getNamedItem("name").getTextContent());
                        room.addObject(o);
                    }
                }
            }
            map.addRoom(room);
        }
    }

    public static void readTextFile(String file) throws IOException {
        FileReader input = new FileReader(file);
        BufferedReader bufRead = new BufferedReader(input);
        String startindex = bufRead.readLine();
        int Startpoint =Integer.parseInt(startindex);
        String object;
        while ((object = bufRead.readLine()) != null) {
            itemsToCollect.add(object);
        }
        //calculateRoute(Startpoint);
        dfs_iterative(Startpoint);
    }

    public static void calculateRoute(int Start) {
        Queue<Room> rooms = new LinkedList<>();
        rooms.add(map.getRoom(Start));
        while (!itemsToCollect.isEmpty()) {
            Room r = rooms.remove();
            searchRoom(r.getId());
            System.out.println(r);
            for(int i = 0; i < r.getDirections().size(); i++) {
                rooms.add(map.getRoom(r.getDirections().get(i).getId()));
            }
        }
    }

    // recursive dfs
    private static void dfs_rec(List<Room> rooms, int start){
        if (itemsToCollect.isEmpty())
            return;
        System.out.println(rooms.get(start));
        searchRoom(start);
        for(Direction d : rooms.get(start).getDirections()){
            dfs_rec(rooms, d.getId());
        }
    }

    public static void dfs_iterative(int start){
        Stack<Integer> st = new Stack<Integer>();
        st.push(start);
        while(!st.isEmpty()){
            if(itemsToCollect.isEmpty()) {
                break;
            }
            Room r  = map.getRoom(st.pop());
            if(r.isVisited()) {
                System.out.println(r);
            }
            else {
                searchRoom(r.getId());
                r.setVisited(true);
                System.out.println(r);
                Stack<Integer> auxStack = new Stack<Integer>();
                for (Direction d : map.getRoom(r.getId()).getDirections()) {
                   // if(!map.getRoom(d.getId()).isVisited())
                        auxStack.push(d.getId());
                }
                while (!auxStack.isEmpty()) {
                    st.push(auxStack.pop());
                }
            }
        }
    }

    /*public static String calculateRoute(int Start, List<String> itemsToCollect) {

        if(itemsToCollect.isEmpty())
            return "";
        if(Start == -1)
            return "";
        //System.out.println(Start);
        Room current = map.getRoom(Start);
        System.out.println(current);
        map.removeRoom(current.getId());
        String room = "";
        for (int i = 0; i < itemsToCollect.size(); i++) {
            if(current.hasObject(itemsToCollect.get(i))) {
                itemsToCollect.remove(i);
                // System.out.println(r);
                room = current.toString();
            }
        }
        return room+calculateRoute(current.getRoomFromDirection(CardinalDirection.north, map), itemsToCollect)
                +calculateRoute(current.getRoomFromDirection(CardinalDirection.east, map), itemsToCollect)+
                calculateRoute(current.getRoomFromDirection(CardinalDirection.west, map), itemsToCollect)+
                calculateRoute(current.getRoomFromDirection(CardinalDirection.south, map), itemsToCollect);
    }*/

    public static void  searchRoom(int id) {
        Room r = map.getRoom(id);
        for (int i = 0; i < itemsToCollect.size(); i++) {

            if(r.hasObject(itemsToCollect.get(i))) {
                //System.out.println(itemsToCollect.get(i));
                itemsToCollect.remove(i);
                i--;
            }

        }
    }

    public static void main(String[]args) throws ParserConfigurationException, SAXException, IOException {
        readMap();
        readTextFile("src/inputFiles/config1.txt");
    }
}
