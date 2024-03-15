package Tic_Tac_Toe.Factories;

import TicTacToe.Strategies.bot_playing.EasyBotPlayingStrategy;
import Tic_Tac_Toe.Models.BotLevel;
import Tic_Tac_Toe.Strategies.bot_playing.BotPlayingStrategy;
import Tic_Tac_Toe.Strategies.bot_playing.EasyLevelBotPlaying;

public class BotPlayingStrategyFactory {
    public static BotPlayingStrategy getBotPlayingStrategy(BotLevel botLevel){
        switch (botLevel){
            case EASY:
            case MEDIUM:
            case DIFFICULT:
                return new EasyLevelBotPlaying();
            default:
                throw new UnsupportedOperationException("The given bot level is not supported");
        }
    }
}
