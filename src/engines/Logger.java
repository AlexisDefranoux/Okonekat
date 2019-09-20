package engines;

public class Logger {

    private static boolean activate = false;

    /**
     * Gère l'affichage du projet. si activate est à true, affiche tous les logs dans la console.
     * @param string la phrase à afficher dans la console.
     */
    public static void printLine (String string)
    {
        if(activate)
            System.out.println(string);
    }

    public static void setActivate(boolean a) {
        activate = a;
    }
}