package goals;

import bots.Marcel;
import engines.Deck;
import org.junit.Before;
import org.junit.Test;
import parcels.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestGoal {

    @Before
    public void resetGameBoard() {
        GameBoard.reset();
    }

    @Before
    public void resetDeck() {
        Deck.reset();
    }

    @Test
    public void createObjective() {
            PandaGoal pandaGoal = new PandaGoal(4, ColorList.PINK);
        assertEquals(ColorList.PINK,pandaGoal.getColor());
        assertEquals(4,pandaGoal.getScoring());
    }

    @Test
    public void CheckRightStraightParcelGoal(){
        Marcel b = new Marcel();
        ClassicParcel p1 = new ClassicParcel(ColorList.GREEN);
        int[] coord1 = {27, 28};
        p1.setCoordinates(coord1);
        p1.setIrrigation();
        ClassicParcel p2 = new ClassicParcel(ColorList.GREEN);
        int[] coord2 = {28, 27};
        p2.setCoordinates(coord2);
        p2.setIrrigation();
        ClassicParcel p3 = new ClassicParcel(ColorList.PINK);
        int[] coord3 = {28, 26};
        p3.setCoordinates(coord3);
        p3.setIrrigation();
        ClassicParcel p4 = new ClassicParcel(ColorList.GREEN);
        int[] coord4 = {29, 26};
        p4.setCoordinates(coord4);
        p4.setIrrigation();

        GameBoard.getInstance().addParcel(p1, b);
        GameBoard.getInstance().addParcel(p2, b);
        GameBoard.getInstance().addParcel(p3, b);
        GameBoard.getInstance().addParcel(p4, b);

        ArrayList<Parcel> list = GameBoard.getInstance().getExistingParcel();

        Pattern pattern = new Pattern(Form.STRAIGHT, ColorList.GREEN);
        ParcelGoal pg = new ParcelGoal(3, pattern);

        assertEquals(true, pg.checkGoal(list));


    }

    @Test
    public void CheckWrongParcelGoal(){
        Marcel b = new Marcel();
        ClassicParcel p1 = new ClassicParcel(ColorList.GREEN);
        int[] coord1 = {27, 28};
        p1.setCoordinates(coord1);
        ClassicParcel p2 = new ClassicParcel(ColorList.PINK);
        int[] coord2 = {28, 27};
        p2.setCoordinates(coord2);
        ClassicParcel p3 = new ClassicParcel(ColorList.PINK);
        int[] coord3 = {28, 26};
        p3.setCoordinates(coord3);
        ClassicParcel p4 = new ClassicParcel(ColorList.GREEN);
        int[] coord4 = {29, 26};
        p4.setCoordinates(coord4);

        GameBoard.getInstance().addParcel(p1, b);
        GameBoard.getInstance().addParcel(p2, b);
        GameBoard.getInstance().addParcel(p3, b);
        GameBoard.getInstance().addParcel(p4, b);

        ArrayList<Parcel> list = GameBoard.getInstance().getExistingParcel();
        //System.out.println(list.size());

        Pattern pattern = new Pattern(Form.STRAIGHT, ColorList.GREEN);
        ParcelGoal pg = new ParcelGoal(3, pattern);

        assertEquals(false, pg.checkGoal(list));


    }

    @Test
    public void CheckRightCurveParcelGoal(){
        Marcel b = new Marcel();
        ClassicParcel p1 = new ClassicParcel(ColorList.GREEN);
        int[] coord1 = {27, 28};
        p1.setCoordinates(coord1);
        p1.setIrrigation();
        ClassicParcel p2 = new ClassicParcel(ColorList.GREEN);
        int[] coord2 = {28, 27};
        p2.setCoordinates(coord2);
        p2.setIrrigation();
        ClassicParcel p3 = new ClassicParcel(ColorList.PINK);
        int[] coord3 = {28, 26};
        p3.setCoordinates(coord3);
        p3.setIrrigation();
        ClassicParcel p4 = new ClassicParcel(ColorList.GREEN);
        int[] coord4 = {29, 26};
        p4.setCoordinates(coord4);
        p4.setIrrigation();
        ClassicParcel p5 = new ClassicParcel(ColorList.GREEN);
        int[] coord5 = {28, 28};
        p5.setCoordinates(coord5);
        p5.setIrrigation();

        GameBoard.getInstance().addParcel(p1, b);
        GameBoard.getInstance().addParcel(p2, b);
        GameBoard.getInstance().addParcel(p3, b);
        GameBoard.getInstance().addParcel(p4, b);
        GameBoard.getInstance().addParcel(p5, b);

        ArrayList<Parcel> list = GameBoard.getInstance().getExistingParcel();

        Pattern pattern = new Pattern(Form.CURVE, ColorList.GREEN);
        ParcelGoal pg = new ParcelGoal(3, pattern);

        assertEquals(true, pg.checkGoal(list));


    }

    @Test
    public void CheckWrongCurveParcelGoal(){
        Marcel b = new Marcel();
        ClassicParcel p1 = new ClassicParcel(ColorList.GREEN);
        int[] coord1 = {27, 28};
        p1.setCoordinates(coord1);
        ClassicParcel p2 = new ClassicParcel(ColorList.PINK);
        int[] coord2 = {28, 27};
        p2.setCoordinates(coord2);
        ClassicParcel p3 = new ClassicParcel(ColorList.PINK);
        int[] coord3 = {28, 26};
        p3.setCoordinates(coord3);
        ClassicParcel p4 = new ClassicParcel(ColorList.GREEN);
        int[] coord4 = {29, 26};
        p4.setCoordinates(coord4);
        ClassicParcel p5 = new ClassicParcel(ColorList.GREEN);
        int[] coord5 = {28, 28};
        p5.setCoordinates(coord5);

        GameBoard.getInstance().addParcel(p1, b);
        GameBoard.getInstance().addParcel(p2, b);
        GameBoard.getInstance().addParcel(p3, b);
        GameBoard.getInstance().addParcel(p4, b);
        GameBoard.getInstance().addParcel(p5, b);

        ArrayList<Parcel> list = GameBoard.getInstance().getExistingParcel();

        Pattern pattern = new Pattern(Form.CURVE, ColorList.GREEN);
        ParcelGoal pg = new ParcelGoal(3, pattern);

        assertEquals(false, pg.checkGoal(list));


    }

    @Test
    public void CheckRightTriangleParcelGoal(){
        Marcel b = new Marcel();
        ClassicParcel p1 = new ClassicParcel(ColorList.YELLOW);
        int[] coord1 = {27, 28};
        p1.setCoordinates(coord1);
        p1.setIrrigation();
        ClassicParcel p2 = new ClassicParcel(ColorList.GREEN);
        int[] coord2 = {28, 27};
        p2.setCoordinates(coord2);
        p2.setIrrigation();
        ClassicParcel p3 = new ClassicParcel(ColorList.GREEN);
        int[] coord3 = {28, 26};
        p3.setCoordinates(coord3);
        p3.setIrrigation();
        ClassicParcel p4 = new ClassicParcel(ColorList.GREEN);
        int[] coord4 = {29, 26};
        p4.setCoordinates(coord4);
        p4.setIrrigation();
        ClassicParcel p5 = new ClassicParcel(ColorList.PINK);
        int[] coord5 = {28, 28};
        p5.setCoordinates(coord5);
        p5.setIrrigation();

        GameBoard.getInstance().addParcel(p1, b);
        GameBoard.getInstance().addParcel(p2, b);
        GameBoard.getInstance().addParcel(p3, b);
        GameBoard.getInstance().addParcel(p4, b);
        GameBoard.getInstance().addParcel(p5, b);

        ArrayList<Parcel> list = GameBoard.getInstance().getExistingParcel();

        Pattern pattern = new Pattern(Form.TRIANGLE, ColorList.GREEN);
        ParcelGoal pg = new ParcelGoal(3, pattern);

        assertEquals(true, pg.checkGoal(list));
    }

    @Test
    public void CheckWrongTriangleParcelGoal(){
        Marcel b = new Marcel();
        ClassicParcel p1 = new ClassicParcel(ColorList.YELLOW);
        int[] coord1 = {27, 28};
        p1.setCoordinates(coord1);
        ClassicParcel p2 = new ClassicParcel(ColorList.GREEN);
        int[] coord2 = {28, 27};
        p2.setCoordinates(coord2);
        ClassicParcel p3 = new ClassicParcel(ColorList.YELLOW);
        int[] coord3 = {28, 26};
        p3.setCoordinates(coord3);
        ClassicParcel p4 = new ClassicParcel(ColorList.GREEN);
        int[] coord4 = {29, 26};
        p4.setCoordinates(coord4);
        ClassicParcel p5 = new ClassicParcel(ColorList.PINK);
        int[] coord5 = {28, 28};
        p5.setCoordinates(coord5);

        GameBoard.getInstance().addParcel(p1, b);
        GameBoard.getInstance().addParcel(p2, b);
        GameBoard.getInstance().addParcel(p3, b);
        GameBoard.getInstance().addParcel(p4, b);
        GameBoard.getInstance().addParcel(p5, b);

        ArrayList<Parcel> list = GameBoard.getInstance().getExistingParcel();

        Pattern pattern = new Pattern(Form.TRIANGLE, ColorList.GREEN);
        ParcelGoal pg = new ParcelGoal(3, pattern);

        assertEquals(false, pg.checkGoal(list));
    }

    @Test
    public void testPandaGoal(){
        Marcel b = new Marcel();
        PandaGoal p = new PandaGoal(2, ColorList.YELLOW);
        PandaGoal p1 = new PandaGoal(2, ColorList.GREEN);
        PandaGoal p2 = new PandaGoal(2, ColorList.PINK);
        PandaGoal p3 = new PandaGoal(2, ColorList.MULTICOLOR);

        b.setGreenBambooNb(4);
        b.setPinkBambooNb(4);
        b.setYellowBambooNb(4);

        assertEquals(true, p.checkGoal(b.getGreenBambooNb(),b.getPinkBambooNb(), b.getYellowBambooNb()));
        assertEquals(true, p1.checkGoal(b.getGreenBambooNb(),b.getPinkBambooNb(), b.getYellowBambooNb()));
        assertEquals(true, p2.checkGoal(b.getGreenBambooNb(),b.getPinkBambooNb(), b.getYellowBambooNb()));
        assertEquals(true, p3.checkGoal(b.getGreenBambooNb(),b.getPinkBambooNb(), b.getYellowBambooNb()));
    }

    @Test
    public void CheckGardenerGoal(){
        Marcel b = new Marcel();
        ClassicParcel p1 = new ClassicParcel(ColorList.YELLOW);
        int[] coord1 = {27, 28};
        p1.setCoordinates(coord1);
        p1.setIrrigation();
        ClassicParcel p2 = new ClassicParcel(ColorList.GREEN);
        int[] coord2 = {28, 27};
        p2.setCoordinates(coord2);
        p2.setIrrigation();
        ClassicParcel p3 = new ClassicParcel(ColorList.YELLOW);
        int[] coord3 = {28, 26};
        p3.setCoordinates(coord3);
        p3.setIrrigation();
        ClassicParcel p4 = new ClassicParcel(ColorList.GREEN, ImprovementList.fertilizer);
        int[] coord4 = {29, 26};
        p4.setCoordinates(coord4);
        p4.setIrrigation();
        ClassicParcel p5 = new ClassicParcel(ColorList.PINK);
        int[] coord5 = {28, 28};
        p5.setCoordinates(coord5);
        p5.setIrrigation();

        p1.growBamboo();
        p1.growBamboo();
        p1.growBamboo();
        p2.growBamboo();
        p2.growBamboo();
        p2.growBamboo();
        p3.growBamboo();
        p3.growBamboo();
        p3.growBamboo();
        p4.growBamboo();
        p4.growBamboo();
        p4.growBamboo();
        p5.growBamboo();
        p5.growBamboo();
        p5.growBamboo();



        GameBoard.getInstance().addParcel(p1, b);
        GameBoard.getInstance().addParcel(p2, b);
        GameBoard.getInstance().addParcel(p3, b);
        GameBoard.getInstance().addParcel(p4, b);
        GameBoard.getInstance().addParcel(p5, b);

        GardenerGoal g = new GardenerGoal(3, 3, 2, ColorList.YELLOW);
        GardenerGoal g1 = new GardenerGoal(3, 3, 2, ColorList.PINK);
        GardenerGoal g2 = new GardenerGoal(3, 3, 1, ImprovementList.fertilizer, ColorList.GREEN);


        assertEquals(true, g.checkGoal());
        assertEquals(false, g1.checkGoal());
        assertEquals(true, g2.checkGoal());
    }

    @Test
    public void CheckRightDiamondParcelGoal(){
        Marcel b = new Marcel();
        ClassicParcel p1 = new ClassicParcel(ColorList.GREEN);
        int[] coord1 = {27, 28};
        p1.setCoordinates(coord1);
        p1.setIrrigation();
        ClassicParcel p2 = new ClassicParcel(ColorList.GREEN);
        int[] coord2 = {28, 27};
        p2.setCoordinates(coord2);
        p2.setIrrigation();
        ClassicParcel p3 = new ClassicParcel(ColorList.YELLOW);
        int[] coord3 = {28, 28};
        p3.setCoordinates(coord3);
        p3.setIrrigation();
        ClassicParcel p4 = new ClassicParcel(ColorList.YELLOW);
        int[] coord4 = {29, 27};
        p4.setCoordinates(coord4);
        p4.setIrrigation();
        ClassicParcel p5 = new ClassicParcel(ColorList.GREEN);
        int[] coord5 = {29, 26};
        p5.setCoordinates(coord5);
        p5.setIrrigation();

        GameBoard.getInstance().addParcel(p1, b);
        GameBoard.getInstance().addParcel(p2, b);
        GameBoard.getInstance().addParcel(p3, b);
        GameBoard.getInstance().addParcel(p4, b);
        GameBoard.getInstance().addParcel(p5, b);

        ArrayList<Parcel> list = GameBoard.getInstance().getExistingParcel();

        Pattern pattern = new Pattern(Form.DIAMOND, ColorList.YELLOW, ColorList.GREEN);
        ParcelGoal pg = new ParcelGoal(3, pattern);

        assertEquals(true, pg.checkGoal(list));
    }

    @Test
    public void CheckWrongDiamondParcelGoal(){
        Marcel b = new Marcel();
        ClassicParcel p1 = new ClassicParcel(ColorList.YELLOW);
        int[] coord1 = {27, 28};
        p1.setCoordinates(coord1);
        ClassicParcel p2 = new ClassicParcel(ColorList.GREEN);
        int[] coord2 = {28, 27};
        p2.setCoordinates(coord2);
        ClassicParcel p3 = new ClassicParcel(ColorList.YELLOW);
        int[] coord3 = {28, 28};
        p3.setCoordinates(coord3);
        ClassicParcel p4 = new ClassicParcel(ColorList.YELLOW);
        int[] coord4 = {29, 27};
        p4.setCoordinates(coord4);
        ClassicParcel p5 = new ClassicParcel(ColorList.PINK);
        int[] coord5 = {26, 27};
        p5.setCoordinates(coord5);

        GameBoard.getInstance().addParcel(p1, b);
        GameBoard.getInstance().addParcel(p2, b);
        GameBoard.getInstance().addParcel(p3, b);
        GameBoard.getInstance().addParcel(p4, b);
        GameBoard.getInstance().addParcel(p5, b);

        ArrayList<Parcel> list = GameBoard.getInstance().getExistingParcel();

        Pattern pattern = new Pattern(Form.DIAMOND, ColorList.YELLOW, ColorList.PINK);
        ParcelGoal pg = new ParcelGoal(3, pattern);

        assertEquals(false, pg.checkGoal(list));
    }

}
