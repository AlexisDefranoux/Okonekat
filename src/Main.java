import bots.Barnabot;
import bots.Marcel;
import bots.Zerobot;
import engines.Dashboard;
import bots.MotherBot;

public class Main {

    public static void main(String[] args) {

        //Première simulation de 1000 parties : notre meilleur bot (Barnabot) vs un bot moins bon (Marcel) vs un bot aux actions aléatoires (Zerobot)
        MotherBot[] botList = {new Barnabot(), new Marcel(), new Zerobot()};
        Dashboard dashboard = new Dashboard(false, true, false,1000, botList);
        dashboard.startSimulation();

        //Seconde simulation de 1000 parties : notre meilleur bot contre lui-même (Barnabot)
        MotherBot[] botList2 = {new Barnabot(), new Barnabot()};
        Dashboard dashboard2 = new Dashboard(false, true, false,1000, botList2);
        dashboard2.startSimulation();

    }
}

