package ir.maktabsharif.demofinalproject2.exeption;

public class UsernameAlreadyTakenException extends RuntimeException{
    public UsernameAlreadyTakenException(String message) {
        super(message);
    }

}
