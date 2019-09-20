package engines;

import bots.Marcel;
import bots.MotherBot;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestGame {

    Game g;

    @Test
    public void testCreateGame() {
        MotherBot[] botList = {new Marcel(), new Marcel()};
        g = new Game(botList, false);
        g.startGame();
        assertEquals("Marcel", botList[0].toString());
        assertEquals("Marcel", botList[1].toString());
    }
}