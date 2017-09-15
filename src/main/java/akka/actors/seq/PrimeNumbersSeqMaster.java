package akka.actors.seq;

import org.apache.log4j.Logger;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.message.NumberMessage;
import akka.message.NumberSeqMessage;
import akka.message.PartialResultMessage;
import akka.message.ResultMessage;
import akka.routing.RoundRobinRouter;

public class PrimeNumbersSeqMaster extends UntypedActor
{
   final static Logger LOG = Logger.getLogger( PrimeNumbersSeqMaster.class );

   private final ActorRef primeNumbersRouter;

   private final ActorRef printResult;

   private int expectedMessages = Integer.MAX_VALUE;

   private int receivedResults = 0;

   private int sum = 0;


   public PrimeNumbersSeqMaster( final int numberOfWorkers, final int noOfExpectedResults,
         final ActorRef printResultActor )
   {
      // Create a new router to distribute messages out to PrimeNumbersActors
      this.primeNumbersRouter =
            this.getContext().actorOf(
                  new Props( PrimeNumbersSeqActor.class ).withRouter( new RoundRobinRouter( numberOfWorkers ) ),
                  "router" );

      this.printResult = printResultActor;
   }


   @Override
   public void onReceive( final Object message ) throws Exception
   {
      if ( message instanceof NumberMessage )
      {
         final int number = (( NumberMessage ) message).getNumber();

         int start = 0;
         int sent = 0;
         while ( start <= number )
         {

            final int end = start + 1000;

            if ( end > number )
            {
               this.primeNumbersRouter.tell( new NumberSeqMessage( start, number, number ), getSelf() );
            }
            else
            {
               this.primeNumbersRouter.tell( new NumberSeqMessage( start, end, number ), getSelf() );
            }
            sent++;

            start = end;
         }

         this.expectedMessages = sent;

      }
      else if ( message instanceof PartialResultMessage )
      {
         this.receivedResults++;
         this.sum += (( PartialResultMessage ) message).getSumOfPrimeNumbers();

         if ( this.receivedResults >= this.expectedMessages )
         {
            //finish
            this.printResult.tell( new ResultMessage( (( PartialResultMessage ) message).getNumber(), this.sum ),
                  getSelf() );

            getContext().stop( getSelf() );
         }
      }
      else
      {
         unhandled( message );
      }

   }

}
