package ba.edu.ibu.gym.core.exceptions.general;

public class CustomBlobStorageException extends RuntimeException{

    public CustomBlobStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
