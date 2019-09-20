package engines;

import goals.*;
import parcels.ClassicParcel;
import parcels.ColorList;
import parcels.ImprovementList;

import java.util.Random;

import java.util.ArrayList;

public class Deck {

    private static Deck deck = new Deck();

    private ArrayList<ClassicParcel> parcelList = new ArrayList<>();
    private ArrayList<PandaGoal> pandaGoalList = new ArrayList<>();
    private ArrayList<GardenerGoal> gardenerGoalList = new ArrayList<>();
    private ArrayList<ParcelGoal> parcelGoalList = new ArrayList<>();
    private int fertilizerImprovementNb;
    private int pondImprovementNb;
    private int enclosureImprovementNb;
    private int irrigationNb;


    public static Deck getInstance(){
        if(deck == null)
            deck = new Deck();
        return deck;
    }

    private Deck(){
        this.parcelInit();
        this.pandaGoalListInit();
        this.gardenerGoalListInit();
        this.parcelGoalListInit();
        fertilizerImprovementNb = 3;
        pondImprovementNb = 3;
        enclosureImprovementNb = 3;
        irrigationNb = 20;
    }

    public static void reset(){
        deck = new Deck();
    }

    public int getIrrigationNb() { return irrigationNb; }

    public ArrayList<ClassicParcel> getParcelList() {
        return parcelList;
    }

    public void setIrrigationNb(int irrigationNb) {
        this.irrigationNb = irrigationNb;
    }

    /**
     * Créé les 27 parcels du jeu dans parcelList.
     */
    private void parcelInit(){

        //11 parcels vertes
        parcelList.add(new ClassicParcel(ColorList.GREEN));
        parcelList.add(new ClassicParcel(ColorList.GREEN));
        parcelList.add(new ClassicParcel(ColorList.GREEN));
        parcelList.add(new ClassicParcel(ColorList.GREEN));
        parcelList.add(new ClassicParcel(ColorList.GREEN));
        parcelList.add(new ClassicParcel(ColorList.GREEN));

        parcelList.add(new ClassicParcel(ColorList.GREEN, ImprovementList.pond));
        parcelList.add(new ClassicParcel(ColorList.GREEN, ImprovementList.pond));
        parcelList.add(new ClassicParcel(ColorList.GREEN, ImprovementList.enclosure));
        parcelList.add(new ClassicParcel(ColorList.GREEN, ImprovementList.enclosure));
        parcelList.add(new ClassicParcel(ColorList.GREEN, ImprovementList.fertilizer));

        //7 parcels roses
        parcelList.add(new ClassicParcel(ColorList.PINK));
        parcelList.add(new ClassicParcel(ColorList.PINK));
        parcelList.add(new ClassicParcel(ColorList.PINK));
        parcelList.add(new ClassicParcel(ColorList.PINK));

        parcelList.add(new ClassicParcel(ColorList.PINK, ImprovementList.pond));
        parcelList.add(new ClassicParcel(ColorList.PINK, ImprovementList.enclosure));
        parcelList.add(new ClassicParcel(ColorList.PINK, ImprovementList.fertilizer));

        //9 parcels jaunes
        parcelList.add(new ClassicParcel(ColorList.YELLOW));
        parcelList.add(new ClassicParcel(ColorList.YELLOW));
        parcelList.add(new ClassicParcel(ColorList.YELLOW));
        parcelList.add(new ClassicParcel(ColorList.YELLOW));
        parcelList.add(new ClassicParcel(ColorList.YELLOW));
        parcelList.add(new ClassicParcel(ColorList.YELLOW));

        parcelList.add(new ClassicParcel(ColorList.YELLOW, ImprovementList.pond));
        parcelList.add(new ClassicParcel(ColorList.YELLOW, ImprovementList.enclosure));
        parcelList.add(new ClassicParcel(ColorList.YELLOW, ImprovementList.fertilizer));
    }

    /**
     * Créé les 15 objectifs panda dans pandaGoalList.
     */
    private void pandaGoalListInit() {

        pandaGoalList.add(new PandaGoal(3, ColorList.GREEN));
        pandaGoalList.add(new PandaGoal(3, ColorList.GREEN));
        pandaGoalList.add(new PandaGoal(3, ColorList.GREEN));
        pandaGoalList.add(new PandaGoal(3, ColorList.GREEN));
        pandaGoalList.add(new PandaGoal(3, ColorList.GREEN));

        pandaGoalList.add(new PandaGoal(4, ColorList.YELLOW));
        pandaGoalList.add(new PandaGoal(4, ColorList.YELLOW));
        pandaGoalList.add(new PandaGoal(4, ColorList.YELLOW));
        pandaGoalList.add(new PandaGoal(4, ColorList.YELLOW));

        pandaGoalList.add(new PandaGoal(5, ColorList.PINK));
        pandaGoalList.add(new PandaGoal(5, ColorList.PINK));
        pandaGoalList.add(new PandaGoal(5, ColorList.PINK));

        pandaGoalList.add(new PandaGoal(6, ColorList.MULTICOLOR));
        pandaGoalList.add(new PandaGoal(6, ColorList.MULTICOLOR));
        pandaGoalList.add(new PandaGoal(6, ColorList.MULTICOLOR));
    }

    /**
     * Créé les 15 objectifs jardinier dans gardenerGoalList.
     */
    private void gardenerGoalListInit() {

        gardenerGoalList.add(new GardenerGoal(3, 4, 1, ImprovementList.fertilizer, ColorList.GREEN));
        gardenerGoalList.add(new GardenerGoal(4, 4, 1, ImprovementList.enclosure, ColorList.GREEN));
        gardenerGoalList.add(new GardenerGoal(4, 4, 1, ImprovementList.pond, ColorList.GREEN));
        gardenerGoalList.add(new GardenerGoal(5, 4, 1, ColorList.GREEN));

        gardenerGoalList.add(new GardenerGoal(4, 4, 1, ImprovementList.fertilizer, ColorList.YELLOW));
        gardenerGoalList.add(new GardenerGoal(5, 4, 1, ImprovementList.enclosure, ColorList.YELLOW));
        gardenerGoalList.add(new GardenerGoal(5, 4, 1, ImprovementList.pond, ColorList.YELLOW));
        gardenerGoalList.add(new GardenerGoal(6, 4, 1, ColorList.YELLOW));

        gardenerGoalList.add(new GardenerGoal(6, 3, 2, ColorList.PINK));
        gardenerGoalList.add(new GardenerGoal(7, 3, 3, ColorList.YELLOW));
        gardenerGoalList.add(new GardenerGoal(8, 3, 4, ColorList.GREEN));

        gardenerGoalList.add(new GardenerGoal(5, 4, 1, ImprovementList.fertilizer, ColorList.PINK));
        gardenerGoalList.add(new GardenerGoal(6, 4, 1, ImprovementList.pond, ColorList.PINK));
        gardenerGoalList.add(new GardenerGoal(6, 4, 1, ImprovementList.enclosure, ColorList.PINK));
        gardenerGoalList.add(new GardenerGoal(7, 4, 1, ColorList.PINK));

    }

    /**
     * Créé les 15 objectifs parcels dans parcelGoalList.
     */
    private void parcelGoalListInit() {

        parcelGoalList.add(new ParcelGoal(3, new Pattern(Form.STRAIGHT, ColorList.YELLOW)));
        parcelGoalList.add(new ParcelGoal(4, new Pattern(Form.DIAMOND, ColorList.YELLOW)));
        parcelGoalList.add(new ParcelGoal(3, new Pattern(Form.CURVE, ColorList.YELLOW)));
        parcelGoalList.add(new ParcelGoal(3, new Pattern(Form.TRIANGLE, ColorList.YELLOW)));

        parcelGoalList.add(new ParcelGoal(2, new Pattern(Form.STRAIGHT, ColorList.GREEN)));
        parcelGoalList.add(new ParcelGoal(2, new Pattern(Form.CURVE, ColorList.GREEN)));
        parcelGoalList.add(new ParcelGoal(2, new Pattern(Form.TRIANGLE, ColorList.GREEN)));
        parcelGoalList.add(new ParcelGoal(3, new Pattern(Form.DIAMOND, ColorList.GREEN)));

        parcelGoalList.add(new ParcelGoal(5, new Pattern(Form.DIAMOND, ColorList.GREEN, ColorList.YELLOW)));
        parcelGoalList.add(new ParcelGoal(4, new Pattern(Form.DIAMOND, ColorList.GREEN, ColorList.PINK)));
        parcelGoalList.add(new ParcelGoal(3, new Pattern(Form.DIAMOND, ColorList.PINK, ColorList.YELLOW)));

        parcelGoalList.add(new ParcelGoal(4, new Pattern(Form.CURVE, ColorList.PINK)));
        parcelGoalList.add(new ParcelGoal(4, new Pattern(Form.TRIANGLE, ColorList.PINK)));
        parcelGoalList.add(new ParcelGoal(5, new Pattern(Form.DIAMOND, ColorList.PINK)));
        parcelGoalList.add(new ParcelGoal(4, new Pattern(Form.STRAIGHT, ColorList.PINK)));
    }

    /**
     * Renvoie une arrayList des aménagements disponibles dans le deck aménagement.
     */
    public ArrayList<ImprovementList> getImprovementList() {

        ArrayList<ImprovementList> improvementList = new ArrayList<>();

        if(enclosureImprovementNb>0)
            improvementList.add(ImprovementList.enclosure);
        if (pondImprovementNb>0)
            improvementList.add(ImprovementList.pond);
        if (fertilizerImprovementNb>0)
            improvementList.add(ImprovementList.fertilizer);

        return improvementList;
    }

    /**
     * Supprime l'aménagement du deck aménagement.
     * @param improvementList l'aménagement choisi par le bot
     */
    public void removeImprovement(ImprovementList improvementList){

        switch (improvementList){
            case fertilizer:
                fertilizerImprovementNb--;
                break;
            case enclosure:
                enclosureImprovementNb--;
                break;
            case pond:
                pondImprovementNb--;
                break;
            default:
                Logger.printLine("ERREUR deck aménagement ");
                break;
        }
    }

    /**
     * Renvoie le nombre total d'aménagement disponible dans le deck parcelList.
     */
    public int getImprovementNb(){
        return enclosureImprovementNb+fertilizerImprovementNb+pondImprovementNb;
    }

    /**
     * Renvoie 3 parcels random diponible dans le deck parcelList. Si il y a moins de 3 parcels renvoie le deck parcelList.
     */
    public ArrayList<ClassicParcel> getRandomParcelList() {

        if (parcelList.size()<=3){
                return parcelList;
        }else{
            ArrayList<ClassicParcel> my3Parcel = new ArrayList<>();
            ArrayList<ClassicParcel> parcelListClone = (ArrayList<ClassicParcel>)parcelList.clone();
            for (int i=0; i<3; i++){
                Random randomGenerator = new Random();
                int index = randomGenerator.nextInt(parcelListClone.size());
                my3Parcel.add(parcelListClone.get(index));
                parcelListClone.remove(index);
            }
            return my3Parcel;
        }
    }

    /**
     * Supprime la parcel du deck parcelList.
     * @param parcel l'aménagement choisi par le bot
     */
    public void removeParcel(ClassicParcel parcel){
        parcelList.remove(parcel);
    }

    /**
     * Renvoie un objectif random du type passé en paramètre
     * @param goalType le type d'objectif choisi par le bot
     */
    public Goal getRandomGoal(GoalType goalType) {

        Logger.printLine("Il choisi le type d'objectif " + goalType+".");

        Random randomGenerator = new Random();
        Goal goal;

        switch (goalType) {
            case GARDENER:
                goal = gardenerGoalList.get(randomGenerator.nextInt(gardenerGoalList.size()));
                gardenerGoalList.remove(goal);
                break;
            case PANDA:
                goal = pandaGoalList.get(randomGenerator.nextInt(pandaGoalList.size()));
                pandaGoalList.remove(goal);
                break;
            case PARCEL:
                goal = parcelGoalList.get(randomGenerator.nextInt(parcelGoalList.size()));
                parcelGoalList.remove(goal);
                break;
            default:
                goal = null;
        }

        Logger.printLine("Il pioche un objectif " + goal+".");
        return goal;
    }

    /**
     * Donne 3 objectifs random de 3 types d'objectifs différent pour initialiser le bot.
     */
    public ArrayList<Goal> getRandomGoalList() {

        ArrayList<Goal> my3Goal = new ArrayList<>();
        Random randomGenerator = new Random();
        int index;

        index = randomGenerator.nextInt(pandaGoalList.size());
        my3Goal.add(pandaGoalList.get(index));
        pandaGoalList.remove(index);

        index = randomGenerator.nextInt(gardenerGoalList.size());
        my3Goal.add(gardenerGoalList.get(index));
        gardenerGoalList.remove(index);

        index = randomGenerator.nextInt(parcelGoalList.size());
        my3Goal.add(parcelGoalList.get(index));
        parcelGoalList.remove(index);

        return my3Goal;
    }

    /**
     * Renvoie les types d'objectifs encore diponible dans le deck.
     */
    public ArrayList<GoalType> getGoalTypePossible(){

        ArrayList<GoalType> goalType = new ArrayList<>();
        if(pandaGoalList.size()>0)
            goalType.add(GoalType.PANDA);
        if(gardenerGoalList.size()>0)
            goalType.add(GoalType.GARDENER);
        if(parcelGoalList.size()>0)
            goalType.add(GoalType.PARCEL);

        return goalType;
    }
}
