package bots;

import org.junit.Test;
import parcels.GameBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestZerobot {
    Zerobot zero;
    GameBoard board;


    @Test
    public void createZerobot() {
        zero = new Zerobot();
        assertEquals("Zerobot", zero.toString());
    }

    @Test
    public void getterMarcel() {
        zero = new Zerobot();
        assertEquals(0, zero.getPinkBambooNb());
        assertEquals(0, zero.getGreenBambooNb());
        assertEquals(0, zero.getYellowBambooNb());
        assertEquals(0, zero.getTotalScore());
        assertEquals(false, zero.getWinner());
        assertEquals(0, zero.getIrrigationNb());

    }@Test
    public void setterMarcel() {
        zero = new Zerobot();
        zero.setPinkBambooNb(3);
        zero.setGreenBambooNb(2);
        zero.setYellowBambooNb(4);
        zero.setTotalScore(200);
        zero.setWinner(true);
        zero.setIrrigationNb(7);
        assertEquals(3, zero.getPinkBambooNb());
        assertEquals(2, zero.getGreenBambooNb());
        assertEquals(4, zero.getYellowBambooNb());
        assertEquals(200, zero.getTotalScore());
        assertEquals(true, zero.getWinner());
        assertEquals(7, zero.getIrrigationNb());
    }

}
