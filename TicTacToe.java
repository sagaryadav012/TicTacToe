package Tic_Tac_Toe;

import Tic_Tac_Toe.Controllers.GameController;
import Tic_Tac_Toe.Exceptions.BotCountExceededException;
import Tic_Tac_Toe.Models.*;

import java.util.*;

public class TicTacToe {
    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();

        Scanner sc = new Scanner(System.in);

        System.out.println("How many HumanPlayer ?");
        int humanPlayers = sc.nextInt();
        System.out.println("No.of undo per Human Player : ");
        int undoCount = sc.nextInt();

        Set<Character> symbolSet = new HashSet<>();
        for(int i = 1; i <= humanPlayers; i++){
            System.out.println("Enter name and Symbol of player "+i);
            String name = sc.next();
            String symbolStr = sc.next();
            Character symbol = symbolStr.charAt(0);
            while(symbolSet.contains(symbol)){
                System.out.println("This symbol has taken, Please Enter new symbol : ");
                symbolStr = sc.next();
                symbol = symbolStr.charAt(0);
            }
            symbolSet.add(symbol);
            players.add(new HumanPlayer(name, new Symbol(symbol), undoCount));
        }

        System.out.println("How many Bot players ?");
        int botPlayers = sc.nextInt();

        Random random = new Random();
        String s = "abcdefghijklmnopqrstuvwxyz1234567890";
        for(int i = 1; i <= botPlayers; i++){
            System.out.println("Enter bot"+i+" difficulty level (E/M/D)");
            String diffLevelStr = sc.next();
            char diffLevel = diffLevelStr.charAt(0);
            BotLevel botLevel;
            switch (diffLevel){
                case 'E' : botLevel = BotLevel.EASY;
                    break;
                case 'M' : botLevel = BotLevel.MEDIUM;
                    break;
                case 'D' : botLevel = BotLevel.DIFFICULT;
                    break;
                default:
                    System.out.println("Invalid input, Choosing easy!");
                    botLevel = BotLevel.EASY;
                    break;
            }

            // Generate Symbol for bot.
            int idx = random.nextInt(s.length()-1);
            Character symbol = s.charAt(idx);
            while(symbolSet.contains(symbol)){
                idx = random.nextInt(s.length()-1);
                symbol = s.charAt(idx);
            }
            symbolSet.add(symbol);
            players.add(new BotPlayer("Bot "+i, new Symbol(symbol), botLevel));
        }

        GameController gameController = new GameController();

        Game game;
        try {
            game = gameController.createGame(players);
        } catch (BotCountExceededException e) {
            System.out.println(e.getMessage());
            return;
        }

        // play game
        // while gameStatus = InProgress
        // 1. Print Board
        // 2. Make Move
        // 2.1 Check player has won?
        // 2.1.1 if wins, set gameStatus to ended
        // 2.1.2 else check is game draw
        // 2.1.3 else pass chance to next player

        while(gameController.getGameStatus(game).equals(GameStatus.IN_PROGRESS)){
            gameController.printBoard(game);
            gameController.makeMove(game);
            gameController.undo(game);
        }

        GameStatus gameStatus = gameController.getGameStatus(game);
        if(gameStatus.equals(GameStatus.ENDED)){
            gameController.printBoard(game);
            Player player = gameController.getCurrentPlayer(game);
            System.out.println("Game has ended");
            System.out.println("Player "+player.getName()+" won the game with symbol "+player.getSymbol().getSymbol());
        }
        else if(gameStatus.equals(GameStatus.DRAWN)){
            gameController.printBoard(game);
            System.out.println("Game has drawn");
        }

        System.out.println("Do you want reply the game? (y/n)");
        String replayReply = sc.next();
        if(replayReply.charAt(0) == 'y' || replayReply.charAt(0) == 'Y'){
            gameController.replay(game);
        }

    }
}
