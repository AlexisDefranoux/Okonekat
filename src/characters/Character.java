package characters;

import engines.Logger;
import bots.MotherBot;
import parcels.GameBoard;
import parcels.Parcel;

import java.util.ArrayList;

public abstract class Character {

    protected Parcel parcel;

    protected Character() { parcel = GameBoard.getInstance().getBoard()[27][27]; }

    public Parcel getPosition(){
        return parcel;
    }

    protected void setPosition(Parcel parcel) {
        this.parcel = parcel;
    }

    protected abstract void action(Parcel p, MotherBot bot);

    /**
     * Déplace le personnage sur une parcelle et effectue l'action qui lui est attribuée.
     * @param coord coordonnées de la parcelle oe destination
     * @param b bot qui effectue cette action
     * @return true si le personnage peut se rendre à cette position, false sinon.
     */
    public boolean move(int[] coord, MotherBot b) {

        Parcel next = GameBoard.getInstance().getXYParcel(coord[0], coord[1]);
        ArrayList<Parcel> accessible = accessibleParcels();

        String str;

        if (this instanceof Panda)
            str = "panda";
        else
            str = "jardinier";

        if(next != parcel && accessible.contains(next)) {
            setPosition(next);

            int x = getPosition().getCoordinates()[0] - 27;
            int y = getPosition().getCoordinates()[1] - 27;

            Logger.printLine(b + " déplace le " + str + " en [" + x + ", " + y + "].");
            action(parcel, b);
            return true;
        } else{
            Logger.printLine("Impossible de déplacer le " + str + " en [" + (coord[0]-27) + ", " + (coord[0]-27) + "].");
            return false;
        }
    }

    /**
     * @return la liste des parcelles accessibles au personnage.
     */
    public ArrayList<Parcel> accessibleParcels() {
        ArrayList<Parcel> tab = new ArrayList<>();
        String[] directionList = {"NO", "NE", "E", "SE", "SO", "O"};
        for(int i = 0; i < directionList.length; i++) {
            accessibleParcels2(tab, directionList[i], GameBoard.getInstance().getNextParcel(parcel, directionList[i]));
        }
        return tab;
    }

    /**
     * Ajoute à tab les parcelles accessibles au personnage dans une direction donnée.
     * @param tab tableau auquel sont ajoutées les parcelles accessibles
     * @param direction direction dans laquelle chercher les parcelles accessibles
     * @param p parcelle où se trouve le personnage
     */
    public void accessibleParcels2(ArrayList<Parcel> tab, String direction, Parcel p) {
        if(p != null) {
            tab.add(p);
            accessibleParcels2(tab, direction, GameBoard.getInstance().getNextParcel(p, direction));
        }
    }

    public abstract ArrayList<Parcel> findBamboo();
}
