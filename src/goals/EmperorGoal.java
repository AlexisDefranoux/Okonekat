package goals;

import parcels.Parcel;

import java.util.ArrayList;

public class EmperorGoal extends Goal{

    public EmperorGoal(int scoring){
        super(scoring);
    }

    @Override
    public boolean checkGoal(ArrayList<Parcel> list) {
        return false;
    }

    @Override
    public boolean checkGoal(int green, int yellow, int pink) {
        return false;
    }

    @Override
    public boolean checkGoal() {
        return false;
    }

    @Override
    public String toString() {
        return null;
    }
}
