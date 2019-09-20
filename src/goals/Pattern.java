package goals;

import parcels.ColorList;

import java.util.ArrayList;

public class Pattern {
    protected ArrayList<ColorList> color;
    protected Form form;

    public Pattern(Form form, ColorList color){
        this.form = form;
        this.color = new ArrayList<>();
        for(int i = 0; i <= form.getSize(); i++){
            this.color.add(color);
        }
    }

    public Pattern(Form form, ColorList color1, ColorList color2){
        this.form = form;
        this.color = new ArrayList<>();
        this.color.add(color1);
        this.color.add(color1);
        this.color.add(color2);
        this.color.add(color2);
    }

    public Form getForm(){
        return this.form;
    }

    public ArrayList<ColorList> getColor(){
        return color;
    }

    @Override
    public String toString() {
        String a ="";
        for (ColorList c : this.color)
            a = a+c+", ";
        return getForm()+", de couleur "+a;
    }
}

