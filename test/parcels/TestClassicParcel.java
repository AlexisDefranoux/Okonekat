package parcels;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestClassicParcel {

    ClassicParcel p;
    int tab[] = {10,12};

    @Test
    public void createClassicParcel(){
        p = new ClassicParcel(ColorList.YELLOW);
        p.setCoordinates(tab);
        assertEquals(0, p.getHeightBamboo());
        assertEquals(tab, p.getCoordinates());
        assertEquals(null, p.getImprovement());
        assertEquals(false, p.getIrrigation());
    }

    @Test
    public void createClassicParcelLayout() {
        p = new ClassicParcel(ColorList.GREEN, ImprovementList.fertilizer);
        p.setCoordinates(tab);
        assertEquals(ImprovementList.fertilizer, p.getImprovement());
    }

    @Test
    public void testGrowBamboo() {
        p = new ClassicParcel(ColorList.YELLOW);
        int[] coordinates = {66, 66};
        p.setCoordinates(coordinates);
        p.setIrrigation();
        p.growBamboo();
        assertEquals(2, p.getHeightBamboo());
    }

    @Test
    public void  testEatBamboo() {
        p = new ClassicParcel(ColorList.YELLOW);
        int[] coordinates = {66, 66};
        p.setCoordinates(coordinates);
        p.setIrrigation();
        assertEquals(true, p.canBambooGrow());
        assertEquals(true, p.canEatBamboo());
        p.eatBamboo();
        assertEquals(0, p.getHeightBamboo());
    }

    @Test
    public void testNextIrrigation() {
        p = new ClassicParcel(ColorList.GREEN);
        p.setCoordinates(new int[]{29,28});

        assertArrayEquals(new int[]{58,55}, p.getNextIrrigation("SO"));
    }

}
