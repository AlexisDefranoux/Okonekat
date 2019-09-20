package parcels;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestPond {

    Pond p;

    @Test
    public void createPond() {
        p = new Pond();
        p.growBamboo();
        p.eatBamboo();
        assertArrayEquals(new int[]{27,27}, p.getCoordinates());
        assertEquals(0, p.getHeightBamboo());
        assertEquals(null, p.getColor());
        assertEquals(false, p.getIrrigation());
    }
}
