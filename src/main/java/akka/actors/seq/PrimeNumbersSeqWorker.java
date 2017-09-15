package akka.actors.seq;

import org.apache.log4j.Logger;

import akka.actor.UntypedActor;
import akka.messages.NumberSeqMessage;
import akka.messages.PartialResultMessage;
import demo.numbers.PrimeNumbersAdder;

/**
 * Actor used to calculate sum of prime numbers between a sequence of numbers.
 * 
 * @author manton
 */
public class PrimeNumbersSeqWorker extends UntypedActor
{
   /** The logger */
   final static Logger LOG = Logger.getLogger( PrimeNumbersSeqWorker.class );


   @Override
   public void onReceive( final Object message )
   {
      if ( message instanceof NumberSeqMessage )
      {
         final int start = (( NumberSeqMessage ) message).getStart();
         final int end = (( NumberSeqMessage ) message).getEnd();

         final PrimeNumbersAdder adder = new PrimeNumbersAdder( start, end );
         final int result = adder.calculate();

         // Send a notification back to the sender
         getSender().tell( new PartialResultMessage( (( NumberSeqMessage ) message).getInitialNumber(), result ),
               getSelf() );
      }
      else
      {
         // Mark this message as unhandled
         unhandled( message );
      }
   }
}
