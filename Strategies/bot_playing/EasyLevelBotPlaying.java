package Tic_Tac_Toe.Strategies.bot_playing;

import Tic_Tac_Toe.Exceptions.InvalidGameStateException;
import Tic_Tac_Toe.Models.Board;
import Tic_Tac_Toe.Models.Cell;
import javafx.util.Pair;

import java.util.List;

public class EasyLevelBotPlaying implements BotPlayingStrategy{
    @Override
    public Pair<Integer, Integer> makeMove(Board board) {
        List<List<Cell>> cells = board.getCells();
        for (List<Cell> row : cells) {
            for (Cell cell : row) {
                if(cell.cellIsUnOccupied()){
                   return new Pair<>(cell.getRow(), cell.getCol());
                }
            }
        }
        throw new InvalidGameStateException("There is no place for to make a move");
    }
}
