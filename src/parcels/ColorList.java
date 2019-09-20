package parcels;

public enum ColorList {

    GREEN("verte"),
    PINK("rose"),
    YELLOW("jaune"),
    MULTICOLOR("multicolor");

    private String color = "";

    ColorList(String color){
        this.color = color;
    }

    public String toString() {
        return color;
    }
}
