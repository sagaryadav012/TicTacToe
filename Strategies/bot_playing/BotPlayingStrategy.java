package Tic_Tac_Toe.Strategies.bot_playing;

import Tic_Tac_Toe.Models.Board;
import javafx.util.Pair;

public interface BotPlayingStrategy {
    Pair<Integer, Integer> makeMove(Board board);
}
