package goals;

import java.util.ArrayList;

public enum Form {

    STRAIGHT("straight"),
    TRIANGLE("triangle"),
    CURVE("curve"),
    DIAMOND("diamond");

    private String formName;
    private ArrayList<String> form;

    Form(String name){
        this.formName = name;
        form = new ArrayList<>();
        switch(name){
            case "straight":
                form.add("SE");
                form.add("SE");
                break;
            case "triangle":
                form.add("SO");
                form.add("E");
                break;
            case "curve":
                form.add("SO");
                form.add("SE");
                break;
            case "diamond":
                form.add("SE");
                form.add("SO");
                form.add( "NO");
        }
    }

    public ArrayList<String> getFormList() {
        return form;
    }

    public int getSize(){
        return form.size();
    }

    public String toString() {
        return formName;
    }
}
