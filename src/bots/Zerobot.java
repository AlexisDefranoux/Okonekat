package bots;

import characters.Gardener;
import characters.Panda;
import engines.Action;
import engines.ActionList;
import engines.Deck;
import engines.WeatherList;
import goals.Goal;
import goals.GoalType;
import goals.PandaGoal;
import parcels.*;
import parcels.Parcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Zerobot extends MotherBot {

    public Zerobot() {
        super();
        this.name = "Zerobot";
    }

    public String toString() {
        return this.name;
    }

    private static final Random r = new Random();


    /**
     * Gere la meteo
     * @param turnNb le numero du tour
     * @param actionNb le nombre d'actions restantes
     * @param actionList la liste des actions restantes
     * @return une action
     */
    @Override
    public Action itsMyTurn(int turnNb, int actionNb, ArrayList<ActionList> actionList) {

        if (actionList.contains(ActionList.WeatherAddBamboo)) {
            ArrayList<Parcel> list = GameBoard.getInstance().getExistingParcel();
            list.remove(GameBoard.getInstance().getBoard()[27][27]);
            int size = list.size();
            if(size > 0) {
                Parcel p = list.get(r.nextInt(size));
                return new Action(ActionList.WeatherAddBamboo, (ClassicParcel) p);
            }
        }

        if (actionList.contains(ActionList.WeatherMovePanda)) {
            ArrayList<Parcel> list = GameBoard.getInstance().getExistingParcel();
            list.remove(GameBoard.getInstance().getBoard()[27][27]);
            int size = list.size();
            if(size > 0) {
                int[] coord = list.get(r.nextInt(size)).getCoordinates();
                return new Action(ActionList.WeatherMovePanda, coord);
            }
            }

        if (actionList.contains(ActionList.WeatherChooseImprovement)) {
            ArrayList<ImprovementList> list = Deck.getInstance().getImprovementList();
            int size = list.size();
            if (size > 0) {
                ImprovementList improvement = list.get(r.nextInt(size));
                return new Action(ActionList.WeatherChooseImprovement, improvement);
            }
        }

        if (actionList.contains(ActionList.WeatherChooseWeather)) {
            WeatherList myWeather = WeatherList.CLOUDS;
            ArrayList<WeatherList> list = myWeather.getWeatherPossible();
            int size = list.size();
            if(size > 0) {
                myWeather = list.get(r.nextInt(size));
                return new Action(ActionList.WeatherChooseWeather, myWeather);
            }
        }

        if (actionNb != 0) {
            if(CheckGoals() != null) return new Action(ActionList.FreeCheckActionGoal, CheckGoals());

            if(irrigationNb > 0) {
                ArrayList<int[]> list = IrrigationBoard.getInstance().irrigationFreeLocation();
                int size = list.size();
                if(size > 0)
                    return new Action(ActionList.FreePutIrrigation, list.get(r.nextInt(size)));
            }

            int len = improvementList.size();
            if(len > 0) {
                ArrayList<ClassicParcel> list = GameBoard.getInstance().getAllParcelPossibleForImprovement();
                int size = list.size();
                if(size > 0)
                    return new Action(ActionList.FreePutImprovement, improvementList.get(r.nextInt(len)), list.get(r.nextInt(size)));
            }

            if (this.goalList.size() < 3 && actionList.contains(ActionList.ActionGoal)) {
                return new Action(ActionList.ActionGoal, getChosenGoalType());
            } else {
                return this.BehaviorTree(turnNb, actionNb, actionList);
            }
        }else{
            return new Action(ActionList.FreeFinishMyTurn);
        }
    }


    /**
     * Choisit quelle action le bot va executer
     * @param turnNb le numero du tour
     * @param actionNb le nombre d'actions restantes
     * @param actionList la liste des actions restantes
     * @return une action
     */
    @Override
    public Action BehaviorTree(int turnNb, int actionNb, ArrayList<ActionList> actionList) {
        ArrayList<Parcel> parcelsList;
        ArrayList<Integer[]> placesList;
        ArrayList<GoalType> goalsList;

        int size = actionList.size();
        ActionList action = actionList.get(r.nextInt(size));
        switch(action) {
            case ActionPanda:
                parcelsList = Panda.getInstance().accessibleParcels();
                if(parcelsList.size() != 0)
                    return pandaAction(parcelsList.get(r.nextInt(parcelsList.size())).getCoordinates());
            case ActionGardener:
                parcelsList = Gardener.getInstance().accessibleParcels();
                if(parcelsList.size() != 0)
                    return gardenerAction(parcelsList.get(r.nextInt(parcelsList.size())).getCoordinates());
            case ActionParcel:
                placesList = GameBoard.getInstance().freeParcelLocations();
                if(placesList.size() != 0)
                    return parcelAction(placesList);
            case ActionGoal:
                goalsList = Deck.getInstance().getGoalTypePossible();
                if(goalsList.size() != 0)
                    return goalAction(goalsList.get(r.nextInt(goalsList.size())));
            case ActionIrrigation:
                return irrigationAction();
            default:
                return skipTurn();
        }

    }
}
