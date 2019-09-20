package bots;

import engines.Action;
import engines.ActionList;
import engines.Deck;
import goals.Goal;
import goals.GoalType;
import goals.PandaGoal;
import parcels.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract  class MotherBot {

    protected String name;
    protected int pinkBambooNb;
    protected int greenBambooNb;
    protected int yellowBambooNb;
    protected ArrayList<Goal> goalList = new ArrayList<>();
    protected ArrayList<Goal> goalListValidated = new ArrayList<>();
    protected int totalScore;
    protected boolean winner;
    protected ArrayList<ImprovementList> improvementList = new ArrayList<>();
    protected int irrigationNb;

    protected MotherBot() {

        this.name = "";
        this.pinkBambooNb = 0;
        this.greenBambooNb = 0;
        this.yellowBambooNb = 0;
        this.totalScore = 0;
        this.winner = false;
        this.irrigationNb = 0;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPinkBambooNb() {
        return pinkBambooNb;
    }

    public void setPinkBambooNb(int pinkBambooNb) {
        this.pinkBambooNb = pinkBambooNb;
    }

    public int getGreenBambooNb() {
        return greenBambooNb;
    }

    public void setGreenBambooNb(int greenBambooNb) {
        this.greenBambooNb = greenBambooNb;
    }

    public int getYellowBambooNb() {
        return yellowBambooNb;
    }

    public void setYellowBambooNb(int yellowBambooNb) {
        this.yellowBambooNb = yellowBambooNb;
    }

    public ArrayList<Goal> getGoalList() {
        return goalList;
    }

    public void setGoalList(ArrayList<Goal> goalList) {
        this.goalList = goalList;
    }

    public void addGoal(Goal goal) {
        goalList.add(goal);
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public boolean getWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public ArrayList<Goal> getGoalListValidated() {
        return goalListValidated;
    }

    public void addGoalListValidated(Goal goal) {
        this.goalListValidated.add(goal);
    }

    public ArrayList<ImprovementList> getImprovementList() {
        return improvementList;
    }

    public void setImprovementList(ArrayList<ImprovementList> improvementList) {
        this.improvementList = improvementList;
    }

    public int getIrrigationNb() {
        return irrigationNb;
    }

    public void setIrrigationNb(int irrigationNb) {
        this.irrigationNb = irrigationNb;
    }


    /**
     * Créé une action pour le panda
     * @param destination => coordonnées de la parcel de destination
     * @return une action de type ActionPanda avec les coordonnées données
     */
    public Action pandaAction(int[] destination) {

        Action action = new Action(ActionList.ActionPanda, destination);
        return action;
    }

    /**
     * Créé une action pour le jardinier
     * @param destination => coordonnées de la parcel de destination
     * @return une action de type ActionGardener avec les coordonnées données
     */
    public Action gardenerAction(int[] destination) {

        Action action = new Action(ActionList.ActionGardener, destination);
        return action;
    }

    /**
     * Créé une action pour la pose de parcelles apres avoir choit laquelle correspond avec un des objectifs
     * @param freeParcel => liste des endroits ou l'on peut mettre une parcelle
     * @return une action de type ActionParcel avec les coordonnées données
     */
    public Action parcelAction(ArrayList<Integer[]> freeParcel) {

        Random r = new Random();
        int k = r.nextInt(freeParcel.size());
        int[] pos = new int[2];
        pos[0] = freeParcel.get(k)[0];
        pos[1] = freeParcel.get(k)[1];

        ArrayList<ClassicParcel> my3Parcel = Deck.getInstance().getRandomParcelList();
        if(my3Parcel.size() > 0) {
            Random randomGenerator = new Random();
            int index;
            index = randomGenerator.nextInt(my3Parcel.size());
            ClassicParcel chosenOne = my3Parcel.get(index);
            PandaGoal pG;
            for (Goal g : this.goalList) {
                if (g.getGoalType() == GoalType.PANDA) {
                    pG = (PandaGoal) g;
                    for (ClassicParcel cP : my3Parcel) {
                        if (cP.getColor() == pG.getColor()) {
                            chosenOne = cP;
                            break;
                        }
                    }
                }
            }
            ArrayList<Integer[]> freeParcelLoc = GameBoard.getInstance().freeParcelLocations();
            switch (chosenOne.getColor()) {
                case GREEN:
                    List<Parcel> rightColorG = GameBoard.getInstance().getExistingParcel().stream()
                            .filter(p -> p.getColor() == ColorList.GREEN)
                            .collect(Collectors.toList());
                    if (FreeXColor(freeParcelLoc, rightColorG) != null) {
                        pos[0] = FreeXColor(freeParcelLoc, rightColorG).getCoordinates()[0];
                        pos[1] = FreeXColor(freeParcelLoc, rightColorG).getCoordinates()[1];
                    }
                    break;
                case YELLOW:
                    List<Parcel> rightColorY = GameBoard.getInstance().getExistingParcel().stream()
                            .filter(p -> p.getColor() == ColorList.YELLOW)
                            .collect(Collectors.toList());
                    if (FreeXColor(freeParcelLoc, rightColorY) != null) {
                        pos[0] = FreeXColor(freeParcelLoc, rightColorY).getCoordinates()[0];
                        pos[1] = FreeXColor(freeParcelLoc, rightColorY).getCoordinates()[1];
                    }
                    break;
                case PINK:
                    List<Parcel> rightColorP = GameBoard.getInstance().getExistingParcel().stream()
                            .filter(p -> p.getColor() == ColorList.PINK)
                            .collect(Collectors.toList());
                    if (FreeXColor(freeParcelLoc, rightColorP) != null) {
                        pos[0] = FreeXColor(freeParcelLoc, rightColorP).getCoordinates()[0];
                        pos[1] = FreeXColor(freeParcelLoc, rightColorP).getCoordinates()[1];
                    }
                    break;
            }

            Action action = new Action(ActionList.ActionParcel, chosenOne, pos);

            return action;
        }else
            return new Action(ActionList.FreeFinishMyTurn);

    }

    /**
     * Pioche un objectif
     * @param goalType type d'objectif
     * @return une action de type ActionGoal
     */
    public Action goalAction(GoalType goalType) {
        return new Action(ActionList.ActionGoal, goalType);
    }

    /**Pioche une irrigation
     * @return une action de type ActionIrrigation
     */
    public Action irrigationAction() {
        return new Action(ActionList.ActionIrrigation);
    }

    /**
     * Passe le tour du Bot
     * @return une action de type FreeFinishMyTurn
     */
    public Action skipTurn() {

        Action action = new Action(ActionList.FreeFinishMyTurn);
        return action;

    }

    /**
     * Enlève l'étang d'une liste
     * @param listExistingParcel liste ou l'on veut enlever l'étang
     */
    public void RemovePond(ArrayList<Parcel> listExistingParcel) {

        Parcel delete = new Pond();
        for (Parcel p : listExistingParcel) {

            if (p.getClass() == Pond.class) {

                delete = p;

            }

        }
        listExistingParcel.remove(delete);

    }

    /**
     * Permet d'obtenir le type d'un objectif (utilisé pour la pioche d'objectifs)
     * @return le type de l'objectif
     */
    public GoalType getChosenGoalType() {

        ArrayList<GoalType> list = new ArrayList<>();
        for (Goal g : this.goalList) {
            list.add(g.getGoalType());
        }
        if (!list.contains(GoalType.PANDA) && Deck.getInstance().getGoalTypePossible().contains(GoalType.PANDA)) return GoalType.PANDA;
        else if (!list.contains(GoalType.GARDENER) && Deck.getInstance().getGoalTypePossible().contains(GoalType.GARDENER)) return GoalType.GARDENER;
        else if (!list.contains(GoalType.PARCEL) && Deck.getInstance().getGoalTypePossible().contains(GoalType.PARCEL)) return GoalType.PARCEL;
        else return Deck.getInstance().getGoalTypePossible().get(0);

    }

    /**
     * Verifie si un des objectif est rempli
     * @return l'objectif validé ou Null
     */
    public Goal CheckGoals() {

        ArrayList<Parcel> listExistingParcel = GameBoard.getInstance().getExistingParcel();
        for (Goal g : this.goalList) {
            if (g.getGoalType() == GoalType.PANDA) {
                if (g.checkGoal(this.getGreenBambooNb(), this.getPinkBambooNb(), this.getYellowBambooNb())) {
                    return g;
                }
            } else if (g.getGoalType() == GoalType.PARCEL) {
                if (g.checkGoal(listExistingParcel)) {
                    return g;
                }
            } else if (g.getGoalType() == GoalType.GARDENER) {
                if (g.checkGoal()) {
                    return g;
                }
            }
        }

        return null;

    }

    /**
     * Fait le croisement entre le tableau des parcelles libres et  le tableau des parcelles de bonnes couleur
     * @param freeParcelLoc tableau des parcelles libres
     * @param rightColor tableau des parcelles de bonnes couleur
     * @return un tableau des parcelles résultant du croisement des 2 tableaux
     */
    public ClassicParcel FreeXColor(ArrayList<Integer[]> freeParcelLoc,List<Parcel> rightColor){
        for (Parcel p : rightColor) {
            for (Integer[] i : freeParcelLoc) {
                if (GameBoard.getInstance().getNeighbor(i[0],i[1]).contains(p)) return (ClassicParcel)p;
            }
        }
        return null;
    }

    /**
     * Reset le bot
     */
    public void ResetBot(){

        this.pinkBambooNb = 0;
        this.greenBambooNb = 0;
        this.yellowBambooNb = 0;
        this.totalScore = 0;
        this.winner = false;
        this.irrigationNb = 0;
        
    }

    public abstract Action itsMyTurn(int turnNb, int actionNb, ArrayList<ActionList> actionList);
    public abstract Action BehaviorTree(int turnNb, int actionNb, ArrayList<ActionList> actionList);
}
