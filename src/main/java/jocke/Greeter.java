package jocke;

import akka.actor.UntypedActor;

/**
 * Created by joachim on 14/02/14.
 */
public class Greeter extends UntypedActor {

    String greeting = "";

    public Greeter(Object[] args) {
        greeting = "msg";
    }

    public void onReceive(Object message) {
        if (message instanceof WhoToGreet)
            greeting = "hello, " + ((WhoToGreet) message).who;

        else if (message instanceof Greet)
            // Send the current greeting back to the sender
            getSender().tell(new Greeting(greeting), getSelf());

        else unhandled(message);
    }
}
