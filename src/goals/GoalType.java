package goals;

public enum GoalType {

    PANDA("panda"),
    GARDENER("jardinier"),
    PARCEL("parcel");

    private String name = "";

    GoalType(String name){
        this.name = name;
    }

    public String toString() {
        return name;
    }
}