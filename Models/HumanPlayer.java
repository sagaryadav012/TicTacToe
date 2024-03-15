package Tic_Tac_Toe.Models;

import javafx.util.Pair;

import java.util.Scanner;

public class HumanPlayer extends Player{
    private int pendingUndo;
    public HumanPlayer(String name, Symbol symbol, int pendingUndo) {
        super(name, symbol);
        this.pendingUndo = pendingUndo;
    }

    @Override
    public Pair<Integer, Integer> makeMove(Board board) {
        System.out.println("It's "+ this.getName() +"'s turn. Enter row and col :");
        Scanner sc = new Scanner(System.in);
        int row = sc.nextInt();
        int col = sc.nextInt();
        return new Pair<>(row, col);
    }

    public int getPendingUndo() {
        return pendingUndo;
    }

    public void decrementUndoCount(){
        this.pendingUndo -= 1;
    }
}
