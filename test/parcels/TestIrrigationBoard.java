package parcels;

import bots.Marcel;
import bots.MotherBot;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Field;

public class TestIrrigationBoard {

    MotherBot b;
    ClassicParcel p;
    ClassicParcel p2;
    int[] coordinates = {27,28};
    int[] coordinates2 = {28,27};

    @Before
    public void resetIrrigationBoard() {
        IrrigationBoard.reset();
    }

    @Before
    public void resetGameBoard() {
        GameBoard.reset();
    }

    @Test
    public void createIrrigationGameBoard() {
        assertEquals(107, IrrigationBoard.getInstance().getBoard().length);
        assertEquals(true, IrrigationBoard.getInstance().getBoard()[54][55]);
        assertEquals(false, IrrigationBoard.getInstance().getBoard()[30][27]);
    }

    @Test
    public void testType() {
        assertEquals(1, IrrigationBoard.type(54, 55));
        assertEquals(2, IrrigationBoard.type(55, 43));
        assertEquals(3, IrrigationBoard.type(29, 40));
        assertEquals(0, IrrigationBoard.type(28, 54));
    }

    @Test
    public void testConnected() {
        assertEquals(true, IrrigationBoard.getInstance().connected(54, 55));
        assertEquals(true, IrrigationBoard.getInstance().connected(55, 55));
        assertEquals(true, IrrigationBoard.getInstance().connected(53, 56));
        assertEquals(false, IrrigationBoard.getInstance().connected(54, 57));
    }

    @Test
    public void testCanAddIrrigation() {
        p = new ClassicParcel(ColorList.GREEN);
        p.setCoordinates(coordinates);
        p2 = new ClassicParcel(ColorList.YELLOW);
        p2.setCoordinates(coordinates2);

        GameBoard.getInstance().addParcel(p, b);
        GameBoard.getInstance().addParcel(p2, b);

        assertEquals(true, IrrigationBoard.getInstance().canAddIrrigation(55, 55));
        assertEquals(false, IrrigationBoard.getInstance().canAddIrrigation(55, 54));
        assertEquals(false, IrrigationBoard.getInstance().canAddIrrigation(55, 59));
    }

    @Test
    public void testIrrigationFreeLocation() {
        b = new Marcel();
        p = new ClassicParcel(ColorList.YELLOW);
        p.setCoordinates(coordinates);
        p2 = new ClassicParcel(ColorList.GREEN);
        p2.setCoordinates(coordinates2);

        assertEquals(0, IrrigationBoard.getInstance().irrigationFreeLocation().size());

        GameBoard.getInstance().addParcel(p, b);
        GameBoard.getInstance().addParcel(p2, b);

        assertEquals(1, IrrigationBoard.getInstance().irrigationFreeLocation().size());
    }
}
