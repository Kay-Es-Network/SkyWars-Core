package it.aendrix.skywars.exception;

public class PlayerIsNotInGameException extends Exception{

    public PlayerIsNotInGameException() {
        super();
    }

    public PlayerIsNotInGameException(String message) {
        super(message);
    }

}
