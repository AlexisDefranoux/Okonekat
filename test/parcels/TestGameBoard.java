package parcels;

import bots.Marcel;
import bots.MotherBot;
import characters.Gardener;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import parcels.GameBoard;

import static org.junit.Assert.*;
import java.util.ArrayList;

public class TestGameBoard {

    ClassicParcel p;
    ClassicParcel p1;
    ClassicParcel p2;

    @Before
    public void resetGameBoard() {
        GameBoard.reset();
    }

    @Test
    public void createGameBoard(){
        assertEquals(54, GameBoard.getInstance().getSize());
        assertEquals(null, GameBoard.getInstance().getBoard()[27][28]);
    }

    @Test
    public void addWrongParcel(){
        Marcel b = new Marcel();
        int[] coord = {15, 15};
        p = new ClassicParcel(ColorList.YELLOW);
        p.setCoordinates(coord);
        assertEquals(false, GameBoard.getInstance().addParcel(p, b));
    }

    @Test
    public void testAddParcel() {
        Marcel b = new Marcel();
        p = new ClassicParcel(ColorList.GREEN);
        p.setCoordinates(new int[]{28,27});
        p1 = new ClassicParcel(ColorList.GREEN);
        p1.setCoordinates(new int[]{29,27});
        GameBoard.getInstance().addParcel(p, b);
        assertEquals(true, GameBoard.getInstance().addParcel(p1,b));
    }

    @Test
    public void addRightParcel(){
        Marcel b = new Marcel();
        int[] coord = {27, 28};
        p = new ClassicParcel(ColorList.YELLOW);
        p.setCoordinates(coord);
        assertEquals(true, GameBoard.getInstance().addParcel(p, b));
    }

    @Test
    public void allowedParcels(){
        ArrayList<Integer[]> parcelCoord = GameBoard.getInstance().freeParcelLocations();
        int i;
        for(i = 0; i < parcelCoord.size(); i++){
            System.out.println("[" + parcelCoord.get(i)[0] + ", " + parcelCoord.get(i)[1] + "]");
        }
        assertEquals(6, parcelCoord.size());
    }

    @Test
    public void testNullNeighboor(){
        int[] coord = {28, 28};
        ArrayList<Integer[]> neighboorCoord = GameBoard.getInstance().getNeighbor(coord[0], coord[1]);
        assertEquals(0, neighboorCoord.size());
    }

    @Test
    public void testExistingNeighboor(){
        int[] coord = {27, 27};
        ArrayList<Integer[]> neighboorCoord = GameBoard.getInstance().getNeighbor(coord[0], coord[1]);
        assertEquals(6, neighboorCoord.size());
    }

    @Test
    public void testExistingParcels(){
        ArrayList<Parcel> p = GameBoard.getInstance().getExistingParcel();
        assertEquals(1, p.size());
        assertEquals(true, p.get(0).getCoordinates()[0] == 27 && p.get(0).getCoordinates()[1] == 27);
    }

    @Test
    public void testColoredParcel(){
        Marcel b = new Marcel();
        p = new ClassicParcel(ColorList.GREEN);
        p.setCoordinates(new int[]{27,28});
        p1 = new ClassicParcel(ColorList.PINK);
        p1.setCoordinates(new int[]{28,27});
        GameBoard.getInstance().addParcel(p, b);
        GameBoard.getInstance().addParcel(p1, b);
        assertEquals(1, GameBoard.getInstance().getColoredParcel(ColorList.GREEN).size());
        assertEquals(ColorList.PINK, GameBoard.getInstance().getXYParcel(28,27).getColor());
        assertEquals(ColorList.GREEN, GameBoard.getInstance().getNextParcel(p1, "NO").getColor());
    }

    @Test
    public void testIrrigableParcels() {
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

        IrrigationBoard.getInstance().addIrrigation(new int[]{55,55}, b);
        System.out.println(GameBoard.getInstance().getBoard()[28][28].getIrrigation());
        assertEquals(true, GameBoard.getInstance().irrigableParcels().containsKey(GameBoard.getInstance().getBoard()[28][28]));
        assertEquals(1, GameBoard.getInstance().irrigableParcels().size());
    }

    @Test
    public void testWhereCanGrow() {
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

        assertEquals(2, GameBoard.getInstance().whereCanGrow().size());
    }

    @Test
    public void testWhereCanEat() {
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

        p.growBamboo();

        assertEquals(2, GameBoard.getInstance().whereCanEat().size());
    }
}
