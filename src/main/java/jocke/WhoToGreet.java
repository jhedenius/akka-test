package jocke;

import java.io.Serializable;

/**
 * Created by joachim on 14/02/14.
 */
public class WhoToGreet implements Serializable {
    public final String who;
    public WhoToGreet(String who) {
        this.who = who;
    }
}