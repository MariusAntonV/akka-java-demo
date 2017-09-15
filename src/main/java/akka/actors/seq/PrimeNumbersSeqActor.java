package akka.actors.seq;

import org.apache.log4j.Logger;

import akka.actor.UntypedActor;
import akka.message.NumberSeqMessage;
import akka.message.PartialResultMessage;
import demo.numbers.PrimeNumbersAdder;

public class PrimeNumbersSeqActor extends UntypedActor
{
   final static Logger LOG = Logger.getLogger( PrimeNumbersSeqActor.class );


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
