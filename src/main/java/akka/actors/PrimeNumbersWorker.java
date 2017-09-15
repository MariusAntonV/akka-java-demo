package akka.actors;

import org.apache.log4j.Logger;

import akka.actor.UntypedActor;
import akka.messages.NumberMessage;
import akka.messages.ResultMessage;
import demo.numbers.PrimeNumbersAdder;

public class PrimeNumbersWorker extends UntypedActor
{
   final static Logger LOG = Logger.getLogger( PrimeNumbersWorker.class );


   @Override
   public void onReceive( final Object message )
   {
      if ( message instanceof NumberMessage )
      {
         final int number = (( NumberMessage ) message).getNumber();
         final PrimeNumbersAdder adder = new PrimeNumbersAdder( number );

         final int result = adder.calculate();

         // Send a notification back to the sender
         getSender().tell( new ResultMessage( number, result ), getSelf() );
      }
      else
      {
         // Mark this message as unhandled
         unhandled( message );
      }
   }
}
