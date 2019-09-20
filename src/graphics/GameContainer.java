package graphics;

import characters.Gardener;
import characters.Panda;
import parcels.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameContainer extends JPanel{

    private ArrayList<Parcel> parcels = new ArrayList<>();
    private static final int thickness = 30;
    private static final int multiply = 30;

    public void paintComponent(Graphics g){
        try{
            Image bg = ImageIO.read(new File("background.jpg"));
            g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), this);
        }catch(IOException e){
            e.printStackTrace();
        }
        if(parcels.size() > 0){
            for(Parcel p : parcels){
                int[] temp;
                temp = this.coordConverter(p.getCoordinates()[0], p.getCoordinates()[1]);
                int x = temp[0];
                int y = temp[1];
                if(p.getColor() != null){
                    switch(p.getColor()){
                        case GREEN:
                            g.setColor(Color.GREEN);
                            break;
                        case PINK:
                            g.setColor(Color.PINK);
                            break;
                        case YELLOW:
                            g.setColor(Color.YELLOW);
                    }
                }else{
                    g.setColor(Color.BLUE);
                }
                g.fillRect( x, y, thickness, thickness);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, thickness, thickness);

                String bamboo = "" + p.getHeightBamboo();
                g.drawString(bamboo, x + thickness/2-5, y + thickness-5);
                if(p.getClass() == ClassicParcel.class){
                    ClassicParcel tempo = (ClassicParcel) p;
                    if(tempo.getImprovement() != null){
                        String layout = tempo.getImprovement().toString().substring(0, 1);
                        g.drawString(layout, x + thickness - 10, y + thickness - 5);
                    }
                }
                
                temp = this.coordConverter(Panda.getInstance().getPosition().getCoordinates()[0], Panda.getInstance().getPosition().getCoordinates()[1]);
                int px = temp[0];
                int py = temp[1];

                g.drawString("P",  px+5, py+thickness/2);

                temp = this.coordConverter(Gardener.getInstance().getPosition().getCoordinates()[0], Gardener.getInstance().getPosition().getCoordinates()[1]);
                int gx = temp[0];
                int gy = temp[1];
                g.drawString("G", gx + thickness - 10, gy+ thickness/2);
            }
        }
    }

    public void renew(){
        parcels = GameBoard.getInstance().getExistingParcel();
    }

    public int[] coordConverter(int x, int y){
        double ny = -(y-27)+27;
        double nx = (x*multiply)-(thickness*((ny-27)/2))-540;
        ny = ny*multiply - 540;
        int[] back = {(int) nx, (int) ny};
        return back;
    }


}
