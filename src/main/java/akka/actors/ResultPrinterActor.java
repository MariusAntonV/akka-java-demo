
package akka.actors;

import org.apache.log4j.Logger;

import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import akka.message.ResultMessage;
import demo.numbers.util.StopWatch;

/**
 * @author manton
 *
 */
public class ResultPrinterActor extends UntypedActor
{
   final static Logger LOG = Logger.getLogger( ResultPrinterActor.class );

   private final ActorSystem actorSystem;

   private final int expectedMessages;

   private int receivedMessages;

   private final StopWatch stopWatch = new StopWatch();


   public ResultPrinterActor( final int expectedMessages, final ActorSystem actorSystem )
   {
      super();
      this.actorSystem = actorSystem;
      this.expectedMessages = expectedMessages;
      this.stopWatch.start();
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
            this.stopWatch.stop();

            this.actorSystem.shutdown();
         }
      }
      else
      {
         unhandled( message );
      }
   }
}
