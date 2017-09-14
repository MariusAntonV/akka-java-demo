
package akka.actors;

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


   @Override
   public void onReceive( final Object message ) throws Exception
   {
      if ( message instanceof ResultMessage )
      {
         final ResultMessage result = ( ResultMessage ) message;

         System.out.println( "" );
         ResultPrinterActor.LOG.info(
               "Sum of prime numbers lower then " + result.getNumber() + " is " + result.getSumOfPrimeNumbers() );

         //TODO count and shut down after 30 results ?
      }
      else
      {
         unhandled( message );
      }
   }
}
