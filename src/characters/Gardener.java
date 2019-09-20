package characters;

import bots.MotherBot;
import parcels.ColorList;
import parcels.GameBoard;
import parcels.Parcel;

import java.util.ArrayList;

public class Gardener extends Character {

    private static Gardener gardener = new Gardener();

    public static void reset(){
        gardener = new Gardener();
    }

    public static Gardener getInstance() {
        if (gardener == null)
            gardener = new Gardener();
        return gardener;
    }

    /**
     * Le jardinier fait pousser un bambou sur sa parcelle ainsi que sur celles autour de lui.
     * @param p parcelle où effectuer l'action
     * @param bot bot qui gère le jardinier à ce moment
     */
    public void action(Parcel p, MotherBot bot) {
        String[] directionList = {"NO", "NE", "E", "SE", "SO", "O"};
        ColorList colorList = p.getColor();

        if(p.canBambooGrow())
            p.growBamboo();

        for(int i = 0; i < 6; i++){
            Parcel next = GameBoard.getInstance().getNextParcel(p, directionList[i]);
            if(next != null && next.getColor() == colorList) {
                if(next.canBambooGrow())
                    next.growBamboo();
                int nextX = next.getCoordinates()[0] - 27;
                int nextY = next.getCoordinates()[1] - 27;
            }
        }
    }

    /**
     * @return less parcelles accessibles au jardinier où il est possible de faire pousser du bambou.
     */
    public ArrayList<Parcel> findBamboo() {
        ArrayList<Parcel> accessible = Gardener.getInstance().accessibleParcels();
        ArrayList<Parcel> bambooToGrowHere = new ArrayList<>();
        for(int i = 0; i < accessible.size(); i++){
            if (accessible.get(i).canBambooGrow()) {
                bambooToGrowHere.add(accessible.get(i));
            }
        }
        return bambooToGrowHere;
    }
}
