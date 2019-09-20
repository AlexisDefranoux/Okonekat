package engines;

import bots.MotherBot;

import java.util.ArrayList;

public class Statistic {

    private ArrayList<Game> gameList = new ArrayList<>();
    private MotherBot[] botList;
    private int[] nbWin = {0, 0, 0, 0};
    private double[] ratio = {0, 0, 0, 0};
    private double[] ecartType = {0 , 0, 0 ,0};
    private int[] moyenneNbtour = {0, 0, 0, 0};
    private float[] moyenneScore = {0, 0, 0, 0};
    private int egaliteNb = 0;
    private int errorNb = 0;

    public Statistic(MotherBot[] botList){
        this.botList = botList;
    }

    /**Ajoute la partie g à la liste des parties dont on étudies les stats.
     * @param g la partie terminé.
     */
    public void addGame(Game g) {
        gameList.add(g);
    }

    /**
     * Affiche les satistiques des parties jouées : parties gagnées, égalités, non finies, scores moyens.
     */
    public void displayStatistic(){

        for (Game g: gameList) {

            boolean egalite = false;

            for(int i = 0; i<botList.length; i++) {

                if (g.getBotList()[i].getWinner() && egalite)
                    egaliteNb++;

                if (g.getBotList()[i].getWinner()) {
                    egalite = true;
                    this.nbWin[i]++;
                    moyenneScore[i] += g.getBotList()[i].getTotalScore();
                }
            }

            if(g.getError())
                errorNb++;
        }

        for(int i = 0; i<botList.length; i++) {
            moyenneScore[i] /= gameList.size();
        }

        for(int i = 0; i<botList.length; i++)
            ratio[i] = (double) nbWin[i]/gameList.size();

        for(int i = 0; i<botList.length; i++)
            ecartType[i] = Math.rint(Math.sqrt(Math.pow((1-ratio[i]), 2.0) * nbWin[i]));

        System.out.println("\n// Statistiques \\\\\n ");
        System.out.println("Sur " + gameList.size() + " parties : \n");

        for(int i = 0; i<botList.length; i++) {
            System.out.println("- "+botList[i] + " a gagne " + nbWin[i] + " parties, soit " + Math.round(ratio[i]*100) + "% des parties. Il a obtenu un score moyen de " + Math.round(moyenneScore[i]) + " points par partie.");
        }

        System.out.println("\n- Il y a eu "+ egaliteNb + " egalite(s), soit "+(egaliteNb*100)/gameList.size()+"% des parties.");
        System.out.println("- "+errorNb + " partie(s) ne se sont pas terminees en moins de 300 tours, soit "+(errorNb*100)/gameList.size()+"% des parties.\n");
    }
}
