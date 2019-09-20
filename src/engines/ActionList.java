package engines;

public enum ActionList {

    //5 ACTIONS
    ActionPanda("déplacer le panda."), // Panda.getInstance().findBamboo()
    ActionGardener("déplacer le jardinier."), // Gardener.getInstance().findBamboo()
    ActionParcel("piocher une parcelle."), // Deck.getInstance().getRandomParcelList() + GameBoard.getInstance().freeParcelLocations()
    ActionGoal("piocher un objectif."), // Deck.getInstance().getGoalTypePossible()
    ActionIrrigation("piocher une irrigation."),

    //4 METEOS
    WeatherAddBamboo("poser un bambou grace à la météo."), // GameBoard.getInstance().whereCanGrow()
    WeatherMovePanda("bouger le panda grace à la météo."), // GameBoard.getInstance().whereCanEat()
    WeatherChooseImprovement("choisi un type d'aménagement."), // Deck.getInstance().getImprovementList()
    WeatherChooseWeather("choisi une météo."), // weatherList.getWeatherPossible()

    //4 ACTIONS GRATUITES
    FreePutIrrigation("choisi de poser une irrigation."), // IrrigationBoard.getInstance().irrigationFreeLocation() ou GameBoard.getInstance().irrigableParcels
    FreePutImprovement("choisi de poser un aménagement."), // GameBoard.getInstance().getAllParcelPossibleForImprovement()
    FreeCheckActionGoal("valider un objectif."), // checkGoals()
    FreeFinishMyTurn("finir son tour.");

    private String action;

    ActionList(String action){
        this.action = action;
    }

    public String toString() {
        return action;
    }
}
