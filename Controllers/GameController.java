package Tic_Tac_Toe.Controllers;

import Tic_Tac_Toe.Exceptions.BotCountExceededException;
import Tic_Tac_Toe.Models.Game;
import Tic_Tac_Toe.Models.GameStatus;
import Tic_Tac_Toe.Models.Player;

import java.util.List;

public class GameController {
    public Game createGame(List<Player> players) throws BotCountExceededException {
        return Game.getBuilder().setPlayers(players).build();
    }
    public GameStatus getGameStatus(Game game){
        return game.getGameStatus();
    }
    public void printBoard(Game game){
        game.printBoard();
    }
    public void makeMove(Game game){
        game.makeMove();
    }
    public Player getCurrentPlayer(Game game){
        return game.getCurrentPlayer();
    }
    public void undo(Game game){
        game.undo();
    }
    public void replay(Game game){
        game.replay();
    }
}
