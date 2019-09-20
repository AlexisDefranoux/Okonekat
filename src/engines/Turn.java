package engines;

import bots.MotherBot;
import characters.Gardener;
import characters.Panda;
import goals.Goal;
import goals.GoalType;
import goals.PandaGoal;
import graphics.Window;
import parcels.GameBoard;
import parcels.ImprovementList;
import parcels.IrrigationBoard;

import java.util.ArrayList;

import static engines.ActionList.*;
import static engines.WeatherList.WIND;

public class Turn {

    private int actionNb;
    ArrayList<ActionList> actionList = new ArrayList<>();

    public Turn() {

        actionNb = 2;
        //ACTIONS
        actionList.add(ActionList.ActionGardener);
        actionList.add(ActionList.ActionPanda);
        actionList.add(ActionList.ActionIrrigation);
        actionList.add(ActionList.ActionGoal);
        actionList.add(ActionList.ActionParcel);

        //GRATUITES
        actionList.add(ActionList.FreeCheckActionGoal);
        actionList.add(ActionList.FreeFinishMyTurn);
    }

    private boolean availableActionParcel() {
        if (Deck.getInstance().getParcelList().size() > 0) {
            return true;
        }
        return false;
    }

    public int getActionNb() {
        return actionNb;
    }

    /**
     * Initialise les actions du bot à vide.
     */
    private void zeroAction() {
        actionList.clear();
        actionList.add(ActionList.FreeFinishMyTurn);
    }

    /**
     * Démarre le tour d'un bot
     * @param bot le bot qui doit jouer ses actions.
     * @param tourNb le tour du jeu ou l'on se trouve.
     * @param w la fenêtre graphique de debug.
     */
    public void startMyTurn(MotherBot bot, int tourNb, Window w) {

        Action botAction = new Action();
        WeatherList myWeather = weatherFirst(bot, tourNb, botAction);

        while (botAction.getActionList() != FreeFinishMyTurn) {

            if (actionNb == 0)
                // Enlève toutes les actions
                zeroAction();
            else{
                // Remove si le deck est vide ou l'inventaire du bot est au max
                if (bot.getGoalList().size() >= 5 || Deck.getInstance().getGoalTypePossible().size() < 1)
                    actionList.remove(ActionList.ActionGoal);
                if (Deck.getInstance().getIrrigationNb() < 1)
                    actionList.remove(ActionList.ActionIrrigation);
                if (!availableActionParcel())
                    actionList.remove(ActionList.ActionParcel);
            }

            // add si inventaire du bot est ok
            if (bot.getImprovementList().size() > 0)
                actionList.add(ActionList.FreePutImprovement);
            if (bot.getIrrigationNb() > 0)
                actionList.add(ActionList.FreePutIrrigation);
            if(bot.getGoalList().size() >0)
                actionList.add(ActionList.FreeCheckActionGoal);

            Logger.printLine("NOMBRE ACTION : " + actionNb);
            botAction = bot.itsMyTurn(tourNb, actionNb, actionList);
            Logger.printLine(bot + " a choisi de " + botAction.getActionList());

            actionNb--;

            if (myWeather != WIND && botAction.getActionList() != FreeFinishMyTurn && botAction.getActionList() != FreeCheckActionGoal && botAction.getActionList() != FreePutImprovement && botAction.getActionList() != FreePutIrrigation)
                actionList.remove(botAction.getActionList());

            switch (botAction.getActionList()) {
                case ActionPanda:
                    Panda.getInstance().move(botAction.getCoordinates(), bot);
                    break;

                case ActionGardener:
                    Gardener.getInstance().move(botAction.getCoordinates(), bot);
                    break;

                case ActionParcel:
                    Deck.getInstance().removeParcel(botAction.getParcel());
                    botAction.getParcel().setCoordinates(botAction.getCoordinates());
                    GameBoard.getInstance().addParcel(botAction.getParcel(), bot);
                    break;

                case ActionGoal:
                    bot.addGoal(Deck.getInstance().getRandomGoal(botAction.getGoalType()));
                    break;

                case ActionIrrigation:
                    Deck.getInstance().setIrrigationNb(Deck.getInstance().getIrrigationNb() - 1);
                    bot.setIrrigationNb(bot.getIrrigationNb() + 1);
                    break;

                case FreePutImprovement:
                    actionNb++;
                    botAction.getParcel().setImprovement(botAction.getImprovement());
                    ArrayList<ImprovementList> list = bot.getImprovementList();
                    list.remove(botAction.getImprovement());
                    bot.setImprovementList(list);
                    if (bot.getImprovementList().size() < 1)
                        actionList.remove(ActionList.FreePutImprovement);
                    break;

                case FreePutIrrigation:
                    actionNb++;
                    bot.setIrrigationNb(bot.getIrrigationNb() - 1);
                    IrrigationBoard.getInstance().addIrrigation(botAction.getCoordinates(), bot);
                    if (bot.getIrrigationNb() < 1)
                        actionList.remove(ActionList.FreePutIrrigation);
                    break;

                case FreeCheckActionGoal:
                    actionNb++;
                    bot.setTotalScore(bot.getTotalScore() + botAction.getGoal().getScoring());
                    Logger.printLine(bot + " a validé l'objectif " + botAction.getGoal());

                    PandaGoal g;
                    if (botAction.getGoal().getGoalType() == GoalType.PANDA) {
                        g = (PandaGoal) botAction.getGoal();
                        switch (g.getColor()) {
                            case GREEN:
                                bot.setGreenBambooNb(bot.getGreenBambooNb() - 2);
                                break;
                            case YELLOW:
                                bot.setYellowBambooNb(bot.getYellowBambooNb() - 2);
                                break;
                            case PINK:
                                bot.setPinkBambooNb(bot.getPinkBambooNb() - 2);
                                break;
                            case MULTICOLOR:
                                bot.setPinkBambooNb(bot.getPinkBambooNb() - 1);
                                bot.setYellowBambooNb(bot.getYellowBambooNb() - 1);
                                bot.setGreenBambooNb(bot.getGreenBambooNb() - 1);
                                break;
                        }
                    }

                    bot.addGoalListValidated(botAction.getGoal());

                    ArrayList<Goal> goal = bot.getGoalList();
                    goal.remove(botAction.getGoal());
                    bot.setGoalList(goal);
                    break;

                case FreeFinishMyTurn:
                    actionNb = 0;
                    break;

                default:
                    actionNb++;
                    break;
            }

            if (myWeather != WIND && !availableActionParcel() && botAction.getActionList() != FreeFinishMyTurn && botAction.getActionList() != FreeCheckActionGoal && botAction.getActionList() != FreePutImprovement && botAction.getActionList() != FreePutIrrigation)
                actionList.remove(botAction.getActionList());

            if (w != null) {
                w.renew();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Fait jouer le bot en fonction du dé météorologique. Renvoie la météo.
     * @param bot le bot qui doit jouer ses actions.
     * @param tourNb le tour du jeu ou l'on se trouve.
     */
    private WeatherList weatherFirst(MotherBot bot, int tourNb, Action botAction){

        WeatherList myWeather = WeatherList.CLOUDS;

        if (tourNb > 1) {

            myWeather = myWeather.randomWeather();
            Logger.printLine("Le dé météorologique affiche " + myWeather + ".");

            switch (myWeather) {

                case SUN:
                    actionNb++;
                    break;

                case RAIN:
                    ArrayList<ActionList> rain = new ArrayList<>();
                    rain.add(ActionList.WeatherAddBamboo);
                    botAction = bot.itsMyTurn(tourNb, actionNb, rain);
                    if (botAction.getParcel() != null)
                        botAction.getParcel().growBamboo();
                    else
                        Logger.printLine("Erreur null pointer exception sur la parcel de WeatherAddBamboo");
                    break;

                case STORM:
                    ArrayList<ActionList> storm = new ArrayList<>();
                    storm.add(ActionList.WeatherMovePanda);
                    botAction = bot.itsMyTurn(tourNb, actionNb, storm);
                    if(botAction.getCoordinates() != null)
                        Panda.getInstance().movePanda(botAction.getCoordinates(), bot);
                    else
                        Logger.printLine("Erreur null pointer exception sur coordinates de WeatherMovePanda");
                    break;

                case CLOUDS:

                    if (Deck.getInstance().getImprovementNb() > 0){
                        ArrayList<ActionList> clouds = new ArrayList<>();
                        clouds.add(ActionList.WeatherChooseImprovement);
                        botAction = bot.itsMyTurn(tourNb, actionNb, clouds);

                        if(botAction.getImprovement() != null) {
                            Logger.printLine(bot + " à choisi un aménagement "+botAction.getImprovement());
                            ArrayList<ImprovementList> impList = bot.getImprovementList();
                            impList.add(botAction.getImprovement());
                            bot.setImprovementList(impList);
                            Deck.getInstance().removeImprovement(botAction.getImprovement());
                        }else
                            Logger.printLine("Erreur null pointer exception sur improvement de WeatherMovePanda");
                    } else {
                        Logger.printLine("Il n'y a plus d'aménagement. Choisissez une météo.");
                        myWeather = chooseWeather(botAction, bot, tourNb, myWeather);
                    }
                    break;

                case CHOOSEONE:
                    myWeather = chooseWeather(botAction, bot, tourNb, myWeather);
                    break;
            }
        }
        return myWeather;
    }

    private WeatherList chooseWeather(Action botAction, MotherBot bot, int tourNb, WeatherList myWeather){

        ArrayList<ActionList> choose = new ArrayList<>();
        choose.add(ActionList.WeatherChooseWeather);
        botAction = bot.itsMyTurn(tourNb, actionNb, choose);

        myWeather = botAction.getWeatherList();
        Logger.printLine(bot + " à choisi la météo " + myWeather);

        switch (myWeather) {
            case SUN:
                actionNb++;
                break;

            case RAIN:
                ArrayList<ActionList> rain = new ArrayList<>();
                rain.add(ActionList.WeatherAddBamboo);
                botAction = bot.itsMyTurn(tourNb, actionNb, rain);
                if (botAction.getParcel() != null)
                    botAction.getParcel().growBamboo();
                break;

            case STORM:
                ArrayList<ActionList> storm = new ArrayList<>();
                storm.add(ActionList.WeatherMovePanda);
                botAction = bot.itsMyTurn(tourNb, actionNb, storm);
                if(botAction.getCoordinates() != null)
                    Panda.getInstance().movePanda(botAction.getCoordinates(), bot);
                break;

            case CLOUDS:
                ArrayList<ActionList> clouds = new ArrayList<>();
                clouds.add(ActionList.WeatherChooseImprovement);
                botAction = bot.itsMyTurn(tourNb, actionNb, clouds);
                if(botAction.getImprovement() != null) {
                    Logger.printLine(bot + " à choisi un aménagement "+botAction.getImprovement());
                    ArrayList<ImprovementList> impList = bot.getImprovementList();
                    impList.add(botAction.getImprovement());
                    bot.setImprovementList(impList);
                    Deck.getInstance().removeImprovement(botAction.getImprovement());
                } else
                    Logger.printLine("Erreur null pointer exception sur improvement de WeatherMovePanda");
                break;
        }
        return myWeather;
    }
}
