package bots;

import org.junit.Test;
import parcels.GameBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBarnabot {

    Barnabot barnabot;
    GameBoard board;


    @Test
    public void createBarnabot() {
        barnabot = new Barnabot();
        assertEquals("Barnabot", barnabot.toString());
    }

    @Test
    public void getterBarnabot() {
        barnabot = new Barnabot();
        assertEquals(0, barnabot.getPinkBambooNb());
        assertEquals(0, barnabot.getGreenBambooNb());
        assertEquals(0, barnabot.getYellowBambooNb());
        assertEquals(0, barnabot.getTotalScore());
        assertEquals(false, barnabot.getWinner());
        assertEquals(0, barnabot.getIrrigationNb());

    }@Test
    public void setterBarnabot() {
        barnabot = new Barnabot();
        barnabot.setPinkBambooNb(7);
        barnabot.setGreenBambooNb(1);
        barnabot.setYellowBambooNb(5);
        barnabot.setTotalScore(177);
        barnabot.setWinner(true);
        barnabot.setIrrigationNb(8);
        assertEquals(7, barnabot.getPinkBambooNb());
        assertEquals(1, barnabot.getGreenBambooNb());
        assertEquals(5, barnabot.getYellowBambooNb());
        assertEquals(177, barnabot.getTotalScore());
        assertEquals(true, barnabot.getWinner());
        assertEquals(8, barnabot.getIrrigationNb());
    }

}
