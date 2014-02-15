package jocke;

import java.io.Serializable;

/**
 * Created by joachim on 14/02/14.
 */
public class Greeting implements Serializable {

    public final String message;

    public Greeting(String message) {
        this.message = message;
    }
}
