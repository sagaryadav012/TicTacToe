package Tic_Tac_Toe.Strategies.check_for_win;

import Tic_Tac_Toe.Models.Cell;

public interface PlayerWinStrategy {
    boolean checkForWin(Cell cell);
    void handleUndo(Cell cell);
}
