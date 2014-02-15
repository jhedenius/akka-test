package jocke;

import akka.testkit.TestActorRef;
import jocke.Greet;
import jocke.Greeter;
import jocke.Greeting;
import jocke.WhoToGreet;
import scala.concurrent.duration.Duration;
import akka.actor.*;
import akka.testkit.JavaTestKit;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.TimeUnit;


public class HelloAkkaTest {

    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void teardown() {
        system.shutdown();
        system.awaitTermination(Duration.create(10, TimeUnit.SECONDS));
    }

    @Test
    public void testSetGreeter() {
        new JavaTestKit(system) {{


            final TestActorRef<Greeter> greeter;
            Props g = Props.create(Greeter.class, null);

            /*
            greeter = TestActorRef.create(system, g, null);

            greeter.tell(new WhoToGreet("testkit"), getTestActor());

            Assert.assertEquals("hello, testkit", greeter.underlyingActor().greeting);
            */

        }};
    }

    @Test
    public void testGetGreeter() {
        new JavaTestKit(system) {{

            final ActorRef greeter = system.actorOf(Props.create(Greeter.class, null));

            greeter.tell(new WhoToGreet("testkit"), getTestActor());
            greeter.tell(new Greet(), getTestActor());

            final Greeting greeting = expectMsgClass(Greeting.class);

            new Within(duration("10 seconds")) {
                protected void run() {
                    Assert.assertEquals("hello, testkit", greeting.message);
                }
            };
        }};
    }
}
