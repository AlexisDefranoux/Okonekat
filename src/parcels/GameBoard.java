package parcels;

import engines.Logger;
import bots.MotherBot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GameBoard {

    private static GameBoard gameBoard = new GameBoard();

    private Parcel[][] board;
    private int size;

    public static GameBoard getInstance() {
        if (gameBoard == null)
            gameBoard = new GameBoard();
        return gameBoard;
    }

    private GameBoard() {
        this.size = 54;
        board = new Parcel[size][size];
        this.board[27][27] = new Pond();
    }

    public static void reset() {
        gameBoard = new GameBoard();
    }

    /**
     * Permet de connaitre la taille du plateau de jeu
     * @return la taille en int
     */
    public int getSize() {
        if (this.size > 0)
            return this.size;
        else
            return 0;
    }

    public Parcel[][] getBoard() {
        return this.board;
    }

    /**
     * @return une arraylist contenant les parcelles existante
     */
    public ArrayList<Parcel> getExistingParcel() {
        ArrayList<Parcel> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != null) {
                    list.add(board[i][j]);
                }
            }
        }
        return list;
    }

    /**
     * @param color la couleur voulue
     * @return une arraylist de toutes les parcelles de la couleur demandée
     */
    public ArrayList<ClassicParcel> getColoredParcel(ColorList color) {
        ArrayList<Parcel> uncoloredList = this.getExistingParcel();
        ArrayList<ClassicParcel> coloredList = new ArrayList<>();
        for (int i = 0; i < uncoloredList.size(); i++) {
            if (uncoloredList.get(i).getClass() == ClassicParcel.class) {
                ClassicParcel cp = (ClassicParcel) uncoloredList.get(i);
                if (cp.getColor().equals(color)) {
                    coloredList.add(cp);
                }
            }
        }
        return coloredList;
    }

    /**
     * @param x coordonnée x de la parcelle voulue
     * @param y coordonnée y de la parcelle voulue
     * @return la parcelle au coordonnées données
     */
    public Parcel getXYParcel(int x, int y) {
        return this.board[x][y];
    }

    /**
     * @param p parcelle de départ
     * @param direction direction vers laquelle on veut récupérer une parcelle
     * @return
     */
    public Parcel getNextParcel(Parcel p, String direction) {
        int x = p.getCoordinates()[0];
        int y = p.getCoordinates()[1];
        switch (direction) {
            case "NO":
                return this.getXYParcel(x - 1, y + 1);
            case "NE":
                return this.getXYParcel(x, y + 1);
            case "E":
                return this.getXYParcel(x + 1, y);
            case "SE":
                return this.getXYParcel(x + 1, y - 1);
            case "SO":
                return this.getXYParcel(x, y - 1);
            case "O":
                return this.getXYParcel(x - 1, y);
        }
        return null;
    }

    /**
     * @param x coordonnée x de la parcelle voulue
     * @param y coordonnée x de la parcelle voulue
     * @return une arraylist des parcelles voisines a celle voulue
     */
    public ArrayList<Integer[]> getNeighbor(int x, int y) {
        ArrayList<Integer[]> liste = new ArrayList<>();
        if (board[x][y] == null) {
            return liste;
        }
        if (x - 1 > 0) {
            if (board[x - 1][y] == null) {
                Integer[] temp = {x - 1, y};
                liste.add(temp);
            }
        }
        if (x + 1 < size) {
            if (board[x + 1][y] == null) {
                Integer[] temp = {x + 1, y};
                liste.add(temp);
            }
        }
        if (y - 1 > 0) {
            if (board[x][y - 1] == null) {
                Integer[] temp = {x, y - 1};
                liste.add(temp);
            }
        }
        if (y + 1 < size) {
            if (board[x][y + 1] == null) {
                Integer[] temp = {x, y + 1};
                liste.add(temp);
            }
        }
        if (x - 1 > 0 && y + 1 < size) {

            if (board[x - 1][y + 1] == null) {
                Integer[] temp = {x - 1, y + 1};
                liste.add(temp);
            }
        }
        if (x + 1 < size && y - 1 > 0) {
            if (board[x + 1][y - 1] == null) {
                Integer[] temp = {x + 1, y - 1};
                liste.add(temp);
            }
        }
        return liste;
    }

    /**
     * @param x coordonnée x de la parcelle voulue
     * @param y coordonnée x de la parcelle voulue
     * @return une arraylist des parcelles voisines vides a celle voulue
     */
    public ArrayList<Integer[]> getNullNeighbor(int x, int y) {
        ArrayList<Integer[]> liste = new ArrayList<>();
        if (board[x][y] != null) {
            return liste;
        }
        if (x - 1 > 0) {
            if (board[x - 1][y] != null) {
                Integer[] temp = {x - 1, y};
                liste.add(temp);
            }
        }
        if (x + 1 < size) {
            if (board[x + 1][y] != null) {
                Integer[] temp = {x + 1, y};
                liste.add(temp);
            }
        }
        if (y - 1 > 0) {
            if (board[x][y - 1] != null) {
                Integer[] temp = {x, y - 1};
                liste.add(temp);
            }
        }
        if (y + 1 < size) {
            if (board[x][y + 1] != null) {
                Integer[] temp = {x, y + 1};
                liste.add(temp);
            }
        }
        if (x - 1 > 0 && y + 1 < size) {
            if (board[x - 1][y + 1] != null) {
                Integer[] temp = {x - 1, y + 1};
                liste.add(temp);
            }
        }
        if (x + 1 < size && y - 1 > 0) {
            if (board[x + 1][y - 1] != null) {
                Integer[] temp = {x + 1, y - 1};
                liste.add(temp);
            }
        }
        return liste;
    }

    /**
     * @return une arraylist d'endoit ou il est possible de poser une parcelle
     */
    public ArrayList<Integer[]> freeParcelLocations() {
        ArrayList<Integer[]> fullList = new ArrayList<Integer[]>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ArrayList<Integer[]> tempList = new ArrayList<>();
                if (i == 27 && j == 27) {
                    tempList = getNeighbor(i, j);
                } else {
                    ArrayList<Integer[]> tempFullList = getNullNeighbor(i, j);
                    if (tempFullList.size() >= 2) {
                        Integer[] temp = {i, j};
                        tempList.add(temp);
                        //this.displayTable(tempList);
                    }
                }
                //System.out.println(tempList.size());
                if (tempList.size() != 0) {
                    //System.out.println("temp: " + tempList.size());
                    for (int k = 0; k < tempList.size(); k++) {
                        boolean verif = true;
                        //System.out.println("full: " + fullList.size());
                        if (fullList.size() != 0) {
                            for (int l = 0; l < fullList.size(); l++) {
                                if (tempList.get(k)[0] == fullList.get(l)[0] && tempList.get(k)[1] == fullList.get(l)[1]) {
                                    //System.out.println(tempList.get(k)[0] == fullList.get(l)[0] && tempList.get(k)[1] == fullList.get(l)[1]);
                                    verif = false;
                                }
                            }
                        }
                        if (verif) {
                            fullList.add(tempList.get(k));
                        }
                    }
                }
            }
        }
        return fullList;
    }


    /**
     *
     * @param p la parcelle a poser
     * @param b le bot qui fait l'action
     * @return pose une parcelle sur le plateau
     */
    public boolean addParcel(ClassicParcel p, MotherBot b) {
        ClassicParcel temp = p;
        int x = p.getCoordinates()[0];
        int y = p.getCoordinates()[1];
        if (x < 0 || x > 54 || y < 0 || y > 54 || board[x][y] != null) {
            return false;
        } else {
            ArrayList<Integer[]> hasNeighbor = this.getNullNeighbor(x, y);
            if (hasNeighbor.size() > 0) {
                int pos[] = {x, y};

                board[x][y] = p;
                Logger.printLine(b + " ajoute une parcelle " + temp.getColor() + " en [" + (x-27) + ", " + (y-27) + "].");

                if (Arrays.equals(pos, new int[]{28, 27}) || Arrays.equals(pos, new int[]{27, 28}) || Arrays.equals(pos, new int[]{28, 26})
                        || Arrays.equals(pos, new int[]{27, 26}) || Arrays.equals(pos, new int[]{26, 27}) || Arrays.equals(pos, new int[]{26, 28}))
                    board[x][y].setIrrigation();

                x -= 27;
                y -= 27;

                return true;
            }
            Logger.printLine("Impossible d'ajouter une parcelle ici.");
            return false;
        }
    }

    /**
     * @return les parcelles irrigables ainsi que les emplacements possibles pour les irriguer
     */
    public HashMap<Parcel, ArrayList<int[]>> irrigableParcels() {
        HashMap<Parcel, ArrayList<int[]>> irrigable = new HashMap<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.board[i][j] != null && !this.board[i][j].getIrrigation())
                    if(this.board[i][j].irrigable().size() != 0)
                        irrigable.put(this.board[i][j], this.board[i][j].irrigable());
            }
        }
        return irrigable;
    }

    /**
     * @return les parcelles où il est possibles de faire pousser du bambou
     */
    public ArrayList<ClassicParcel> whereCanGrow() {
        ArrayList<ClassicParcel> canGrowHere = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != null && board[i][j].canBambooGrow())
                    if (board[i][j].getClass() == ClassicParcel.class) {
                        canGrowHere.add((ClassicParcel) board[i][j]);
                    }
            }
        }
        return canGrowHere;
    }

    /**
     * @return les parcelles où il est possibles de manger du bambou
     */
    public ArrayList<Parcel> whereCanEat() {
        ArrayList<Parcel> canEatHere = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(board[i][j] != null && board[i][j].canEatBamboo())
                    canEatHere.add(board[i][j]);
            }
        }
        return canEatHere;
    }

    public ArrayList<ClassicParcel> getAllParcelPossibleForImprovement(){
        ArrayList<ClassicParcel> list = new ArrayList<>();

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                ClassicParcel a;
                if(board[i][j] != null && board[i][j].getClass() == ClassicParcel.class) {
                    a = (ClassicParcel) board[i][j];
                    if (a.getImprovement() == null)
                        list.add(a);
                }
            }
        }
        return list;
    }
}
