package it.aendrix.skywars.exception;

public class PlayerAlredyInGameException extends Exception{

    public PlayerAlredyInGameException() {
        super();
    }

    public PlayerAlredyInGameException(String message) {
        super(message);
    }

}
