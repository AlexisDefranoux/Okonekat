package engines;

import bots.Marcel;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTurn {

    Turn turn;
    Marcel marcel;

    @Test
    public void createTurn() {
        turn = new Turn();
        assertEquals(2, turn.getActionNb());
    }
}
