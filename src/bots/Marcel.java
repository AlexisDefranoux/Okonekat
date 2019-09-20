package bots;

import characters.Gardener;
import characters.Panda;
import engines.Action;
import engines.ActionList;
import engines.Deck;
import engines.WeatherList;
import goals.GardenerGoal;
import goals.Goal;
import goals.GoalType;
import goals.PandaGoal;
import parcels.*;

import java.util.ArrayList;
import java.util.Random;

public class Marcel extends MotherBot {


    public Marcel() {
        super();
        this.name = "Marcel";
    }


    /**
     * Donne le nom du Bot
     * @return le nom du Bot
     */
    public String toString() {
        return this.name;
    }


    /**
     * Gere la meteo
     * @param turnNb le numero du tour
     * @param actionNb le nombre d'actions restantes
     * @param actionList la liste des actions restantes
     * @return une action
     */
    @Override
    public Action itsMyTurn(int turnNb, int actionNb, ArrayList<ActionList> actionList) {

        Action a = null;
        switch(actionList.get(0)){
            case WeatherAddBamboo:
                if (GameBoard.getInstance().whereCanGrow().size() > 0) {
                    Random r = new Random();
                    int rand = r.nextInt(GameBoard.getInstance().whereCanGrow().size());
                    a = new Action(ActionList.WeatherAddBamboo, GameBoard.getInstance().whereCanGrow().get(rand));
                }
                break;
            case WeatherMovePanda:
                a = pandaWeather();
                break;
            case WeatherChooseImprovement:
                Random r = new Random();
                ArrayList<ImprovementList> listImprovements = Deck.getInstance().getImprovementList();
                if (listImprovements.size() > 0) {
                    ImprovementList improvement = listImprovements.get(r.nextInt(listImprovements.size()));
                    a = new Action(ActionList.WeatherChooseImprovement, improvement);
                }
                break;
            case WeatherChooseWeather:
                a = new Action(ActionList.WeatherChooseWeather, WeatherList.WIND);
                break;
        }
        if(a == null) {
            if (CheckGoals() != null){
                a = new Action(ActionList.FreeCheckActionGoal, CheckGoals());
            }else {
                if (this.goalList.size() < 3 && actionList.contains(ActionList.ActionGoal)) {
                    a = new Action(ActionList.ActionGoal, getChosenGoalType());
                } else {
                    a = BehaviorTree(turnNb, actionNb, actionList);
                }
            }
        }
        return a;
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

        ArrayList<Parcel> bambooPosition = Panda.getInstance().findBamboo();
        bambooPosition.remove(Panda.getInstance().getPosition());

        ArrayList<Parcel> listExistingParcel = Gardener.getInstance().findBamboo();
        listExistingParcel.remove(Gardener.getInstance().getPosition());
        this.RemovePond(listExistingParcel);

        if (irrigationNb > 0) {
            return putIrrigation();
        } else {
            if (bambooPosition.size() > 0 && actionList.contains(ActionList.ActionPanda)) {
                PandaGoal pG = new PandaGoal(0, ColorList.MULTICOLOR);
                for (Goal g : this.goalList) {
                    if (g.getGoalType() == GoalType.PANDA) {
                        pG = (PandaGoal) g;
                        break;
                    }
                }
                ArrayList<ClassicParcel> goodParcel = new ArrayList<>();
                for (Parcel p : bambooPosition) {
                    if (p.getColor() == pG.getColor()) {
                        goodParcel.add((ClassicParcel) p);
                    }
                }

                if (goodParcel.size() > 1) {
                    if (goodParcel.contains(Panda.getInstance().getPosition()))
                        goodParcel.remove(Panda.getInstance().getPosition());
                    //System.out.println(goodParcel.get(0).getCoordinates()[0] + " " + goodParcel.get(0).getCoordinates()[1]);
                    return pandaAction(goodParcel.get(0).getCoordinates());
                } else {
                    if (bambooPosition.size() == 1) {
                        //System.out.println(bambooPosition.get(0).getCoordinates()[0] + " " + bambooPosition.get(0).getCoordinates()[1]);
                        return pandaAction(bambooPosition.get(0).getCoordinates());

                    } else {
                        //System.out.println(bambooPosition.get(1).getCoordinates()[0] + " " + bambooPosition.get(1).getCoordinates()[1]);
                        return pandaAction(bambooPosition.get(1).getCoordinates());

                    }
                }
            } else {
                //int[] test = {27,27};
            /*if(((Panda.getInstance().getPosition().coordinates[0] != test[0] && Panda.getInstance().getPosition().coordinates[1] != test[1])
                    || (Panda.getInstance().getPosition().coordinates[0] == test[0] && Panda.getInstance().getPosition().coordinates[1] != test[1])
                    || (Panda.getInstance().getPosition().coordinates[0] != test[0] && Panda.getInstance().getPosition().coordinates[1] == test[1]))
                    && bambooPosition.size()==0 && turnNb > 1 && actionList.contains(ActionList.ActionPanda)) {

                int[] coordinates = {27,27};
                return pandaAction(coordinates);

            }else*/
                if (listExistingParcel.size() >= 1 && actionList.contains(ActionList.ActionGardener)) {

                    return GardenerGPS(listExistingParcel);

                } else if (actionList.contains(ActionList.ActionIrrigation) && irrigationNb > 2){
                    return new Action(ActionList.ActionIrrigation);

                } else if (Deck.getInstance().getRandomParcelList().size() != 0 && actionList.contains(ActionList.ActionParcel)) {
                ArrayList<Integer[]> freeParcel = GameBoard.getInstance().freeParcelLocations();

                return parcelAction(freeParcel);

                }else if(irrigationNb < 0) {
                    return irrigationAction();
                }else{
                    return skipTurn();
                }
            }

        }
    }

    /**
     * Gere le deplacement du Panda lors de l'orage
     * @return une Action avec la parcelle choisie selon les objectif a accomplir
     */
    public Action pandaWeather() {

        ArrayList<Parcel> bambooPosition = GameBoard.getInstance().whereCanEat();
        bambooPosition.remove(Panda.getInstance().getPosition());

        PandaGoal pG = new PandaGoal(0, ColorList.MULTICOLOR);
        for (Goal g : this.goalList) {
            if (g.getGoalType() == GoalType.PANDA) {
                pG = (PandaGoal) g;
                break;
            }
        }
        ArrayList<ClassicParcel> goodParcel = new ArrayList<>();
        for (Parcel p : bambooPosition){
            if(p.getColor() == pG.getColor()){
                goodParcel.add((ClassicParcel)p);
            }
        }
        if (goodParcel.size() > 1){
            if (goodParcel.contains(Panda.getInstance().getPosition())) goodParcel.remove(Panda.getInstance().getPosition());
            return pandaAction(goodParcel.get(0).getCoordinates());
        } else if (bambooPosition.size() != 0){
            if (bambooPosition.size() == 1) {
                return pandaAction(bambooPosition.get(0).getCoordinates());

            } else {
                return pandaAction(bambooPosition.get(1).getCoordinates());
            }

        }else{
            ArrayList<Parcel> existingParcel = GameBoard.getInstance().getExistingParcel();
            existingParcel.remove(Panda.getInstance().getPosition());
            Random r = new Random();
            int rand = r.nextInt(existingParcel.size());
            return new Action(ActionList.ActionPanda, existingParcel.get(rand).getCoordinates());
        }
    }


    /**
     * Gere le deplacement du Jardinier
     * @return une Action avec la parcelle choisie selon les objectif a accomplir
     */
    public Action GardenerGPS(ArrayList<Parcel> listExistingParcel){
        GardenerGoal gG = new GardenerGoal(0,2,3,ImprovementList.fertilizer,ColorList.PINK);
        for (Goal g : this.goalList) {
            if (g.getGoalType() == GoalType.GARDENER) {
                gG = (GardenerGoal) g;
                break;
            }
        }
        ArrayList<ClassicParcel> goodParcel = new ArrayList<>();
        for (Parcel p : listExistingParcel){
            if(p.getColor() == gG.getColor()){
                goodParcel.add((ClassicParcel)p);
            }
        }
        if (goodParcel.size() > 1){
            return gardenerAction(goodParcel.get(0).getCoordinates());
        } else if (listExistingParcel.size() != 0){
            if (listExistingParcel.size() == 1) {
                return gardenerAction(listExistingParcel.get(0).getCoordinates());

            } else {
                return gardenerAction(listExistingParcel.get(1).getCoordinates());
            }

        }else{
            Random r = new Random();
            int rand = r.nextInt(listExistingParcel.size());
            return gardenerAction(listExistingParcel.get(rand).getCoordinates());
        }
    }

    /**
     * Permet a Marcel de poser une irrigation
     * @return une action de type FreePutIrrigation avec sa destination
     */
    private Action putIrrigation(){
        ArrayList<int[]> irrigation = IrrigationBoard.getInstance().irrigationFreeLocation();
            if (irrigation.size() > 0) {
                Random r = new Random();
                int rand = r.nextInt(irrigation.size());
                int[] chosen = irrigation.get(rand);
                if (irrigation != null)
                    return new Action(ActionList.FreePutIrrigation, chosen);
            }
        return null;
    }

}
