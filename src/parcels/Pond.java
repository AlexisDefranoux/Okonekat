package parcels;

public class Pond extends Parcel {

    public Pond(){
        int[] coordinates = {27,27};
        setCoordinates(coordinates);
    }

    public int getHeightBamboo(){return 0;}

    public ColorList getColor(){return null;}

    public void eatBamboo(){}

    public void growBamboo(){}
}
