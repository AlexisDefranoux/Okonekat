package bots;

import engines.Action;
import engines.ActionList;
import goals.*;
import org.junit.Test;
import parcels.ColorList;
import parcels.GameBoard;
import parcels.ImprovementList;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMotherBot {

    MotherBot bot;
    GameBoard board;


    @Test
    public void createBot() {
        bot = new MotherBot() {
            @Override
            public Action itsMyTurn(int turnNb, int actionNb, ArrayList<ActionList> actionList) {
                return null;
            }

            @Override
            public Action BehaviorTree(int turnNb, int actionNb, ArrayList<ActionList> actionList) {
                return null;
            }
        };
        assertEquals("", bot.getName());
    }

    @Test
    public void getterBot() {
        bot = new MotherBot() {
            @Override
            public Action itsMyTurn(int turnNb, int actionNb, ArrayList<ActionList> actionList) {
                return null;
            }

            @Override
            public Action BehaviorTree(int turnNb, int actionNb, ArrayList<ActionList> actionList) {
                return null;
            }
        };
        assertEquals(0, bot.getPinkBambooNb());
        assertEquals(0, bot.getGreenBambooNb());
        assertEquals(0, bot.getYellowBambooNb());
        assertEquals(0, bot.getTotalScore());
        assertEquals(false, bot.getWinner());
        assertEquals(0, bot.getIrrigationNb());

    }@Test
    public void setterBot() {
        bot = new MotherBot() {
            @Override
            public Action itsMyTurn(int turnNb, int actionNb, ArrayList<ActionList> actionList) {
                return null;
            }

            @Override
            public Action BehaviorTree(int turnNb, int actionNb, ArrayList<ActionList> actionList) {
                return null;
            }
        };
        bot.setPinkBambooNb(3);
        bot.setGreenBambooNb(2);
        bot.setYellowBambooNb(4);
        bot.setTotalScore(200);
        bot.setWinner(true);
        bot.setIrrigationNb(7);
        assertEquals(3, bot.getPinkBambooNb());
        assertEquals(2, bot.getGreenBambooNb());
        assertEquals(4, bot.getYellowBambooNb());
        assertEquals(200, bot.getTotalScore());
        assertEquals(true, bot.getWinner());
        assertEquals(7, bot.getIrrigationNb());
    }


    @Test
    public void TestActionPanda(){
        bot = new MotherBot() {
            @Override
            public Action itsMyTurn(int turnNb, int actionNb, ArrayList<ActionList> actionList) {
                return null;
            }

            @Override
            public Action BehaviorTree(int turnNb, int actionNb, ArrayList<ActionList> actionList) {
                return null;
            }
        };

        int[] tab = {0,1};
        Action test = new Action(ActionList.ActionPanda,tab);
        assertEquals(test.getActionList(),bot.pandaAction(tab).getActionList());
        assertEquals(test.getCoordinates(),bot.pandaAction(tab).getCoordinates());
    }

    @Test
    public void TestActionGardener(){
        bot = new MotherBot() {
            @Override
            public Action itsMyTurn(int turnNb, int actionNb, ArrayList<ActionList> actionList) {
                return null;
            }

            @Override
            public Action BehaviorTree(int turnNb, int actionNb, ArrayList<ActionList> actionList) {
                return null;
            }
        };

        int[] tab = {0,1};
        Action test = new Action(ActionList.ActionGardener,tab);
        assertEquals(test.getActionList(),bot.gardenerAction(tab).getActionList());
        assertEquals(test.getCoordinates(),bot.gardenerAction(tab).getCoordinates());
    }

    @Test
    public void TestSkipTurn(){
        bot = new MotherBot() {
            @Override
            public Action itsMyTurn(int turnNb, int actionNb, ArrayList<ActionList> actionList) {
                return null;
            }

            @Override
            public Action BehaviorTree(int turnNb, int actionNb, ArrayList<ActionList> actionList) {
                return null;
            }
        };

        Action test = new Action(ActionList.FreeFinishMyTurn);
        assertEquals(test.getActionList(),bot.skipTurn().getActionList());
    }

    @Test
    public void TestGetChosenGoalType(){
        bot = new MotherBot() {
            @Override
            public Action itsMyTurn(int turnNb, int actionNb, ArrayList<ActionList> actionList) {
                return null;
            }

            @Override
            public Action BehaviorTree(int turnNb, int actionNb, ArrayList<ActionList> actionList) {
                return null;
            }
        };

        bot.addGoal(new PandaGoal(5, ColorList.PINK));
        bot.addGoal(new GardenerGoal(3, 4, 1, ImprovementList.fertilizer, ColorList.GREEN));

        assertEquals(GoalType.PARCEL,bot.getChosenGoalType());

        bot.addGoal(new ParcelGoal(3, new Pattern(Form.STRAIGHT, ColorList.YELLOW)));

        assertEquals(GoalType.PANDA,bot.getChosenGoalType());

    }

    @Test
    public void TestCheckGoal(){
        bot = new MotherBot() {
            @Override
            public Action itsMyTurn(int turnNb, int actionNb, ArrayList<ActionList> actionList) {
                return null;
            }

            @Override
            public Action BehaviorTree(int turnNb, int actionNb, ArrayList<ActionList> actionList) {
                return null;
            }
        };

        bot.addGoal(new PandaGoal(5, ColorList.PINK));
        bot.addGoal(new GardenerGoal(3, 4, 1, ImprovementList.fertilizer, ColorList.GREEN));
        bot.addGoal(new ParcelGoal(3, new Pattern(Form.STRAIGHT, ColorList.YELLOW)));

        assertEquals(null,bot.CheckGoals());

        bot.setPinkBambooNb(5);

        assertEquals(GoalType.PANDA,bot.CheckGoals().getGoalType());

    }
}
