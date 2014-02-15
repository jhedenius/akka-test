package jocke;

import akka.actor.*;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class HelloAkkaJava {

    public static void main(String[] args) {
        // Create the 'helloakka' actor system
        final ActorSystem system = ActorSystem.create("helloakka");

        // Create the 'greeter' actor
        final ActorRef greeter = system.actorOf(Props.create(Greeter.class, null));

        // Create the "actor-in-a-box"
        final Inbox inbox = Inbox.create(system);

        // Tell the 'greeter' to change its 'greeting' message
        greeter.tell(new WhoToGreet("akka"), ActorRef.noSender());

        // Ask the 'greeter for the latest 'greeting'
        // Reply should go to the "actor-in-a-box"
        inbox.send(greeter, new Greet());

        // Wait 5 seconds for the reply with the 'greeting' message
        Greeting greeting1 = (Greeting) inbox.receive(Duration.create(5, TimeUnit.SECONDS));
        System.out.println("Greeting: " + greeting1.message);

        // Change the greeting and ask for it again
        greeter.tell(new WhoToGreet("typesafe"), ActorRef.noSender());
        inbox.send(greeter, new Greet());
        Greeting greeting2 = (Greeting) inbox.receive(Duration.create(5, TimeUnit.SECONDS));
        System.out.println("Greeting: " + greeting2.message);



        // after zero seconds, send a Greet message every second to the greeter with a sender of the GreetPrinter
        ActorRef greetPrinter = system.actorOf(Props.create(GreetPrinter.class, null));
        system.scheduler().schedule(Duration.Zero(), Duration.create(1, TimeUnit.SECONDS), greeter, new Greet(), (ExecutionContext)system.dispatcher(), greetPrinter);
    }

    public static class GreetPrinter extends UntypedActor {

        public GreetPrinter(Object[] args) {

        }

        public void onReceive(Object message) {
            if (message instanceof Greeting)
                System.out.println(((Greeting) message).message);
        }
    }
}
