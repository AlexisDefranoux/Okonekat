package goals;

import parcels.*;

import java.util.ArrayList;

public class GardenerGoal extends Goal{

    private int bambooSize;
    private int parcelNumber;
    private ImprovementList improvement;
    private ColorList colorList;

    public GardenerGoal(int scoring, int bambooSize, int parcelNumber, ImprovementList improvement, ColorList colorList) {
        super(scoring);
        this.bambooSize = bambooSize;
        this.parcelNumber = parcelNumber;
        this.improvement = improvement;
        this.colorList = colorList;
        this.goalType = GoalType.GARDENER;
    }

    public GardenerGoal(int scoring, int bambooSize, int parcelNumber, ColorList colorList) {
        super(scoring);
        this.bambooSize = bambooSize;
        this.parcelNumber = parcelNumber;
        this.improvement = null;
        this.colorList = colorList;
        this.goalType = GoalType.GARDENER;
    }

    public ImprovementList getImprovement(){return improvement;}
    public int getParcelNumber(){return parcelNumber;}
    public int getBambooSize() {
        return bambooSize;
    }

    public ColorList getColor(){return this.colorList;}

    @Override
    public boolean checkGoal(int green, int yellow, int pink) {
        return false;
    }

    /**
     * @return si le goal est validé ou non
     */
    @Override
    public boolean checkGoal() {
        ArrayList<ClassicParcel> list;
        int count = 0;
        boolean improvementChecker = false;
        switch (colorList){
            case YELLOW:
                list = GameBoard.getInstance().getColoredParcel(ColorList.YELLOW);
                break;
            case PINK:
                list = GameBoard.getInstance().getColoredParcel(ColorList.PINK);
                break;
            case GREEN:
                list = GameBoard.getInstance().getColoredParcel(ColorList.GREEN);
                break;
            default:
                list = null;
        }
        for(ClassicParcel p: list){
            if(improvement == null) {
                if (p.getHeightBamboo() >= this.bambooSize) {
                    count++;
                }
            }else{
                if(p.getHeightBamboo() >= this.bambooSize && this.improvement == p.getImprovement()){
                    count++;
                    improvementChecker = true;
                }
            }

        }
        if(this.improvement == null) {
            if (count >= this.parcelNumber)
                return true;
        }else{
            if(count >= this.parcelNumber && improvementChecker){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkGoal(ArrayList<Parcel> list) {
        return false;
    }

    /**
     * gere l'affichage
     */
    public String toString() {
        String a ="de type "+goalType+ ",de score " + scoring + ", de taille "+ bambooSize + ", de nombre de parcelle " + parcelNumber + ", de couleur "+ colorList;
        if(improvement == null)
            return a;
        else
            return a+ ", d'aménagement  " +improvement;
    }
}
