package goals;

import parcels.ColorList;
import parcels.Parcel;

import java.util.ArrayList;

public class PandaGoal extends Goal {

    private ColorList colorList;

    public PandaGoal(int scoring, ColorList colorList) {
        super(scoring);
        this.colorList = colorList;
        this.goalType = GoalType.PANDA;
    }

    public ColorList getColor(){return this.colorList;}

    /**
     * @return si le goal est validé ou non
     */
    public boolean checkGoal(int green, int pink, int yellow){
        switch(colorList){
            case GREEN:
                if(green >= 2){return true;}
                break;
            case PINK:
                if(pink >= 2){return true;}
                break;
            case YELLOW:
                if(yellow >= 2){return true;}
                break;
            case MULTICOLOR:
                if(green >= 1 && pink >= 1 && yellow >= 1){ return true;}
                break;
        }
        return false;
    }

    @Override
    public boolean checkGoal() {
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
        return "de type "+goalType+ ", de score " + scoring + ", de couleur "+ colorList;
    }
}
