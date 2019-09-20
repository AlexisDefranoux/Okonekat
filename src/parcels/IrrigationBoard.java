package parcels;

import bots.MotherBot;
import engines.*;
import java.util.ArrayList;
import static oracle.jrockit.jfr.events.Bits.intValue;

public class IrrigationBoard {

    private static IrrigationBoard irrigationBoard = new IrrigationBoard();

    private boolean[][] board;
    private int size;

    public static IrrigationBoard getInstance() {
        if(irrigationBoard == null)
            irrigationBoard = new IrrigationBoard();
        return irrigationBoard;
    }

    private IrrigationBoard() {
        size = 107;
        board = new boolean[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0 ; j < size; j++) {
                if(i%2 == 0 || j%2 == 0)
                    board[i][j] = false;
                }
        }
        board[54][55] = true;
        board[55][54] = true;
        board[55][53] = true;
        board[54][53] = true;
        board[53][54] = true;
        board[53][56] = true;
    }

    public static void reset() {
        irrigationBoard = new IrrigationBoard();
    }

    /**
     * @return l'état du réseau d'irrigation
     */
    public boolean[][] getBoard() { return board; }

    /**
     * @param x abscisse de l'emplacement
     * @param y ordonnée de l'emplacement
     * @return le numéro de type de l'irrigation concernée
     */
    public static int type(int x, int y) {
        if(x%2 == 0 && y%2 == 1)
            return 1;
        else if(x%2 == 1 && y%2 == 1)
            return 2;
        else if(x%2 == 1 && y%2 == 0)
            return 3;
        else
            return 0;
    }

    /**
     * @param x abscisse de l'emplacement
     * @param y ordonnée de l'emplacement
     * @return true si l'emplacement ets reliée par d'autres irrigations à l'étang, false sinon
     */
    public boolean connected(int x, int y) {
        switch(type(x, y)) {
            case 1:
                if(board[x-1][y] || board[x-1][y+1] || this.board[x+1][y] || this.board[x+1][y-1])
                    return true;
                break;
            case 2:
                if(board[x-1][y] || board[x][y+1] || board[x+1][y] || board[x][y-1])
                    return true;
                break;
            case 3:
                if(board[x][y-1] || board[x-1][y+1] || board[x][y+1] || board[x+1][y-1])
                    return true;
                break;
            case 0:
                return false;
        }
        return false;
    }

    /**
     * @param x abscisse de l'emplacement
     * @param y ordonnée de l'emplacement
     * @return true si une irrigation peut être ajoutée ici, false sinon
     */
    public boolean canAddIrrigation(int x, int y) {
        if(!IrrigationBoard.getInstance().getBoard()[x][y]) {

            double realX = 0.5 * x;
            double realY = 0.5 * y;
            switch (type(x, y)) {

                case 1:
                    if (GameBoard.getInstance().getBoard()[intValue(realX)][intValue(realY + 0.5)] != null
                            && GameBoard.getInstance().getBoard()[intValue(realX)][intValue(realY - 0.5)] != null
                            && connected(x, y)) {
                        return true;
                    } else {
                        return false;
                    }

                case 2:
                    if (GameBoard.getInstance().getBoard()[intValue(realX + 0.5)][intValue(realY - 0.5)] != null
                            && GameBoard.getInstance().getBoard()[intValue(realX - 0.5)][intValue(realY + 0.5)] != null
                            && connected(x, y)) {
                        return true;
                    } else {
                        return false;
                    }

                case 3:
                    if (GameBoard.getInstance().getBoard()[intValue(realX + 0.5)][intValue(realY)] != null
                            && GameBoard.getInstance().getBoard()[intValue(realX - 0.5)][intValue(realY)] != null
                            && connected(x, y)) {
                        return true;
                    } else {
                        return false;
                    }

                case 0:
                    return false;
            }
        }else {
            return false;
        }
        return false;
    }

    /**
     * Ajoute une irrigation aux coordonnées voulues si c'est possible.
     * @param coord coordonnées de l'emplacement
     * @param b bot qui veut ajouter l'irrigation
     */
    public void addIrrigation(int[] coord, MotherBot b) {
        int x = coord[0];
        int y = coord[1];
        double realX = 0.5 * x;
        double realY = 0.5 * y;

        if(canAddIrrigation(x, y)) {
            switch (type(x, y)) {

                case 1:
                    board[x][y] = true;
                    GameBoard.getInstance().getBoard()[intValue(realX)][intValue(realY + 0.5)].setIrrigation();
                    GameBoard.getInstance().getBoard()[intValue(realX)][intValue(realY - 0.5)].setIrrigation();
                    Logger.printLine(b + " ajoute une irrigation en [" + realX + ", " + realY + "].");
                    break;

                case 2:
                    board[x][y] = true;
                    GameBoard.getInstance().getBoard()[intValue(realX + 0.5)][intValue(realY - 0.5)].setIrrigation();
                    GameBoard.getInstance().getBoard()[intValue(realX - 0.5)][intValue(realY + 0.5)].setIrrigation();
                    Logger.printLine(b + " ajoute une irrigation en [" + realX + ", " + realY + "].");
                    break;

                case 3:
                    board[x][y] = true;
                    GameBoard.getInstance().getBoard()[intValue(realX + 0.5)][intValue(realY)].setIrrigation();
                    GameBoard.getInstance().getBoard()[intValue(realX - 0.5)][intValue(realY)].setIrrigation();
                    Logger.printLine(b + " ajoute une irrigation en [" + realX + ", " + realY + "].");
                    break;

            }
        }
    }

    /**
     * @return les emplacement possibles pour ajouter une irrigation
     */
    public ArrayList<int[]> irrigationFreeLocation() {
        ArrayList<int[]> irrigationFree = new ArrayList<>();
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(IrrigationBoard.getInstance().canAddIrrigation(i, j))
                    irrigationFree.add(new int[]{i,j});
            }
        }
        return irrigationFree;
    }
}
