import java.util.Random;
import java.util.function.Consumer;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import akka.actors.PrimeNumbersMaster;
import akka.actors.ResultPrinterActor;
import akka.actors.seq.PrimeNumbersSeqMaster;
import akka.messages.NumberMessage;
import akka.routing.RoundRobinRouter;
import demo.numbers.PrimeNumbersAdder;
import demo.numbers.util.StopWatch;

public class PerformanceTest
{

   final static Logger LOG = Logger.getLogger( PerformanceTest.class );

   final static int NO_OF_MESSAGES = 20;

   final static int NO_OF_WORKERS = 10;

   final static int RANDOM_NUMBER_LIMIT = 100000;

   private Random rand;

   private ActorSystem actorSystem;

   private ActorRef printer;


   @Before
   public void setUp()
   {

      this.rand = new Random( 324 );

      this.actorSystem = ActorSystem.create( "actorSystem" );

      this.printer = this.actorSystem.actorOf( new Props( new UntypedActorFactory()
      {
         public UntypedActor create()
         {
            return new ResultPrinterActor( PerformanceTest.NO_OF_MESSAGES, PerformanceTest.this.actorSystem );
         }
      } ), "printer" );
   }


   @Test
   public void testWithoutActors()
   {
      final StopWatch stopWatch = new StopWatch();
      stopWatch.start();

      sendMultipleMessages( ( x ) ->
      {
         final PrimeNumbersAdder adder = new PrimeNumbersAdder( x );
         final int sumOfPrimeNumbers = adder.calculate();

         System.out.println( "" );
         PerformanceTest.LOG.info( "Sum of prime numbers lower then " + x + " is " + sumOfPrimeNumbers );
      } );

      stopWatch.stop();
   }


   @Test
   public void testWithActors()
   {

      final ActorRef master = this.actorSystem.actorOf( new Props( new UntypedActorFactory()
      {
         public UntypedActor create()
         {
            return new PrimeNumbersMaster( PerformanceTest.NO_OF_WORKERS, PerformanceTest.this.printer );
         }
      } ), "master" );

      sendMultipleMessages( ( x ) ->
      {
         master.tell( new NumberMessage( x ), null );
      } );

      this.actorSystem.awaitTermination();
   }


   @Test
   public void testWithActorsOptimised()
   {

      final ActorRef master = this.actorSystem.actorOf( new Props( new UntypedActorFactory()
      {
         public UntypedActor create()
         {
            return new PrimeNumbersSeqMaster( PerformanceTest.NO_OF_WORKERS, PerformanceTest.NO_OF_MESSAGES,
                  PerformanceTest.this.printer );
         }
      } ).withRouter( new RoundRobinRouter( PerformanceTest.NO_OF_MESSAGES ) ), "primeNumbersMaster" );

      sendMultipleMessages( ( x ) ->
      {
         master.tell( new NumberMessage( x ), null );
      } );

      this.actorSystem.awaitTermination();
   }


   private void sendMultipleMessages( final Consumer<Integer> consumer )
   {
      for ( int i = 0; i < PerformanceTest.NO_OF_MESSAGES; i++ )
      {
         final int randomNumber = this.rand.nextInt( PerformanceTest.RANDOM_NUMBER_LIMIT );

         consumer.accept( randomNumber );
      }
   }
}
