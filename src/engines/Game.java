package engines;

import bots.MotherBot;
import goals.EmperorGoal;
import goals.GoalType;
import graphics.Window;
import characters.Gardener;
import characters.Panda;
import goals.Goal;
import parcels.GameBoard;
import parcels.IrrigationBoard;

import java.util.ArrayList;

public class Game {

    private final MotherBot[] botList;
    private Window window;
    private int goalNb;
    private int tourNb;
    boolean winner;
    EmperorGoal emperorGoal;
    boolean error;

    public Game(MotherBot[] botList, boolean graphics){

        this.botList = botList.clone();
        if(graphics)
            this.window = new Window();
        Panda.reset();
        GameBoard.reset();
        Gardener.reset();
        IrrigationBoard.reset();
        Deck.reset();
        for(MotherBot bot : botList){
            bot.setGoalList(Deck.getInstance().getRandomGoalList());
        }
        goalNb = 11-botList.length;
        winner = false;
        emperorGoal = new EmperorGoal(2);
        error = false;
    }

    /**
     * Permet de lancer une partie de takenoko. Fait jouer les bots un par un. Décide la fin de partie et le gagnant.
     */
    public void startGame(){

        for(MotherBot b : botList){
            Logger.printLine("\nObjectif de "+b+" : ");
            for (Goal goal : b.getGoalList()){
                Logger.printLine("-" +goal);
            }
        }

        for(tourNb = 1; tourNb<300; tourNb++){

            Logger.printLine("\n>>>>>>>>> Tour " + tourNb + " <<<<<<<<<<");

            for (MotherBot bot : botList){

                Logger.printLine("\n> " + bot + " : "+"Score="+bot.getTotalScore()+", Obj validé="+bot.getGoalListValidated().size()+", Rose=" +bot.getPinkBambooNb() +", Vert="+bot.getGreenBambooNb()+", Jaune="+bot.getYellowBambooNb() + ", Irr=" + bot.getIrrigationNb());

                if(bot.getGoalListValidated().contains(emperorGoal)) {
                    finishGame();
                    return;
                }

                Turn botTurn = new Turn();
                botTurn.startMyTurn(bot, tourNb, window);

                if(bot.getGoalListValidated().size()>=goalNb && winner == false){
                    winner = true;
                    Logger.printLine(bot + " a gagné la carte empereur en validant "+ bot.getGoalListValidated().size() +" sur "+goalNb+" objectifs.");
                    bot.addGoalListValidated(emperorGoal);
                    bot.setTotalScore(bot.getTotalScore()+2);
                }
            }

            if(window != null){
                window.renew();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        Logger.printLine("\n>>>>>>ERREUR<<<<<<\nAucun bot n'a réussi à gagner au bout de " + tourNb + " tours.");
        error = true;
        for(MotherBot b : botList){
            Logger.printLine("- Nombre d'objectifs validés de " + b + " : " + b.getGoalListValidated().size() + " sur " + goalNb + ".");
        }
    }

    /**
     * Indique le vainqueur. ou les vainqueurs en cas d'égalité de score de panda.
     */
    private void finishGame(){

        ArrayList<MotherBot> winnerBot = new ArrayList<>();

        String allname = "";
        for (MotherBot bot : botList) {

            if(winnerBot.size()==0) {
                bot.setWinner(true);
                winnerBot.add(bot);
                allname = bot.getName();

            }else if (bot.getTotalScore()>winnerBot.get(0).getTotalScore()){
                winnerBot.forEach(x->x.setWinner(false));
                winnerBot.clear();
                winnerBot.add(bot);
                bot.setWinner(true);
                allname = bot.getName();

            }else if (bot.getTotalScore() == winnerBot.get(0).getTotalScore()) {

                int scorePanda1 = 0;
                int scorePanda2 = 0;

                for(Goal g : bot.getGoalListValidated()){
                    if(g.getGoalType() == GoalType.PANDA)
                        scorePanda1 += g.getScoring();
                }

                for(Goal g : winnerBot.get(0).getGoalListValidated()){
                    if(g.getGoalType() == GoalType.PANDA)
                        scorePanda2 += g.getScoring();
                }

                if(scorePanda1==scorePanda2) {
                    bot.setWinner(true);
                    winnerBot.add(bot);
                    allname = allname + " et " + bot.getName();

                }else if(scorePanda1>scorePanda2){
                    winnerBot.forEach(x->x.setWinner(false));
                    winnerBot.clear();
                    winnerBot.add(bot);
                    bot.setWinner(true);
                    allname = bot.getName();
                }
            }
        }

        if(winnerBot.size()>1)
            Logger.printLine("\nEgalité : " + allname + " ont gagnés avec un score de "+winnerBot.get(0).getTotalScore()+" en " + tourNb + " tours !");
        else
            Logger.printLine("\n" + winnerBot.get(0) + " à gagné avec un score de "+winnerBot.get(0).getTotalScore()+" en " + tourNb + " tours !");

        if (window != null)
            window.renew();
    }

    public int getTourNb() {
        return tourNb;
    }

    public MotherBot[] getBotList() {
        return botList;
    }

    public boolean getError() { return error; }

}