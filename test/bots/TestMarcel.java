package bots;

import org.junit.Test;
import parcels.GameBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMarcel {

    Marcel marcel;
    GameBoard board;


    @Test
    public void createMarcel() {
        marcel = new Marcel();
        assertEquals("Marcel", marcel.toString());
    }

    @Test
    public void getterMarcel() {
        marcel = new Marcel();
        assertEquals(0, marcel.getPinkBambooNb());
        assertEquals(0, marcel.getGreenBambooNb());
        assertEquals(0, marcel.getYellowBambooNb());
        assertEquals(0, marcel.getTotalScore());
        assertEquals(false, marcel.getWinner());
        assertEquals(0, marcel.getIrrigationNb());

    }@Test
    public void setterMarcel() {
        marcel = new Marcel();
        marcel.setPinkBambooNb(3);
        marcel.setGreenBambooNb(2);
        marcel.setYellowBambooNb(4);
        marcel.setTotalScore(200);
        marcel.setWinner(true);
        marcel.setIrrigationNb(7);
        assertEquals(3, marcel.getPinkBambooNb());
        assertEquals(2, marcel.getGreenBambooNb());
        assertEquals(4, marcel.getYellowBambooNb());
        assertEquals(200, marcel.getTotalScore());
        assertEquals(true, marcel.getWinner());
        assertEquals(7, marcel.getIrrigationNb());
    }

}
