package Tic_Tac_Toe.Exceptions;

public class InvalidGameStateException extends RuntimeException{
    public InvalidGameStateException(String message) {
        super(message);
    }
}
