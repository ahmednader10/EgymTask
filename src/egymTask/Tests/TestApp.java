package egymTask.Tests;

import egymTask.InputOutput;
import org.junit.Test;
import org.xml.sax.SAXException;
import static org.junit.Assert.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class TestApp {
    @Test
    public void testRoutefile1() throws ParserConfigurationException, SAXException, IOException {
        InputOutput io = new InputOutput();
        io.readMap();
        assertEquals("ID: 1 Room: Stairway Items: \n" +
                "ID: 2 Room: Hallway 1 Items: Scarf, Umbrella\n" +
                "ID: 7 Room: Living Room Items: \n" +
                "ID: 2 Room: Hallway 1 Items: Scarf, Umbrella\n" +
                "ID: 3 Room: Hallway 2 Items: Money\n" +
                "ID: 6 Room: Bath Room Items: Soap\n", io.readTextFile("src/inputFiles/config1.txt"));
    }
}
