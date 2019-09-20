package parcels;

public enum ImprovementList {
    pond("bassin"),
    enclosure("cage"),
    fertilizer("engrais");

    private String improvement = "";

    ImprovementList(String improvement){
        this.improvement = improvement;
    }

    public String toString() {
        return improvement;
    }
}
