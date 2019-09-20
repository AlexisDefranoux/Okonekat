package goals;

import parcels.ClassicParcel;
import parcels.Parcel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Goal {

    protected int scoring;
    protected GoalType goalType;

    public Goal(int scoring) {
        this.scoring = scoring;
    }

    public int getScoring() { return this.scoring; }

    public GoalType getGoalType() {
        return goalType;
    }

    public abstract boolean checkGoal(int green, int yellow, int pink);

    public abstract boolean checkGoal();

    public abstract boolean checkGoal(ArrayList<Parcel> list);

    public abstract String toString();
}
