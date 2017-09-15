package akka.actors;

import org.apache.log4j.Logger;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.messages.NumberMessage;
import akka.messages.ResultMessage;
import akka.routing.RoundRobinRouter;

public class PrimeNumbersMaster extends UntypedActor
{

   final static Logger LOG = Logger.getLogger( PrimeNumbersMaster.class );

   private final ActorRef primeNumbersRouter;

   private final ActorRef resultPrinter;


   public PrimeNumbersMaster( final int numberOfWorkers, final ActorRef listener )
   {
      // Save our parameters locally
      this.resultPrinter = listener;

      // Create a new router to distribute messages out to PrimeNumbersActors
      this.primeNumbersRouter =
            this.getContext().actorOf(
                  new Props( PrimeNumbersWorker.class ).withRouter( new RoundRobinRouter( numberOfWorkers ) ),
                  "router" );

   }


   @Override
   public void onReceive( final Object message ) throws Exception
   {
      if ( message instanceof NumberMessage )
      {
         final NumberMessage numberMessage = ( NumberMessage ) message;

         this.primeNumbersRouter.tell( numberMessage, getSelf() );
      }
      else if ( message instanceof ResultMessage )
      {
         this.resultPrinter.tell( message, getSelf() );
      }
      else
      {
         unhandled( message );
      }

   }

}
