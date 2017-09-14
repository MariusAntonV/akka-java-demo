import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

import org.apache.log4j.Logger;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import akka.actors.PrimeNumbersMaster;
import akka.actors.ResultPrinterActor;
import akka.actors.seq.PrimeNumbersSeqMaster;
import akka.message.NumberMessage;
import akka.routing.RoundRobinRouter;
import demo.numbers.PrimeNumbersAdder;

/**
 * @author MariusAnton
 */
public class Main
{
   final static Logger LOG = Logger.getLogger( Main.class );

   final static int NO_OF_MESSAGES = 100;

   final static int NO_OF_WORKERS = 10;

   final Random rand = new Random( 324 );


   public static void main( final String... args )
   {
      final Main main = new Main();

      //      main.start();
      //      main.startWithAkka();
      main.startWithAkkaOptimised();
   }


   private void startWithAkkaOptimised()
   {

      // Create our ActorSystem, which owns and configures the classes
      final ActorSystem actorSystem = ActorSystem.create( "actorSystem" );

      final ActorRef resultPrinterActorRef = actorSystem.actorOf( new Props( new UntypedActorFactory()
      {
         public UntypedActor create()
         {
            return new ResultPrinterActor( Main.NO_OF_MESSAGES );
         }
      } ), "resultPrinterActor" );

      //-----------------------------------
      final ActorRef primeNumbersMaster = actorSystem.actorOf( new Props( new UntypedActorFactory()
      {
         public UntypedActor create()
         {
            return new PrimeNumbersSeqMaster( Main.NO_OF_WORKERS, Main.NO_OF_MESSAGES, resultPrinterActorRef );
         }
      } ).withRouter( new RoundRobinRouter( 50 ) ), "primeNumbersMaster" );
      //-----------------------------------

      // Start the calculation
      for ( int i = 0; i < Main.NO_OF_MESSAGES; i++ )
      {
         final int randomNumber = this.rand.nextInt( 100000 );

         primeNumbersMaster.tell( new NumberMessage( randomNumber ), null );
      }

      System.out.println( "Exit main thread !!!" );

      while ( true )
      {

      }

   }


   private void startWithAkka()
   {

      // Create our ActorSystem, which owns and configures the classes
      final ActorSystem actorSystem = ActorSystem.create( "actorSystem" );

      final ActorRef resultPrinterActorRef = actorSystem.actorOf( new Props( new UntypedActorFactory()
      {
         public UntypedActor create()
         {
            return new ResultPrinterActor( Main.NO_OF_MESSAGES );
         }
      } ), "resultPrinterActor" );

      final ActorRef primeNumbersMaster = actorSystem.actorOf( new Props( new UntypedActorFactory()
      {
         public UntypedActor create()
         {
            return new PrimeNumbersMaster( Main.NO_OF_WORKERS, resultPrinterActorRef );
         }
      } ), "primeNumbersMaster" );

      // Start the calculation
      for ( int i = 0; i < Main.NO_OF_MESSAGES; i++ )
      {
         final int randomNumber = this.rand.nextInt( 100000 );

         primeNumbersMaster.tell( new NumberMessage( randomNumber ), null );
      }

      System.out.println( "Exit main thread !!!" );

      while ( true )
      {

      }

   }


   private void start()
   {
      final LocalDateTime start = LocalDateTime.now();

      for ( int i = 0; i < Main.NO_OF_MESSAGES; i++ )
      {
         final int randomNumber = this.rand.nextInt( 100000 );

         final PrimeNumbersAdder adder = new PrimeNumbersAdder( randomNumber );
         final int sumOfPrimeNumbers = adder.calculate();

         System.out.println( "" );
         Main.LOG.info( "Sum of prime numbers lower then " + randomNumber + " is " + sumOfPrimeNumbers );
      }

      final LocalDateTime end = LocalDateTime.now();

      final Duration dur = Duration.between( start, end );
      System.out.println( "It took " + dur.toMillis() + " ms to run this program." );
   }
}
