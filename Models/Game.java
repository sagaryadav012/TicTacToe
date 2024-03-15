package Tic_Tac_Toe.Models;

import Tic_Tac_Toe.Exceptions.BotCountExceededException;
import Tic_Tac_Toe.Strategies.check_for_win.OrderOneWinningStrategy;
import Tic_Tac_Toe.Strategies.check_for_win.PlayerWinStrategy;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Board board;
    private List<Player> players;
    private int currentPlayerIndex;
    private List<Move> moves;
    private GameStatus gameStatus;
    private PlayerWinStrategy playerWinStrategy;

    private Game(Board board, List<Player> players, int currentPlayerIndex, List<Move> moves, GameStatus gameStatus, PlayerWinStrategy playerWinStrategy) {
        this.board = board;
        this.players = players;
        this.currentPlayerIndex = currentPlayerIndex;
        this.moves = moves;
        this.gameStatus = gameStatus;
        this.playerWinStrategy = playerWinStrategy;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void printBoard(){
        this.board.printBoard();
    }

    public void makeMove(){
        Player player = players.get(currentPlayerIndex);
        Pair<Integer, Integer> position = player.makeMove(this.board);
        int row = position.getKey();
        int col = position.getValue();

        while(!this.board.cellIsUnOccupied(row, col)){
            if(player instanceof HumanPlayer)
               System.out.println(row+","+col+" cell has been occupied, please make move on other cell");

            position = player.makeMove(this.board);
            row = position.getKey();
            col = position.getValue();
        }
        this.board.setPlayerOnCell(row, col, player);
        Cell cell = board.getCell(row, col);
        Move move = new Move(player, cell);
        moves.add(move);

        if(checkForWin(cell)){
            this.gameStatus = GameStatus.ENDED;
            return;
        }
        else if(checkForDraw()){
            this.gameStatus = GameStatus.DRAWN;
            return;
        }

        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % players.size();
    }

    public boolean checkForWin(Cell cell){
        return playerWinStrategy.checkForWin(cell);
    }

    public boolean checkForDraw(){
        int n = board.getSize();
        return moves.size() == n*n;
    }

    public Player getCurrentPlayer(){
        return players.get(currentPlayerIndex);
    }

    public void undo(){
        int prevPlayer = -1;
        if(gameStatus.equals(GameStatus.ENDED) || gameStatus.equals(GameStatus.DRAWN))
             prevPlayer = this.currentPlayerIndex;
        else prevPlayer = this.currentPlayerIndex - 1;

        if(prevPlayer < 0) prevPlayer += players.size();

        Player player = players.get(prevPlayer);
        if(player instanceof HumanPlayer){
            HumanPlayer humanPlayer = (HumanPlayer) player;
            if(humanPlayer.getPendingUndo() > 0){
                System.out.println("Hey "+player.getName()+", Do you want to undo last move? (y/n)");
                Scanner sc = new Scanner(System.in);
                String input = sc.next();
                if(input.charAt(0) == 'y' || input.charAt(0) == 'Y'){
                    // step1 : remove last move from moves
                    // step2 : set cell to empty(un_occupied)
                    // step3 : update maps in OrderOneWinningStrategy
                    // step4 : Give chance again to same user

                    Move move = moves.remove(moves.size() - 1);
                    Cell cell = move.getCell();
                    playerWinStrategy.handleUndo(cell);
                    cell.makeCellEmpty(); // now cell becomes empty. so don't use this cell in future
                    this.currentPlayerIndex = prevPlayer;
                    humanPlayer.decrementUndoCount();

                    System.out.println(player.getName()+" makes undo successfully");
                    if(humanPlayer.getPendingUndo() == 0){
                        System.out.println("Hey "+humanPlayer.getName()+" that was your last undo. no chance left, Play careful...");
                    }

                    if(gameStatus.equals(GameStatus.ENDED) || gameStatus.equals(GameStatus.DRAWN)){
                        gameStatus = GameStatus.IN_PROGRESS;
                    }
                }
            }
        }
    }

    public void replay(){
        this.board.resetBoard();
        int count = 1;
        for (Move move : moves) {
            Player player = move.getPlayer();
            Cell cell = move.getCell();
            int row = cell.getRow();
            int col = cell.getCol();

            board.setPlayerOnCell(row,col,player);
            System.out.println("Turn #" + count++ + ", player " + player.getName() + " makes a move");
            printBoard();
        }
    }

    public static GameBuilder getBuilder(){
        return new GameBuilder();
    }

    public static class GameBuilder{
        private Board board;
        private List<Player> players;
        private int currentPlayerIndex;
        private List<Move> moves;

        public GameBuilder setPlayers(List<Player> players) {
            this.players = players;
            this.board = new Board(players.size() + 1);
            return this;
        }

        public Game build() throws BotCountExceededException{
            int botCount = 0;
            for(Player player : players){
                if(player instanceof BotPlayer){
                    botCount += 1;
                    if(botCount > 1){
                        throw new BotCountExceededException("Can't start game. Found more than 1 bots, maximum only 1 bot is allowed");
                    }
                }
            }
            return new Game(this.board, this.players, 0, new ArrayList<>(), GameStatus.IN_PROGRESS, new OrderOneWinningStrategy(board.getSize()));
        }
    }
}
