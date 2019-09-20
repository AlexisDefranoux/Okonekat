package characters;

import bots.MotherBot;
import parcels.GameBoard;
import engines.Logger;
import parcels.Parcel;

import java.util.ArrayList;

public class Panda extends Character {

    private static Panda panda = new Panda();

    public static Panda getInstance() {
        if(panda == null)
            panda = new Panda();
        return panda;
    }

    public static void reset(){
        panda = new Panda();
    }

    /**
     * Le panda mange un bambou sur cette parcelle.
     * @param p parcelle où effectuer l'action
     * @param bot bot qui gère le panda à ce moment
     */
    public void action(Parcel p, MotherBot bot) {
        if(Panda.getInstance().getPosition().canEatBamboo()){
            switch(Panda.getInstance().getPosition().getColor()) {
                case GREEN:
                    bot.setGreenBambooNb(bot.getGreenBambooNb() + 1);
                    break;
                case YELLOW:
                    bot.setYellowBambooNb(bot.getYellowBambooNb() + 1);
                    break;
                case PINK:
                    bot.setPinkBambooNb(bot.getPinkBambooNb() + 1);
                    break;
            }
            int x = p.getCoordinates()[0];
            int y = p.getCoordinates()[1];
            GameBoard.getInstance().getBoard()[x][y].eatBamboo();
        }else
            Logger.printLine("Le panda ne peut pas manger de bambou sur cette parcelle car il n'y a pas de bambou ou il y a l'aménagement cage.");
    }

    /**
     * Déplace le panda n'importe où lors de l'orage.
     * @param coord coordonnées de destination
     * @param b bot qui effectue l'action
     */
    public void movePanda(int[] coord, MotherBot b) {
        Parcel next = GameBoard.getInstance().getXYParcel(coord[0], coord[1]);
        if(next != parcel) {
            Panda.getInstance().setPosition(next);
            int x = getPosition().getCoordinates()[0] - 27;
            int y = getPosition().getCoordinates()[1] - 27;
            Logger.printLine(b + " déplace le panda en [" + x + ", " + y + "].");
            action(parcel, b);
        } else{
            Logger.printLine("Impossible de déplacer le panda en [" + (coord[0]-27) + ", " + (coord[0]-27) + "].");
        }
    }

    /**
     * @return les parcelles accessibles où il est possible pour le panda de manger du bambou
     */
    public ArrayList<Parcel> findBamboo() {
        ArrayList<Parcel> accessible = Panda.getInstance().accessibleParcels();
        ArrayList<Parcel> bambooHere = new ArrayList<>();
        for(int i = 0; i < accessible.size(); i++){
            if (accessible.get(i).canEatBamboo()) {
                bambooHere.add(accessible.get(i));
            }
        }
        return bambooHere;
    }



}
