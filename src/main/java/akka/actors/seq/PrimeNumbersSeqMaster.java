package akka.actors.seq;

import org.apache.log4j.Logger;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.messages.NumberMessage;
import akka.messages.NumberSeqMessage;
import akka.messages.PartialResultMessage;
import akka.messages.ResultMessage;
import akka.routing.RoundRobinRouter;

/**
 * Master actor used to calculate sum of prime numbers lower then a limit, using smaller intervals.
 * 
 * @author manton
 *
 */
public class PrimeNumbersSeqMaster extends UntypedActor
{
   /** The logger */
   final static Logger LOG = Logger.getLogger( PrimeNumbersSeqMaster.class );

   /** Router used for calling workers */
   private final ActorRef workers;

   /** Print result actor */
   private final ActorRef printers;

   /** Reference for number of expected messages */
   private int expectedMessages = Integer.MAX_VALUE;

   /** Actual number of received results */
   private int receivedResults = 0;

   /** Sum of prime numbers calculated so far */
   private int sum = 0;


   /**
    * 
    * @param numberOfWorkers
    * @param noOfExpectedResults
    * @param printResultActor
    */
   public PrimeNumbersSeqMaster( final int numberOfWorkers, final int noOfExpectedResults,
         final ActorRef printResultActor )
   {
      this.workers =
            this.getContext().actorOf(
                  new Props( PrimeNumbersSeqWorker.class ).withRouter( new RoundRobinRouter( numberOfWorkers ) ),
                  "router" );

      this.printers = printResultActor;
   }


   @Override
   public void onReceive( final Object message ) throws Exception
   {
      if ( message instanceof NumberMessage )
      {
         final int number = (( NumberMessage ) message).getNumber();

         final int sent = sendSmallerIntervals( number );

         this.expectedMessages = sent;

      }
      else if ( message instanceof PartialResultMessage )
      {
         this.receivedResults++;
         this.sum += (( PartialResultMessage ) message).getSumOfPrimeNumbers();

         printResultsWhenFinished( ( PartialResultMessage ) message );
      }
      else
      {
         unhandled( message );
      }

   }


   /**
    * Break entire sequence into smaller intervals and send them to be processed by the workers.
    * @param number upper limit for calculating the sum
    * @return number of sent messages to workers
    */
   private int sendSmallerIntervals( final int number )
   {
      int start = 0;
      int sent = 0;
      while ( start <= number )
      {
         final int end = start + 1000;

         if ( end > number )
         {
            this.workers.tell( new NumberSeqMessage( start, number, number ), getSelf() );
         }
         else
         {
            this.workers.tell( new NumberSeqMessage( start, end, number ), getSelf() );
         }
         sent++;

         start = end;
      }
      return sent;
   }


   /**
    * Print final result when processing was finished.
    * @param message
    */
   private void printResultsWhenFinished( final PartialResultMessage message )
   {
      if ( this.receivedResults >= this.expectedMessages )
      {
         //finish
         this.printers.tell( new ResultMessage( message.getNumber(), this.sum ), getSelf() );

         getContext().stop( getSelf() );
      }
   }

}
