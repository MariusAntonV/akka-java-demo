
package akka.actors;

import org.apache.log4j.Logger;

import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import akka.messages.ResultMessage;
import demo.numbers.util.StopWatch;

/**
 * Actor used to print a result.
 * 
 * @author manton
 */
public class ResultPrinterActor extends UntypedActor
{
   /** The logger */
   final static Logger LOG = Logger.getLogger( ResultPrinterActor.class );

   /** Actor system */
   private final ActorSystem actorSystem;

   /** No of expected messages */
   private final int expectedMessages;

   /** No of actual received messages */
   private int receivedMessages;

   /** A stop watch */
   private final StopWatch stopWatch = new StopWatch();


   /**
    * 
    * @param expectedMessages no of expected messages
    * @param actorSystem reference to actorSystem
    */
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

         shutdownWhenLimitReached();
      }
      else
      {
         unhandled( message );
      }
   }


   /**
    * Shutdown actor system when limit is reached. 
    * This is also performed to calculate the duration of program execution.
    */
   private void shutdownWhenLimitReached()
   {
      if ( this.receivedMessages >= this.expectedMessages )
      {
         this.stopWatch.stop();

         this.actorSystem.shutdown();
      }
   }
}
