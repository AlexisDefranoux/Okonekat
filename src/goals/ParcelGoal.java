package goals;

import parcels.*;

import java.util.ArrayList;

public class ParcelGoal extends Goal{

    Pattern pattern;

    public ParcelGoal(int scoring, Pattern pattern) {
        super(scoring);
        this.pattern = pattern;
        this.goalType = GoalType.PARCEL;
    }

    /**
     * @return si le goal est validé ou non
     */
    private boolean checkParcel(Parcel p, ColorList c){
        ClassicParcel cp;
        if(p.getClass() != ClassicParcel.class){
            return false;
        }else{
            cp = (ClassicParcel) p;
            if(cp.getColor() == c){
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean checkGoal(int green, int yellow, int pink) {
        return false;
    }

    @Override
    public boolean checkGoal() {
        return false;
    }

    public boolean checkGoal(ArrayList<Parcel> existingParcels){
        if(pattern.getClass() == Pattern.class){
            //System.out.println("Je suis dans checkPattern" + existingParcels.size());
            return checkAllPattern(existingParcels);
        }
        return false;
    }

    /**
     * @return si le goal est validé ou non de tout type de pattern
     */
    private boolean checkAllPattern(ArrayList<Parcel> existingParcels){
        //System.out.println("Je suis dans checkStraightPattern");
        for(int j = 0; j < existingParcels.size(); j++) {
            Parcel currentParcel = existingParcels.get(j);
            if(currentParcel.getClass() == ClassicParcel.class){
                if(currentParcel.irrigated) {
                    if (checkSingleAllPattern(currentParcel)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    
    private boolean checkSingleAllPattern(Parcel p){
        //System.out.println("Je suis dans checkSingleStraightPattern");
        //System.out.println("" + p.getCoordinates()[0] + ", " + p.getCoordinates()[1]);
        if(p.getClass() == ClassicParcel.class){
            Parcel temp = p;
            int i;
            for(i = 0; i < this.pattern.getForm().getSize(); i++){
                //System.out.println(pattern.getColor().size());
                if(!checkParcel(temp, pattern.getColor().get(i))){
                    return false;
                }else{
                    Parcel shaker = GameBoard.getInstance().getNextParcel(temp, pattern.getForm().getFormList().get(i));
                    if(shaker != null && shaker.irrigated){
                        if(shaker.getClass() == Pond.class){
                            return false;
                        }else{
                            temp = shaker;
                        }
                    }else{
                        return false;
                    }
                }
            }
            return checkParcel(temp, pattern.getColor().get(i));
        }
        return false;
    }

    /**
     * gere l'affichage
     */
    public String toString() {
        return "de type "+goalType+ ", de score " + scoring + ", de forme "+ pattern;
    }
}
