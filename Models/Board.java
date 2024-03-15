package Tic_Tac_Toe.Models;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<List<Cell>> cells;

    public Board(int n) {
        this.cells = new ArrayList<>();
        for(int i = 0; i < n; i++){
            List<Cell> row = new ArrayList<>();
            for(int j = 0; j < n; j++){
                Cell cell = new Cell(i, j, CellStatus.UN_OCCUPIED);
                row.add(cell);
            }
            cells.add(row);
        }
    }

    public void printBoard(){
        for (List<Cell> row : this.cells) {
            for (Cell cell : row) {
                cell.printCell();
            }
            System.out.println();
        }
    }

    public boolean cellIsUnOccupied(int row, int col){
        Cell cell = getCell(row, col);
        return cell.cellIsUnOccupied();
    }

    public Cell getCell(int row, int col){
        return cells.get(row).get(col);
    }

    public void setPlayerOnCell(int row, int col, Player player){
        Cell cell = getCell(row, col);
        cell.setPlayer(player);
    }

    public int getSize(){
        return cells.size();
    }

    public void resetBoard(){
        for (List<Cell> row : cells) {
            for (Cell cell : row) {
                cell.makeCellEmpty();
            }
        }
    }

    public List<List<Cell>> getCells() {
        return cells;
    }
}
