package characters;

import bots.Marcel;
import org.junit.Before;
import org.junit.Test;
import parcels.ClassicParcel;
import parcels.ColorList;
import parcels.GameBoard;
import parcels.Parcel;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TestGardener {

    private Marcel b;
    private ClassicParcel p;

    @Before
    public void resetGardener(){
        Gardener.reset();
    }

    @Before
    public void resetGameBoard() {
        GameBoard.reset();
    }

    @Test
    public void createGardener() {
        assertArrayEquals(new int[]{27,27}, Gardener.getInstance().getPosition().getCoordinates());
    }

    @Test
    public void TestMoveGardener() {
        b = new Marcel();
        p = new ClassicParcel(ColorList.GREEN);
        int[] coord = {27,28};
        p.setCoordinates(coord);
        GameBoard.getInstance().addParcel(p, b);
        Gardener.getInstance().move(coord, b);
        assertArrayEquals(coord, Gardener.getInstance().getPosition().getCoordinates());
    }

    @Test
    public void testAction() {
        b = new Marcel();
        p = new ClassicParcel(ColorList.GREEN);
        p.setCoordinates(new int[]{27,28});
        ClassicParcel p1 = new ClassicParcel(ColorList.GREEN);
        p1.setCoordinates(new int[]{28,27});
        ClassicParcel p2 = new ClassicParcel(ColorList.PINK);
        p2.setCoordinates(new int[]{28,28});
        p2.setIrrigation();

        GameBoard.getInstance().addParcel(p, b);
        GameBoard.getInstance().addParcel(p1, b);
        GameBoard.getInstance().addParcel(p2, b);

        Gardener.getInstance().action(p, b);

        assertEquals(2, p.getHeightBamboo());
        assertEquals(2, p1.getHeightBamboo());
        assertEquals(1, p2.getHeightBamboo());
    }

    @Test
    public void testFindBamboo() {
        p = new ClassicParcel(ColorList.GREEN);
        p.setCoordinates(new int[]{27,28});
        Gardener.getInstance().move(new int[]{27,27}, b);
        ArrayList<Parcel> bambooToGrowHere = Gardener.getInstance().findBamboo();
        assertEquals(true, bambooToGrowHere.isEmpty());
    }
}
