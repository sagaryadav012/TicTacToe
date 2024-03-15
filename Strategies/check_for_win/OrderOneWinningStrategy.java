package Tic_Tac_Toe.Strategies.check_for_win;

import Tic_Tac_Toe.Models.Cell;
import Tic_Tac_Toe.Models.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderOneWinningStrategy implements PlayerWinStrategy{
    List<HashMap<Character, Integer>> rowsMap;
    List<HashMap<Character, Integer>> colsMap;
    HashMap<Character, Integer> diagonalMap;
    HashMap<Character, Integer> reverseDiagonalMap;

    int n;
    public OrderOneWinningStrategy(int n){
        this.n = n;
        rowsMap = new ArrayList<>();
        colsMap = new ArrayList<>();
        diagonalMap = new HashMap<>();
        reverseDiagonalMap = new HashMap<>();

        for(int i = 0; i < n; i++){
            rowsMap.add(new HashMap<>());
            colsMap.add(new HashMap<>());
        }
    }
    @Override
    public boolean checkForWin(Cell cell) {
        int row = cell.getRow();
        int col = cell.getCol();
        Player player = cell.getPlayer();
        Character symbol = player.getSymbol().getSymbol();

        // update maps
        updateMaps(row, col, symbol);

        return checkWinCondition(row, col, symbol);
    }
    public void updateMaps(int row, int col, Character symbol){
        HashMap<Character, Integer> rowMap = rowsMap.get(row);
        rowMap.put(symbol, rowMap.getOrDefault(symbol, 0) + 1);

        HashMap<Character, Integer> colMap = colsMap.get(col);
        colMap.put(symbol, colMap.getOrDefault(symbol, 0) + 1);

        if(checkIfCellISOnDiagonal(row, col)){
            diagonalMap.put(symbol, diagonalMap.getOrDefault(symbol, 0) + 1);
        }

        if(checkIfCellIsOnReverseDiagonal(row, col)){
            reverseDiagonalMap.put(symbol, reverseDiagonalMap.getOrDefault(symbol, 0) + 1);
        }

    }
    public boolean checkWinCondition(int row, int col, Character symbol){
       if(rowsMap.get(row).get(symbol) == n) return true;
       if(colsMap.get(col).get(symbol) == n) return true;
       if(checkIfCellISOnDiagonal(row, col) && diagonalMap.get(symbol) == n) return true;
       if(checkIfCellIsOnReverseDiagonal(row, col) && reverseDiagonalMap.get(symbol) == n) return true;

       return false;
    }
    public boolean checkIfCellISOnDiagonal(int row, int col){
        return row == col;
    }
    public boolean checkIfCellIsOnReverseDiagonal(int row, int col){
        return row + col == n-1;
    }

    @Override
    public void handleUndo(Cell cell) {
        int row = cell.getRow();
        int col = cell.getCol();

        Player player = cell.getPlayer();
        Character symbol = player.getSymbol().getSymbol();

        HashMap<Character, Integer> rowMap = rowsMap.get(row);
        HashMap<Character, Integer> colMap = colsMap.get(col);

        rowMap.put(symbol, rowMap.get(symbol) - 1);
        colMap.put(symbol, colMap.get(symbol) - 1);

        if(checkIfCellISOnDiagonal(row, col)){
            diagonalMap.put(symbol, diagonalMap.get(symbol) - 1);
        }

        if(checkIfCellIsOnReverseDiagonal(row, col)){
            reverseDiagonalMap.put(symbol, reverseDiagonalMap.get(symbol) - 1);
        }
    }
}
