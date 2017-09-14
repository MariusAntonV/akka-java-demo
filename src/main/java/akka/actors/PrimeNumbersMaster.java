package akka.actors;

import java.time.Duration;
import java.time.LocalDateTime;

import org.apache.log4j.Logger;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.message.NumberMessage;
import akka.message.ResultMessage;
import akka.routing.RoundRobinRouter;

public class PrimeNumbersMaster extends UntypedActor
{

   final static Logger LOG = Logger.getLogger( PrimeNumbersMaster.class );

   private final ActorRef primeNumbersRouter;

   private final ActorRef printResultRouter;

   private final ActorRef resultPrinter;

   private int noOfResults = 0;

   private final int noOfExpectedResults;

   final LocalDateTime start = LocalDateTime.now();


   public PrimeNumbersMaster( final int numberOfWorkers, final ActorRef listener, final int noOfExpectedResults )
   {
      // Save our parameters locally
      this.resultPrinter = listener;
      this.noOfExpectedResults = noOfExpectedResults;

      // Create a new router to distribute messages out to PrimeNumbersActors
      this.primeNumbersRouter =
            this.getContext().actorOf(
                  new Props( PrimeNumbersActor.class ).withRouter( new RoundRobinRouter( numberOfWorkers ) ),
                  "router" );

      this.printResultRouter =
            this.getContext().actorOf(
                  new Props( ResultPrinterActor.class ).withRouter( new RoundRobinRouter( numberOfWorkers ) ),
                  "printer" );
   }


   @Override
   public void onReceive( final Object message ) throws Exception
   {
      if ( message instanceof NumberMessage )
      {
         final NumberMessage numberMessage = ( NumberMessage ) message;

         this.primeNumbersRouter.tell( numberMessage, getSelf() );
      }
      else if ( message instanceof ResultMessage )
      {
         this.resultPrinter.tell( message, getSelf() );

         this.noOfResults++;

         if ( this.noOfResults >= this.noOfExpectedResults )
         {

            final LocalDateTime end = LocalDateTime.now();
            final Duration dur = Duration.between( this.start, end );
            // Stop our actor hierarchy
            PrimeNumbersMaster.LOG.info( "It took " + dur.toMillis() + " ms to run this program." );
            getContext().stop( getSelf() );
         }
      }
      else
      {
         unhandled( message );
      }

   }

}
