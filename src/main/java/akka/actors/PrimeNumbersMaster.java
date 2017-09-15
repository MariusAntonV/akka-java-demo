package akka.actors;

import org.apache.log4j.Logger;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.messages.NumberMessage;
import akka.messages.ResultMessage;
import akka.routing.RoundRobinRouter;

/**
 * Master actor used to calculate sum of prime numbers.
 * 
 * @author manton
 *
 */
public class PrimeNumbersMaster extends UntypedActor
{
   /** The logger */
   final static Logger LOG = Logger.getLogger( PrimeNumbersMaster.class );

   /** Router for using PrimeNumbersWorkers */
   private final ActorRef workers;

   /** Print result */
   private final ActorRef printer;


   /**
    * 
    * @param numberOfWorkers number of workers
    * @param printer the printer actor
    */
   public PrimeNumbersMaster( final int numberOfWorkers, final ActorRef printer )
   {
      this.printer = printer;

      // Create a new router to distribute messages out to PrimeNumbersActors
      this.workers =
            this.getContext().actorOf(
                  new Props( PrimeNumbersWorker.class ).withRouter( new RoundRobinRouter( numberOfWorkers ) ),
                  "router" );

   }


   @Override
   public void onReceive( final Object message ) throws Exception
   {
      if ( message instanceof NumberMessage )
      {
         this.workers.tell( message, getSelf() );
      }
      else if ( message instanceof ResultMessage )
      {
         this.printer.tell( message, getSelf() );
      }
      else
      {
         unhandled( message );
      }

   }

}
