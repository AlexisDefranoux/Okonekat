package engines;

import goals.GoalType;
import goals.PandaGoal;
import org.junit.Test;
import parcels.ClassicParcel;
import parcels.ColorList;

import static org.junit.Assert.*;

public class TestAction {

    ClassicParcel parcel;
    Action action;

    @Test
    public void createActionParcel() {
        parcel = new ClassicParcel(ColorList.YELLOW);
        int[] coordinates ={5,5};
        action = new Action(ActionList.ActionParcel, parcel, coordinates);
        assertEquals(parcel, action.getParcel());
    }

    @Test
    public void createActionPandaGardener() {
        int[] coordinates ={5,5};
        action = new Action(ActionList.ActionPanda, coordinates);
        assertEquals(coordinates, action.getCoordinates());
    }

    @Test
    public void createActionSkipTurn() {
        action = new Action(ActionList.FreeFinishMyTurn);
        assertEquals(ActionList.FreeFinishMyTurn, action.getActionList());
    }

    @Test
    public void createActionGoal() {
        action = new Action(ActionList.ActionGoal, GoalType.PANDA);
        assertEquals(GoalType.PANDA, action.getGoalType());
    }

    @Test
    public void testActionCheckGoal() {
        PandaGoal g = new PandaGoal(4, ColorList.PINK);
        action = new Action(ActionList.FreeCheckActionGoal, g);
        assertEquals(g, action.getGoal());
    }

    @Test
    public void createActionList() {
        assertEquals("déplacer le panda.", ActionList.ActionPanda.toString());
    }
}
