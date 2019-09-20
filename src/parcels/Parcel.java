package parcels;

import java.util.ArrayList;


public abstract class Parcel {

    public int[] coordinates;
    public int growingSpeed;
    public boolean canEat;
    public boolean irrigated;

    protected Parcel(){
        this.growingSpeed = 1;
        this.canEat = false;
        this.irrigated = false;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    /**
     * @return true s'il est possible de faire pousser du bambou, false sinon
     */
    public boolean canBambooGrow(){
        if(!irrigated || getHeightBamboo() >= 4)
            return false;
        return true;
    }

    /**
     * @return true s'il est possible de manger du bambou, false sinon
     */
    public boolean canEatBamboo() { return this.canEat; }

    public abstract int getHeightBamboo();

    public abstract void growBamboo();

    public abstract  void eatBamboo();

    public abstract ColorList getColor();

    /**
     * Attribue des coordonnées à la parcelle.
     * @param coordinates coordonnées que l'on souhaite
     */
    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Applique l'irrigation à la parcelle.
     */
    public void setIrrigation() {
        this.irrigated = true;
        this.growBamboo();
    }

    /**
     * @return true si la parcelle est irriguée, false sinon
     */
    public boolean getIrrigation() { return this.irrigated; }

    /**
     * @param direction bord de la parcelle que l'on étudie
     * @return les coordonnées de l'irrigation sur ce bord
     */
    public int[] getNextIrrigation(String direction) {
        int x = getCoordinates()[0];
        int y = getCoordinates()[1];
        switch(direction){
            case "NO":
                return new int[]{intValue((x-0.5)*2), intValue((y+0.5)*2)};
            case "NE":
                return new int[]{intValue(x*2), intValue((y+0.5)*2)};
            case "E":
                return new int[]{intValue((x+0.5)*2), intValue(y*2)};
            case "SE":
                return new int[]{intValue((x+0.5)*2), intValue((y-0.5)*2)};
            case "SO":
                return new int[]{intValue(x*2), intValue((y-0.5)*2)};
            case "O":
                return new int[]{intValue((x-0.5)*2), intValue(y*2)};
        }
        return null;
    }

    /**
     * @return la liste des emplacements possibles pour irriguer la parcelle
     */
    public ArrayList<int[]> irrigable() {
        String[] directionList = {"NO", "NE", "E", "SE", "SO", "O"} ;
        ArrayList<int[]> locations = new ArrayList<>();
        for(String direction : directionList){
            int x = getNextIrrigation(direction)[0];
            int y = getNextIrrigation(direction)[1];
            if(IrrigationBoard.getInstance().canAddIrrigation(x, y)){
                locations.add(new int[]{x, y});
            }
        }
        return locations;
    }

    public boolean equals(Parcel p){
        return this.coordinates[0] == p.getCoordinates()[0] && this.coordinates[1] == p.getCoordinates()[1];
    }
}
