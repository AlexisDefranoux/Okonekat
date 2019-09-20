package engines;

import goals.Goal;
import goals.GoalType;
import parcels.ClassicParcel;
import parcels.ImprovementList;

public class Action {

    private ActionList action;
    private ClassicParcel parcel;
    private int[] coordinates;
    private GoalType goalType;
    private Goal goal;
    private WeatherList weatherList;
    private ImprovementList improvement;

    /**
     * Place la parcel pioché et choisi précédemment
     * @param action ActionList.ActionParcel
     * @param parcel ClassicParcel pioché et choisi parmi les 3 parcel de Deck.getInstance().getRandomParcelList()
     * @param coordinates les coordonnées ou l'on veut poser la parcel grace à GameBoard.getInstance().freeParcelLocations()
     */
    public Action(ActionList action, ClassicParcel parcel, int[] coordinates){
        this.action = action;
        this.parcel = parcel;
        this.coordinates = coordinates;
    }

    /**
     * Déplace le panda ou le gardener
     * @param action ActionList.ActionPanda ou ActionList.ActionGardener ou ActionList.WeatherMovePanda ou ActionList.FreePutIrrigation
     * @param coordinates Ou l'on veut déplacer le panda ou le gardener : Panda.getInstance().findBamboo() ou Gardener.getInstance().findBamboo() ou GameBoard.getInstance().whereCanEat() pour le WeatherMovePanda ou IrrigationBoard.getInstance()....
     */
    public Action(ActionList action, int[] coordinates){
        this.action = action;
        this.coordinates = coordinates;
    }

    /**
     * Choisi de piocher un objectif de type jardinier, panda ou parcel en fct des disponibilités
     * @param action ActionList.ActionGoal
     * @param goalType le type d'objectif choisi parmi les 3 renvoyé par Deck.getInstance().getGoalTypePossible()
     */
    public Action(ActionList action, GoalType goalType){
        this.action = action;
        this.goalType = goalType;
    }

    /**
     * Lorsque le dé tombe sur chooseone, le bot choisi ca météo. Ne compte pas comme une action.
     * @param action ActionList.WeatherChooseWeather
     * @param weatherList renvoie la météo choisi parmi les 5 ou 4 météos renvoyé par weatherList.getWeatherPossible()
     */
    public Action(ActionList action, WeatherList weatherList){
        this.action = action;
        this.weatherList = weatherList;
    }

    /**
     * Lorsque le dé tombe sur WeatherChooseWeather, le bot choisi ca météo. Ne compte pas comme une action.
     * @param action ActionList.WeatherAddBamboo ou ActionList.FreePutImprovement
     * @param parcel la parcel sur lequel on rajoute un bambou/aménagement choisi avec GameBoard.getInstance().whereCanGrow();
     */
    public Action(ActionList action, ClassicParcel parcel){
        this.action = action;
        this.parcel = parcel;
    }

    /**
     * Lorsque le dé tombe sur WeatherChooseImprovement, le bot choisi un aménagement parmi les 3 aménagements possible
     * @param action ActionList.WeatherChooseImprovement
     * @param improvement l'aménagement choisi à l'aide de Deck.getInstance().getImprovementList() et getAllParcelPossibleForImprovement();
     */
    public Action(ActionList action, ImprovementList improvement){
        this.action = action;
        this.improvement = improvement;
    }

    /**
     * Pour poser un improvement
     * @param action ActionList.WeatherChooseImprovement
     * @param improvement l'aménagement choisi dans l'inventaire
     * @param parcel la parcel choisi avec getAllParcelPossibleForImprovement();
     */
    public Action(ActionList action, ImprovementList improvement, ClassicParcel parcel){
        this.action = action;
        this.improvement = improvement;
        this.parcel = parcel;
    }

    /**
     * Choisi de valider un objectif, peut le faire à l'infini n'importe quand. Ne compte pas comme une action.
     * @param action ActionList.FreeCheckActionGoal
     * @param goal renvoie l'objectif validé par CheckGoals()
     */
    public Action(ActionList action,Goal goal){
        this.action = action;
        this.goal = goal;
    }

    /**
     * Permet de finir son tour, ou de piocher une irrigation ou de renvoyer la météo
     * @param action ActionList.FreeFinishMyTurn ou ActionList.ActionIrrigation
     */
    public Action(ActionList action){
        this.action = action;
    }

    /**
     * Pas utile pour les bots. Permet de récupérer l'action dans Turn
     */
    public Action(){ }

    public ActionList getActionList() {
        return action;
    }

    public ClassicParcel getParcel() {
        return parcel;
    }

    public int[] getCoordinates(){
            return coordinates;
    }

    public GoalType getGoalType() {
        return goalType;
    }

    public Goal getGoal() {
        return goal;
    }

    public WeatherList getWeatherList() {
        return weatherList;
    }

    public ImprovementList getImprovement() {
        return improvement;
    }
}
