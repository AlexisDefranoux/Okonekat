package bots;

import bots.MotherBot;
import characters.Gardener;
import characters.Panda;
import engines.*;
import goals.GardenerGoal;
import goals.Goal;
import goals.GoalType;
import goals.PandaGoal;
import parcels.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class Barnabot extends MotherBot {

    public Barnabot(){
        super();
        this.name = "Barnabot";
    }

    public String toString(){
        return this.name;
    }

    @Override
    public Action itsMyTurn(int turnNb, int actionNb, ArrayList<ActionList> actionList) {
        Action chosen = null;
        switch(actionList.get(0)){
            case WeatherAddBamboo:
                if(GameBoard.getInstance().whereCanGrow().size() > 0)
                    chosen = new Action(ActionList.WeatherAddBamboo, randomParcel(GameBoard.getInstance().whereCanGrow()));
                break;
            case WeatherMovePanda:
                chosen = specialCheckBestPanda(sortGoals(GoalType.PANDA));
                break;
            case WeatherChooseImprovement:
                ArrayList<ImprovementList> iprovements = Deck.getInstance().getImprovementList();
                chosen = new Action(ActionList.WeatherChooseImprovement, iprovements.get(0));
                break;
            case WeatherChooseWeather:
                chosen = new Action(ActionList.WeatherChooseWeather, WeatherList.SUN);
                break;
        }
        if(chosen == null)
            chosen = BehaviorTree(turnNb, actionNb, actionList);
        return chosen;
    }

    /**
     *
     * @param turnNb --> Nuuméro du tour actuel.
     * @param actionNb --> Nombre d'actions disponibles.
     * @param actionList --> Liste des actions disponibles.
     * @return Une action choisie selon l'arbre de comportement.
     */
    @Override
    public Action BehaviorTree(int turnNb, int actionNb, ArrayList<ActionList> actionList) {

        Action chosenAction = this.focusPanda(actionList);
        if (this.CheckGoals() != null) {
            return new Action(ActionList.FreeCheckActionGoal, CheckGoals());
        } else {
            if (chosenAction != null) {
                return chosenAction;
            } else {
                chosenAction = this.focusGardener(actionList);
                if (chosenAction != null) {
                    return chosenAction;
                } else {
                    chosenAction = this.putRandomParcel(actionList);
                    if (chosenAction != null) {
                        return chosenAction;
                    } else {
                        chosenAction = this.putRandomIrrigation(actionList);
                        if (chosenAction != null) {
                            return chosenAction;
                        } else {
                            chosenAction = this.pickIrrigation(actionList);
                            if (chosenAction != null) {
                                return chosenAction;
                            } else {
                                chosenAction = this.fullRandom(actionList);
                                if(chosenAction != null){
                                    return chosenAction;
                                }else{
                                    return new Action(ActionList.FreeFinishMyTurn);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Séléctionne la meilleure action Panda disponible en fonction des ses objectifs, des actions disponibles et de la partie en cours
     * @param available --> liste des actions disponibles
     * @return la meilleure actionPanda disponible, pioche un objectif panda ou null si aucun des deux disponible
     */
    private Action focusPanda(ArrayList<ActionList> available){
        Action chosenPanda;
        if(sortGoals(GoalType.PANDA).size() > 0){
            if(available.contains(ActionList.ActionPanda)){
                chosenPanda = checkBestPanda(sortGoals(GoalType.PANDA));
                if(chosenPanda != null){
                    return chosenPanda;
                }else{
                    if(available.contains(ActionList.ActionGardener)) {
                        chosenPanda = gardenerForPandaGoal();
                        if (chosenPanda != null) {
                            return chosenPanda;
                        }
                    }
                    return null;
                }
            }else{
                return null;
            }
        }else{
            if(available.contains(ActionList.ActionGoal)){
                ArrayList<GoalType> dispo = Deck.getInstance().getGoalTypePossible();
                if(dispo.contains(GoalType.PANDA))
                    return new Action(ActionList.ActionGoal, GoalType.PANDA);
                else
                    return null;
            }else{
                return null;
            }
        }
    }

    /**
     *
     * @return La meilleure Action Jardinier en fonction du jeu pour compléter le plus optimisé des PandaGoals.
     */
    private Action gardenerForPandaGoal(){
        ArrayList<PandaGoal> pandaGoals = getPandaGoals();
        int score = 5;
        ClassicParcel toGo = null;
        for(PandaGoal pg: pandaGoals){
            int tempScore = calculateScorePanda(pg);
            ClassicParcel tempToGo = bestGardenerForPanda(pg);
            if(tempScore < score && tempToGo != null){
                if(tempToGo.irrigated) {
                    score = tempScore;
                    toGo = tempToGo;
                }
            }
        }
        if(toGo != null){
            return new Action(ActionList.ActionGardener, toGo.getCoordinates());
        }
        return null;
    }

    /*private int calculateGardenerForPanda(PandaGoal pg){
        ArrayList<ClassicParcel> crossed = crossArrays(Gardener.getInstance().accessibleParcels(), Panda.getInstance().accessibleParcels());
        for(ClassicParcel cp: crossed){
            if(pg.getColor() == ColorList.MULTICOLOR){

                }
            }
        }
        return 0;
    }*/

    /**
     *
     * @param a Liste 1.
     * @param b Liste 2.
     * @return Une liste contenant a inter b.
     */
    private ArrayList<ClassicParcel> crossArrays(ArrayList<Parcel> a, ArrayList<Parcel> b){
        ArrayList<ClassicParcel> crossed = new ArrayList<>();
        for(Parcel p: a){
            for(Parcel pp: b){
                if(p.equals(pp) && p.getClass() == ClassicParcel.class){
                    crossed.add((ClassicParcel) p);
                }
            }
        }
        return crossed;
    }

    /**
     *
     * @param pg Le goal sur lequel se baser.
     * @return La meilleure case sur laquelle déplacer le jardinier afin de compléter pg. null si inexistant.
     */
    private ClassicParcel bestGardenerForPanda(PandaGoal pg){
        ArrayList<ClassicParcel> crossed = crossArrays(Gardener.getInstance().accessibleParcels(), Panda.getInstance().accessibleParcels());
        for(ClassicParcel cp: crossed){
            if(cp.getImprovement() != ImprovementList.enclosure) {
                if (pg.getColor() == ColorList.MULTICOLOR) {
                    if (greenBambooNb == 0 && cp.getColor() == ColorList.GREEN && cp.irrigated) {
                        return cp;
                    }

                    if (pinkBambooNb == 0 && cp.getColor() == ColorList.PINK && cp.irrigated) {
                        return cp;
                    }

                    if (yellowBambooNb == 0 && cp.getColor() == ColorList.YELLOW && cp.irrigated) {
                        return cp;
                    }
                } else {
                    if (cp.getColor() == pg.getColor() && cp.irrigated) {
                        return cp;
                    }
                }
            }
        }
        return null;
    }

    /**
     *
     * @return Renvoie la liste des goals Panda que possède le bot.
     */
    private ArrayList<PandaGoal> getPandaGoals(){
        ArrayList<Goal> pandaNotPanda = sortGoals(GoalType.PANDA);
        ArrayList<PandaGoal> pandaIsPanda = new ArrayList<>();
        for (Goal g : pandaNotPanda) {
            if (g.getClass() == PandaGoal.class) {
                pandaIsPanda.add((PandaGoal) g);
            }
        }
        return pandaIsPanda;
    }

    private Action focusGardener(ArrayList<ActionList> available){
        Action chosenGardener;
        if(sortGoals(GoalType.GARDENER).size() > 0){
            if(available.contains(ActionList.ActionGardener)){
                chosenGardener = checkBestGardener(sortGoals(GoalType.GARDENER));
                if(chosenGardener != null){
                    return chosenGardener;
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }else{
            if(available.contains(ActionList.ActionGoal)){
                ArrayList<GoalType> dispo = Deck.getInstance().getGoalTypePossible();
                if(dispo.contains(GoalType.GARDENER))
                    return new Action(ActionList.ActionGoal, GoalType.GARDENER);
                else
                    return null;
            }else{
                return null;
            }
        }
    }

    /**
     *
     * @param available Liste des actions disponibles.
     * @return --> Une actionParcel en lien avec l'objectif Panda le plus proche d'être réalisé.
     */
    private Action putRandomParcel(ArrayList<ActionList> available){

        if (available.contains(ActionList.ActionParcel)) {
            ArrayList<ClassicParcel> pickOne = Deck.getInstance().getRandomParcelList();
            int finalScore = 5;
            Action chosen = null;
            ClassicParcel finalParcel = null;
            ArrayList<Goal> pandaNotPanda = sortGoals(GoalType.PANDA);
            ArrayList<PandaGoal> pandaIsPanda = new ArrayList<>();
            for (Goal g : pandaNotPanda) {
                if (g.getClass() == PandaGoal.class) {
                    pandaIsPanda.add((PandaGoal) g);
                }
            }
            for (PandaGoal pg : pandaIsPanda) {
                int score = calculateScorePanda(pg);
                if (score < finalScore && containsColor(pickOne, pg.getColor()) != null) {
                    if(containsColor(pickOne, pg.getColor()).getImprovement() != ImprovementList.enclosure) {
                        finalScore = score;
                        finalParcel = containsColor(pickOne, pg.getColor());
                    }
                }
            }
            if (finalParcel != null) {
                return new Action(ActionList.ActionParcel, finalParcel, randomFreeLocation());
            } else if(pickOne.isEmpty()){
                return null;
            }else{
                return new Action(ActionList.ActionParcel, randomParcel(pickOne), randomFreeLocation());
            }
        }
        /*if(available.contains(ActionList.ActionParcel)){
            return this.parcelAction(GameBoard.getInstance().freeParcelLocations());
        }*/
        return null;
    }

    /**
     *
     * @return Renvoie des coodronnées aléatoires disponibles pour poser une parcelle.
     */
    private int[] randomFreeLocation(){
        ArrayList<Integer[]> freeLocations = GameBoard.getInstance().freeParcelLocations();
        Random r = new Random();
        int k = r.nextInt(freeLocations.size());
        int[] chosen = {freeLocations.get(k)[0], freeLocations.get(k)[1]};
        return chosen;
    }

    /**
     *
     * @param toPick Liste de parcelles dont on veut en choisir une aléatoire.
     * @return Une ClassicParcel aléatoire parmis celles soumises précédemment.
     */
    private ClassicParcel randomParcel(ArrayList<ClassicParcel> toPick){
        if(toPick.size() == 1){
            return toPick.get(0);
        }
        Random r = new Random();
        int k = r.nextInt(toPick.size());
        return toPick.get(k);
    }

    private Action putRandomIrrigation(ArrayList<ActionList> available){
        if (available.contains(ActionList.FreePutIrrigation)) {
            ArrayList<int[]> coordinates = IrrigationBoard.getInstance().irrigationFreeLocation();
            if (coordinates.size() > 0) {
                Random r = new Random();
                int k = r.nextInt(coordinates.size());
                int[] chosen = coordinates.get(k);
                if (coordinates != null)
                    return new Action(ActionList.FreePutIrrigation, chosen);
            }
        }
        return null;
    }

    /**
     *
     * @param available Liste des actions disponibles.
     * @return Action piocher une irrigation si elle est disponible, sinon null.
     */
    private Action pickIrrigation(ArrayList<ActionList> available){
        if(available.contains(ActionList.ActionIrrigation))
            return new Action(ActionList.ActionIrrigation);
        return null;
    }

    /**
     * Renvoie la liste de goals possédés du type séléctionné
     * @param gt --> Le type de goals choisi
     * @return La liste de goals possédés du type choisi.
     */
    private ArrayList<Goal> sortGoals(GoalType gt){
        ArrayList<Goal> sorted = new ArrayList<>();
        for(Goal g: goalList){
            if(g.getGoalType() == gt){
                sorted.add(g);
            }
        }
        return sorted;
    }

    /**
     * Renvoie la liste des parcelles accessibles pour le panda et possédant au moins un bambou.
     * @return liste des parcelles accessibles au panda, possédant au moins un bambou.
     */
    private ArrayList<ClassicParcel> accessiblePandaWithBamboo(){
        ArrayList<Parcel> allOfThem = Panda.getInstance().accessibleParcels();
        ArrayList<ClassicParcel> onlyClassic = new ArrayList<>();
        for(Parcel p : allOfThem){
            if(p.getClass() == ClassicParcel.class){
                onlyClassic.add((ClassicParcel) p);
            }
        }
        ArrayList<ClassicParcel> sorted = new ArrayList<>();
        for(ClassicParcel cp: onlyClassic){
            if(cp.getHeightBamboo() > 0 && cp.getImprovement() != ImprovementList.enclosure){
                sorted.add(cp);
            }
        }
        return sorted;
    }

    /**
     * Séléctionne toutes les parcelles disponibles pour le panda, possédant au moins un bambou, dans le cas d'un WeatherMovePanda.
     * @return liste des parcelles accessibles au panda, possédant au moins un bambou.
     */
    private ArrayList<ClassicParcel> specialMovePanda(){
        ArrayList<Parcel> allOfThem = GameBoard.getInstance().getExistingParcel();
        ArrayList<ClassicParcel> onlyClassic = new ArrayList<>();
        for(Parcel p : allOfThem){
            if(p.getClass() == ClassicParcel.class){
                onlyClassic.add((ClassicParcel) p);
            }
        }
        ArrayList<ClassicParcel> sorted = new ArrayList<>();
        for(ClassicParcel cp: onlyClassic){
            if(cp.getHeightBamboo() > 0 && cp.getImprovement() != ImprovementList.enclosure){
                sorted.add(cp);
            }
        }
        return sorted;
    }

    /**
     * Séléctionne la meilleure action Panda dans le but de compléter un pandaGoal en cas de WeatherMovePanda.
     * @param goals --> Liste de goals possédés.
     * @return meilleure action possible si existante, sinon une position aléatoire.
     */
    private Action specialCheckBestPanda(ArrayList<Goal> goals){
        int score = 5;
        Goal chosen = null;
        ClassicParcel parcelToGo = null;
        ArrayList<ClassicParcel> possibleLocations = specialMovePanda();
        for(Goal g: goals){
            int scoreTemp = calculateScorePanda((PandaGoal) g);
            ClassicParcel tempToGo = containsColor(possibleLocations, ((PandaGoal) g).getColor());
            if(scoreTemp < score && tempToGo != null){
                score = scoreTemp;
                chosen = g;
                parcelToGo = tempToGo;
            }
        }
        if(chosen != null) {
            return new Action(ActionList.ActionPanda, parcelToGo.getCoordinates());
        }
        Random r = new Random();
        if(possibleLocations.size() > 0) {
            int nb = r.nextInt(possibleLocations.size());
            return new Action(ActionList.ActionPanda, possibleLocations.get(nb).getCoordinates());
        }else{
            ArrayList<Parcel> noOtherChoice = GameBoard.getInstance().getExistingParcel();
            noOtherChoice.remove(Panda.getInstance().getPosition());
            int nb = r.nextInt(noOtherChoice.size());
            return new Action(ActionList.ActionPanda, noOtherChoice.get(nb).getCoordinates());
        }
    }

    /**
     *
     * @param parcels --> Liste de parcelles à analyser.
     * @param color --> Couleur requise.
     * @return première parcelle parmis la liste de parcelles proposées étant de la couleur requise.
     */
    private ClassicParcel containsColor(ArrayList<ClassicParcel> parcels, ColorList color){
        for(ClassicParcel cp: parcels){
            if(color == ColorList.MULTICOLOR){
                if(greenBambooNb == 0){
                    if(cp.getColor() == ColorList.GREEN){
                        return cp;
                    }
                }
                if(pinkBambooNb == 0){
                    if(cp.getColor() == ColorList.PINK){
                        return cp;
                    }
                }
                if(yellowBambooNb == 0){
                    if(cp.getColor() == ColorList.YELLOW){
                        return cp;
                    }
                }
            }
            if(cp.getColor() == color)
                return cp;
        }
        return null;
    }

    /**
     *
     * @param goals --> Liste de goals.
     * @return La meilleure actionPanda possible dans le but de compléter une objectif Panda, si existante, sinon null.
     */
    private Action checkBestPanda(ArrayList<Goal> goals){
        int score = 5;
        Goal chosen = null;
        ClassicParcel parcelToGo = null;
        ArrayList<ClassicParcel> possibleLocations = accessiblePandaWithBamboo();
        if(possibleLocations.size() == 0){return null;}
        for(Goal g: goals){
            int scoreTemp = calculateScorePanda((PandaGoal) g);
            ClassicParcel tempToGo = containsColor(possibleLocations, ((PandaGoal) g).getColor());
            if(scoreTemp < score && tempToGo != null){
                score = scoreTemp;
                chosen = g;
                parcelToGo = tempToGo;
            }
        }
        if(chosen != null) {
            return new Action(ActionList.ActionPanda, parcelToGo.getCoordinates());
        }
        return null;
    }

    /**
     *
     * @param g --> Goal à analyser.
     * @return Le nombre de bambous manquant afin de compléter l'objectif passé en paramètres.
     */
    private int calculateScorePanda(PandaGoal g){
        int score = 5;
        switch (g.getColor()){
            case GREEN:
                score = 2 - greenBambooNb;
                if(score < 0)
                    return 0;
                else
                    return score;
            case PINK:
                score = 2 - pinkBambooNb;
                if(score < 0)
                    return 0;
                else
                    return score;
            case YELLOW:
                score = 2 - yellowBambooNb;
                if(score < 0)
                    return 0;
                else
                    return score;
            case MULTICOLOR:
                score = 3;
                if(greenBambooNb >= 1){score--;}
                if(pinkBambooNb >= 1){score--;}
                if(yellowBambooNb >= 1){score--;}
                return score;
        }
        return 5;
    }

    /**
     *
     * @param goals Liste de gardner goals exclusivement.
     * @return L'action jardinier la pus optimisée afin de compléter un objectif jardinier. null si inexistante.
     */
    private Action checkBestGardener(ArrayList<Goal> goals){
        ArrayList<Action> possibles = new ArrayList<>();
        ArrayList<Parcel> accessibleParcels = Gardener.getInstance().accessibleParcels();
        ArrayList<ClassicParcel> accessibleClassicParcels = new ArrayList<>();
        for(Parcel p: accessibleParcels){
            if(p.getClass() == ClassicParcel.class){
                accessibleClassicParcels.add((ClassicParcel) p);
            }
        }
        if (accessibleClassicParcels.size() > 0) {
            int score = 40;
            ClassicParcel toGo = null;
            for (Goal g : goals) {
                int tempScore = calculateScoreGardener((GardenerGoal) g);
                ClassicParcel tempToGo = bestParcelForGardener((GardenerGoal) g);
                if(tempScore < score && tempToGo != null && tempToGo.irrigated){
                    score = tempScore;
                    toGo = tempToGo;
                }
            }
            if(toGo != null){
                return new Action(ActionList.ActionGardener, toGo.getCoordinates());
            }

                /*GardenerGoal gg = (GardenerGoal) g;
                ArrayList<ClassicParcel> colored = GameBoard.getInstance().getColoredParcel(gg.getColor());

                               for(ClassicParcel cp: accessibleClassicParcels){
                    for(ClassicParcel col: colored){
                        if(cp.equals(col)){
                            possibles.add(new Action(ActionList.ActionGardener, cp.getCoordinates()));
                        }
                    }
                }
            }
            if(possibles.size() > 0){
                return possibles.get(0);
            }*/
        }
        return null;
    }

    private int calculateScoreGardener(GardenerGoal gg){
        int required = gg.getParcelNumber() * gg.getBambooSize();
        int score = 0;
        ArrayList<ClassicParcel> sorted = sortForGardenerGoal(GameBoard.getInstance().getColoredParcel(gg.getColor()), gg);
        if(sorted.size() > 0){
            for(ClassicParcel cp: sorted){
                if(cp.getHeightBamboo() < gg.getBambooSize()){
                    score += gg.getBambooSize() - cp.getHeightBamboo();
                }
            }
        }
        return score;
    }

    private ClassicParcel bestParcelForGardener(GardenerGoal gg){
        ArrayList<ClassicParcel> sorted = sortForGardener(GameBoard.getInstance().getColoredParcel(gg.getColor()), gg);
        switch (sorted.size()){
            case 0:
                return null;
            case 1:
                return sorted.get(0);
            default:
                Random r = new Random();
                int k = r.nextInt(sorted.size());
                return sorted.get(k);
        }
    }

    private ArrayList<ClassicParcel> sortForGardener(ArrayList<ClassicParcel> toSort, GardenerGoal gg){
        ArrayList<ClassicParcel> sorted = new ArrayList<>();
        ArrayList<Parcel> accessibleGardener = Gardener.getInstance().accessibleParcels();
        if(toSort.size() > 0){
            for(ClassicParcel cp: toSort){
                if(cp.getImprovement() == gg.getImprovement() && cp.getColor() == gg.getColor()){
                    if(sorted.size() < gg.getParcelNumber() && accessibleGardener.contains(cp) && cp.irrigated){
                        sorted.add(cp);
                    }
                }
            }
        }
        return sorted;
    }

    private ArrayList<ClassicParcel> sortForGardenerGoal(ArrayList<ClassicParcel> toSort, GardenerGoal gg){
        ArrayList<ClassicParcel> sorted = new ArrayList<>();
        ArrayList<Parcel> accessibleGardener = Gardener.getInstance().accessibleParcels();
        if(toSort.size() > 0){
            for(ClassicParcel cp: toSort){
                if(cp.getImprovement() == gg.getImprovement() && cp.getColor() == gg.getColor()){
                    if(sorted.size() < gg.getParcelNumber()){
                        sorted.add(cp);
                    }
                }
            }
        }
        return sorted;
    }

    private Action fullRandom(ArrayList<ActionList> actionList){
        Random r = new Random();
        ArrayList<Parcel> parcelsList;
        ArrayList<Integer[]> placesList;

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
                return parcelAction(placesList);
            default:
                return skipTurn();
        }
    }
}
