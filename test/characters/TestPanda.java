package characters;

import bots.Marcel;
import bots.MotherBot;
import org.junit.Before;
import org.junit.Test;
import parcels.ClassicParcel;
import parcels.ColorList;
import parcels.GameBoard;
import parcels.Parcel;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestPanda {

    Marcel b;
    ClassicParcel p;
    ClassicParcel p1;
    ClassicParcel p2;

    @Before
    public void resetPanda() {
        Panda.reset();
    }

    @Before
    public void resetGameBoard() {
        GameBoard.reset();
    }

    @Test
    public void createPanda() {
        assertArrayEquals(new int[]{27, 27}, Panda.getInstance().getPosition().getCoordinates());
    }

    @Test
    public void movePandaTest() {
        b = new Marcel();
        p = new ClassicParcel(ColorList.GREEN);
        int[] coord = {27, 28};
        p.setCoordinates(coord);
        assertEquals(null, GameBoard.getInstance().getBoard()[27][28]);
        GameBoard.getInstance().addParcel(p, b);
        Panda.getInstance().move(coord, b);
        assertArrayEquals(coord, Panda.getInstance().getPosition().getCoordinates());
    }

    @Test
    public void testAction() {
        b = new Marcel();
        p = new ClassicParcel(ColorList.GREEN);
        p.setCoordinates(new int[]{27, 28});
        GameBoard.getInstance().addParcel(p, b);

        p1 = new ClassicParcel(ColorList.PINK);
        p1.setCoordinates(new int[]{28, 27});
        GameBoard.getInstance().addParcel(p1, b);

        p2 = new ClassicParcel(ColorList.YELLOW);
        p2.setCoordinates(new int[]{28, 28});
        GameBoard.getInstance().addParcel(p2, b);
        p2.growBamboo();

        Panda.getInstance().setPosition(p);
        Panda.getInstance().action(p, b);

        Panda.getInstance().setPosition(p1);
        Panda.getInstance().action(p1, b);

        Panda.getInstance().setPosition(p2);
        Panda.getInstance().action(p2, b);

        assertEquals(0, p.getHeightBamboo());
        assertEquals(1, b.getGreenBambooNb());
        assertEquals(1, b.getPinkBambooNb());
        assertEquals(0, b.getYellowBambooNb());
    }

    @Test
    public void testMovePanda() {
        MotherBot b = new Marcel();
        p = new ClassicParcel(ColorList.GREEN);
        p.setCoordinates(new int[]{27,28});
        p1 = new ClassicParcel(ColorList.GREEN);
        p1.setCoordinates(new int[]{28,27});
        p2 = new ClassicParcel(ColorList.GREEN);
        p2.setCoordinates(new int[]{28,28});

        GameBoard.getInstance().addParcel(p, b);
        GameBoard.getInstance().addParcel(p1, b);
        GameBoard.getInstance().addParcel(p2, b);

        Panda.getInstance().setPosition(GameBoard.getInstance().getBoard()[27][27]);
        Panda.getInstance().movePanda(new int[]{28,28}, b);

        assertEquals(p2, Panda.getInstance().getPosition());
    }

    @Test
    public void testFindBamboo() {
        p = new ClassicParcel(ColorList.GREEN);
        p.setCoordinates(new int[]{27,28});
        Panda.getInstance().move(new int[]{27,27}, b);
        ArrayList<Parcel> bambooHere = Panda.getInstance().findBamboo();
        assertEquals(true, bambooHere.isEmpty());
    }
}

