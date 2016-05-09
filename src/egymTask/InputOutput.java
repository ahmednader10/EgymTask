package egymTask;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import egymTask.gameObjects.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InputOutput {
    static Map map = new Map();

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
                if(eElement.hasAttribute("north")) {
                    Direction d = new Direction();
                    d.setCd(CardinalDirection.north);
                    d.setId(Integer.parseInt(eElement.getAttribute("north")));
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
                if(eElement.hasAttribute("south")) {
                    Direction d = new Direction();
                    d.setCd(CardinalDirection.south);
                    d.setId(Integer.parseInt(eElement.getAttribute("south")));
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
        Room Startpoint = map.getRoom(Integer.parseInt(startindex));
        System.out.print(Startpoint);
        List<CollectibleObject> items = new ArrayList<>();
        String object;
        while ((object = bufRead.readLine()) != null) {
            CollectibleObject o = new CollectibleObject();
            o.setName(object);
            items.add(o);
        }
        calculateRoute(Startpoint, items);
    }

    public static void calculateRoute(Room Start, List<CollectibleObject>items) {
    }

    public static void main(String[]args) throws ParserConfigurationException, SAXException, IOException {
        readMap();
        readTextFile("src/inputFiles/config1.txt");
    }
}
