package exception;

import java.io.Serializable;

public class ArduinoException extends RuntimeException implements Serializable{
// code d'erreur
    private int code;

    public ArduinoException(int code) {
        super();
        this.code = code;
    }
    
    public ArduinoException(String message) {
        super(message);
    }

    public ArduinoException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ArduinoException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public ArduinoException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }
// getter et setter
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
