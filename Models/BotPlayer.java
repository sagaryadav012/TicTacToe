package Tic_Tac_Toe.Models;

import Tic_Tac_Toe.Factories.BotPlayingStrategyFactory;
import Tic_Tac_Toe.Strategies.bot_playing.BotPlayingStrategy;
import javafx.util.Pair;

public class BotPlayer extends Player{
    private BotLevel botLevel;
    private BotPlayingStrategy botPlayingStrategy;
    public BotPlayer(String name, Symbol symbol, BotLevel botLevel) {
        super(name, symbol);
        this.botLevel = botLevel;
        this.botPlayingStrategy = BotPlayingStrategyFactory.getBotPlayingStrategy(botLevel);
    }

    @Override
    public Pair<Integer, Integer> makeMove(Board board) {
        return botPlayingStrategy.makeMove(board);
    }
}
