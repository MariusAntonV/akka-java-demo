
package akka.actors;

import java.time.Duration;
import java.time.LocalDateTime;

import org.apache.log4j.Logger;

import akka.actor.UntypedActor;
import akka.message.ResultMessage;

/**
 * @author manton
 *
 */
public class ResultPrinterActor extends UntypedActor
{
   final static Logger LOG = Logger.getLogger( ResultPrinterActor.class );

   private final int expectedMessages;

   private int receivedMessages;

   private final LocalDateTime start;

   private LocalDateTime end;


   public ResultPrinterActor( final int expectedMessages )
   {
      super();
      this.expectedMessages = expectedMessages;
      this.start = LocalDateTime.now();
   }


   @Override
   public void onReceive( final Object message ) throws Exception
   {
      if ( message instanceof ResultMessage )
      {
         this.receivedMessages++;

         final ResultMessage result = ( ResultMessage ) message;

         System.out.println( "" );
         ResultPrinterActor.LOG.info(
               "Sum of prime numbers lower then " + result.getNumber() + " is " + result.getSumOfPrimeNumbers() );

         if ( this.receivedMessages >= this.expectedMessages )
         {
            this.end = LocalDateTime.now();

            final Duration dur = Duration.between( this.start, this.end );
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
