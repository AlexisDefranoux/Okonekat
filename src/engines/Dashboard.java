package engines;

import bots.Barnabot;
import bots.Marcel;
import bots.Zerobot;
import bots.MotherBot;

public class Dashboard {

    private boolean logger;
    private int nbGame;
    private boolean stats;
    private MotherBot[] botList;
    private boolean graphics;

    public Dashboard(boolean logger, boolean stats, boolean graphics, int nbGame, MotherBot[] botList){
        this.logger = logger;
        this.stats = stats;
        this.nbGame = nbGame;
        this.botList = botList;
        this.graphics = graphics;
    }

    /**
     * Lance la simulation des games avec les paramètres passé par le constructeur
     */
    public void startSimulation() {

        Logger.setActivate(logger);
        Statistic statistic = new Statistic(botList);
        long percentage = -1;
        long ratioPrec;
        double ratio;
        String loading;
        boolean first =true;

        for (int i = 0; i<nbGame; i++) {
            Game myGame = new Game(botList, graphics);
            myGame.startGame();
            statistic.addGame(myGame);
            resetBot();

            if(!logger) {
                ratioPrec = percentage;
                ratio = (double) (i + 1) / nbGame;
                percentage = Math.round(ratio * 100);
                loading = "[";

                if (percentage != ratioPrec) {

                    for(int j = 0; j <percentage; j++)
                        loading += "=";
                    for(int j = 0; j <(100-percentage); j++)
                        loading += "-";

                    loading += "]";

                    System.out.print("\r" + loading);
                    //System.out.print("\r" + Math.round(ratio * 100));
                }
            }
        }
        if(stats)
            statistic.displayStatistic();
    }


    /**
     * Créé des nouveaux bots pour chaque game lancée. les anciens bots sont conservés pour les statistiques.
     */
    private void resetBot(){
        int i = 0;
        for (MotherBot b : botList){
            if(b.toString()=="Marcel")
                this.botList[i]= new Marcel();
            else if(b.toString() == "Barnabot")
                this.botList[i]= new Barnabot();
            else if(b.toString() == "Zerobot")
                this.botList[i]= new Zerobot();
            else
                this.botList[i]= new Marcel();
            i++;
        }
    }

}
