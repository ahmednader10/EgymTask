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
    //items required to collect
    static List<String> itemsToCollect = new ArrayList<>();

    //method to parse map.xml file and build a map object accordingly
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

    //takes as parameter the path to the input file to be parsed, and calls a method to calculate a
    // valid route according to the input specified in the text file
    public static String readTextFile(String file) throws IOException {
        FileReader input = new FileReader(file);
        BufferedReader bufRead = new BufferedReader(input);
        String startindex = bufRead.readLine();
        //index of the start room
        int Startpoint = Integer.parseInt(startindex);
        String object;
        while ((object = bufRead.readLine()) != null) {
            itemsToCollect.add(object);
        }
        return calculateRoute(Startpoint);
    }

    // A dfs approach to span all the rooms on the map by setting a boolean variable as true when visiting a room
    // and accordingly if a room is visited before then it's only printed to indicate that the player passed by it
    // otherwise if it's not visited then search for the itemsToCollect inside it.
    public static String calculateRoute(int start){
        Stack<Integer> st = new Stack<Integer>();
        st.push(start);
        String route = "";
        while(!st.isEmpty()){
            if(itemsToCollect.isEmpty()) {
                break;
            }
            Room r  = map.getRoom(st.pop());
            if(r.isVisited()) {
                route += r+"\n";
            }
            else {
                searchRoom(r.getId());
                r.setVisited(true);
                route += r+"\n";
                Stack<Integer> auxStack = new Stack<Integer>();
                if(map.getRoom(r.getId()).hasDirections()) {
                    for (Direction d : map.getRoom(r.getId()).getDirections()) {
                        auxStack.push(d.getId());
                    }
                    while (!auxStack.isEmpty()) {
                        st.push(auxStack.pop());
                    }
                }
            }
        }
        return route;
    }

    //takes as input a room id and searches this room for the elements left in the itemsToCollect ArrayList
    public static void  searchRoom(int id) {
        Room r = map.getRoom(id);
        for (int i = 0; i < itemsToCollect.size(); i++) {

            if(r.hasObject(itemsToCollect.get(i))) {
                itemsToCollect.remove(i);
                i--;
            }
        }
    }

    public static void main(String[]args) throws ParserConfigurationException, SAXException, IOException {
        readMap();
        System.out.println(readTextFile("src/inputFiles/config1.txt"));
    }
}
