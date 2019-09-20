package engines;

import java.util.*;

public enum WeatherList {

    SUN("soleil"),
    RAIN("pluie"),
    WIND("vent"),
    STORM("orage"),
    CLOUDS("nuages"),
    CHOOSEONE("?");

    private String weather;

    WeatherList(String weather){
        this.weather = weather;
    }

    public String toString() {
        return weather;
    }

    private static List<WeatherList> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));

    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    /**
     * Renvoie une météo random pour le dé météo de la classe Turn.
     */
    public WeatherList randomWeather()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    /**
     * Renvoie les météos possibles en fonction du nombre d'aménagement.
     */
    public ArrayList<WeatherList> getWeatherPossible() {

        ArrayList<WeatherList> list = new ArrayList<>();
        list.add(WeatherList.SUN);
        list.add(WeatherList.RAIN);
        list.add(WeatherList.WIND);
        list.add(WeatherList.STORM);

        if(Deck.getInstance().getImprovementNb()>0)
            list.add(WeatherList.CLOUDS);

        return list;
    }
}
