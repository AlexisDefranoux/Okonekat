package engines;

import goals.GoalType;
import org.junit.Before;
import org.junit.Test;
import parcels.ImprovementList;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestDeck {

    @Before
    public void resetDeck() {
        Deck.reset();
    }

    @Test
    public void testCreateDeck() {

        assertEquals(3,Deck.getInstance().getGoalTypePossible().size());
        assertEquals(9,Deck.getInstance().getImprovementNb());
        assertEquals(GoalType.PANDA,Deck.getInstance().getRandomGoal(GoalType.PANDA).getGoalType());

        ArrayList<ImprovementList> i = new ArrayList<>();
        i.add(ImprovementList.enclosure);
        i.add(ImprovementList.pond);
        i.add(ImprovementList.fertilizer);
        assertEquals(i, Deck.getInstance().getImprovementList());

        assertEquals(3,Deck.getInstance().getRandomParcelList().size());
        assertEquals(3,Deck.getInstance().getRandomGoalList().size());
    }

}
